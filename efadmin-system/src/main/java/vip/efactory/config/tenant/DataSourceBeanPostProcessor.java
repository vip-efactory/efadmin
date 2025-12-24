package vip.efactory.config.tenant;

import com.alibaba.druid.pool.DruidDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import vip.efactory.ejpa.tenant.database.TenantDataSourceProvider;
import vip.efactory.modules.tenant.domain.Tenant;
import vip.efactory.modules.tenant.service.ITenantService;


import java.sql.SQLException;
import java.util.List;

/**
 * 此处这么写是为了破解循环引用,MultiTenantJpaConfiguration与ISystemTenantService相互依赖
 * @author dusuanyun
 */
@AllArgsConstructor
@Component
@Slf4j
@DependsOn("dataSource")
public class DataSourceBeanPostProcessor {
    @Resource
    private DruidDataSource druidDataSource;  //自动配置创建的druid数据源
    private ITenantService tenantService;

    @PostConstruct
    public void init() {
        // 获取数据库里所有的租户信息
        log.info("多租户的数据源初始化开始...");
        List<Tenant> tenantList = tenantService.findAllByStatusEquals(ITenantService.TENANT_ENABLE);
        // 初始化所有租户的数据源
        if (!CollectionUtils.isEmpty(tenantList)) {
            tenantList.forEach(tenant -> {
                // 替换原克隆代码块
                try {
                    // 手动创建Druid数据源，避免克隆不完整
                    DruidDataSource newDataSource = new DruidDataSource();

                    // 1. 基础连接配置（从租户表读取，兜底默认值）
                    String driverClassName = tenant.getDriverClassName() != null && !tenant.getDriverClassName().isEmpty()
                            ? tenant.getDriverClassName()
                            : druidDataSource.getDriverClassName(); // 兜底默认驱动

                    newDataSource.setDriverClassName(driverClassName);
                    newDataSource.setUrl(tenant.getJdbcUrl());
                    newDataSource.setUsername(tenant.getDbUsername());
                    newDataSource.setPassword(tenant.getDbPassword());

                    // 2. 连接池配置（复制默认数据源的参数，避免缺失）
                    newDataSource.setInitialSize(druidDataSource.getInitialSize());
                    newDataSource.setMaxActive(druidDataSource.getMaxActive());
                    newDataSource.setMinIdle(druidDataSource.getMinIdle());
                    newDataSource.setMaxWait(druidDataSource.getMaxWait());
                    newDataSource.setValidationQuery("SELECT 1"); // 显式指定校验SQL（MySQL通用）
                    newDataSource.setTestWhileIdle(true); // 空闲时校验连接
                    newDataSource.setTestOnBorrow(false); // 借用时不校验（提升性能）

                    // 3. 初始化数据源
                    newDataSource.init();

                    // 4. 放入租户数据源容器
                    TenantDataSourceProvider.addDataSource(tenant.getId().toString(), newDataSource);
                    log.info("租户{}的数据源初始化完成！", tenant.getId());

                } catch (SQLException throwables) {
                    // 异常处理逻辑不变
                    log.error("租户{}的数据源初始化失败！异常内容:{}", tenant.getId(), throwables.getMessage());
                    throwables.printStackTrace();
                    log.error("多租户模式下项目启动遇到了数据源初始化错误，为数据安全考虑，系统即将退出！");
                    System.exit(1);
                }

            // 下面是jdbc连接池数据源的配置
            // DataSource dataSource = DataSourceBuilder.create()
            //        .url(tenant.getJdbcUrl())
            //        .username(tenant.getDbUsername())
            //        .password(tenant.getDbPassword())
            //        .driverClassName(tenant.getDriverClassName())
            //        .build();
            // TenantDataSourceProvider.addDataSource(tenant.getId().toString(), dataSource);  // 放入数据源集合中
            // log.info("租户{}的数据源初始化完成！", tenant.getId());
            });
        }
        log.info("多租户的数据源初始化结束");
    }

}
