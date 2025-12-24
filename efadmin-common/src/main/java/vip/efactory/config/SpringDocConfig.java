package vip.efactory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components; // 补充导入
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.IntegerSchema; // 补充导入
import io.swagger.v3.oas.models.media.StringSchema; // 补充导入
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Conditional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty; // 补充导入
import org.springframework.web.method.HandlerMethod; // 补充导入

/**
 * SpringDoc OpenAPI 配置（替代原SwaggerConfig）
 * API页面：http://localhost:8000/swagger-ui/index.html
 */
@Configuration
public class SpringDocConfig {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.token-start-with}")
    private String tokenStartWith;

    @Value("${swagger.enabled:true}") // 兼容原来的开关配置，默认true
    private Boolean enabled;

    /**
     * 全局API信息配置
     */
    @Bean
    public OpenAPI efadminOpenAPI() {
        // 配置JWT认证头
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(tokenHeader)
                .description("JWT令牌格式：" + tokenStartWith + " {token}")
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("efadmin 接口文档")
                        .version("2.3")
                        .description("efadmin后台管理系统API文档"))
                // 全局添加JWT认证头
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components() // 简化全限定名
                        .addSecuritySchemes("JWT", securityScheme));
    }

    /**
     * 分组配置（用@ConditionalOnProperty控制开关，替代enabled()方法）
     */
    @Bean
    @ConditionalOnProperty(name = "swagger.enabled", havingValue = "true", matchIfMissing = true) // 开关控制
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("efadmin-api")
                .pathsToMatch("/**") // 匹配所有接口
                .pathsToExclude("/error/**") // 排除错误页面
                .packagesToScan("vip.efactory") // 扫描的包
                // 移除.enabled(enabled)（低版本不支持）
                .build();
    }

    /**
     * 支持Pageable参数在Swagger中正确显示
     */
    @Bean
    public OperationCustomizer pageableOperationCustomizer() {
        return (operation, handlerMethod) -> {
            // 处理Pageable参数，自动转换为page、size、sort
            operation.getParameters().removeIf(param ->
                    param.getName().equals("pageable") || param.getName().equals("org.springframework.data.domain.Pageable"));

            // 修复IntegerSchema的defaultValue：改用setDefaultValue，参数为Object类型
            IntegerSchema pageSchema = new IntegerSchema();
            pageSchema.setDefault(0); // 用setter方法设置默认值

            IntegerSchema sizeSchema = new IntegerSchema();
            sizeSchema.setDefault(10);

            // 添加page参数（调整顺序：先in、name，再schema）
            operation.addParametersItem(new Parameter()
                    .in("query")
                    .name("page")
                    .description("页码（从0开始）")
                    .required(false)
                    .schema(pageSchema));

            // 添加size参数
            operation.addParametersItem(new Parameter()
                    .in("query")
                    .name("size")
                    .description("每页条数")
                    .required(false)
                    .schema(sizeSchema));

            // 添加sort参数
            operation.addParametersItem(new Parameter()
                    .in("query")
                    .name("sort")
                    .description("排序（格式：字段名,asc/desc，多字段用逗号分隔）")
                    .required(false)
                    .schema(new StringSchema()));

            return operation;
        };
    }
}