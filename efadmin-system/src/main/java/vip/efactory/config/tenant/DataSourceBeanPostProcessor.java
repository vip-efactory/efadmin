package vip.efactory.config.tenant;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.efactory.ejpa.tenant.database.TenantDataSourceProvider;
import vip.efactory.modules.tenant.domain.Tenant;
import vip.efactory.modules.tenant.service.ITenantService;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.List;

/**
 * 此处这么写是为了破解循环引用,MultiTenantJpaConfiguration与ISystemTenantService相互依赖
 */
@AllArgsConstructor
@Component
@Slf4j
public class DataSourceBeanPostProcessor {
    private DruidDataSource druidDataSource;  //自动配置创建的druid数据源
    private ITenantService tenantService;

    @PostConstruct
    public void init() {
        // 获取数据库里所有的租户信息
        log.info("多租户的数据源初始化开始...");
        List<Tenant> tenantList = tenantService.findAllByStatusEquals(ITenantService.TENANT_ENABLE);
        // 初始化所有租户的数据源
        if (tenantList != null && tenantList.size() > 0) {
            tenantList.forEach(tenant -> {
                try {
                    DruidDataSource newDataSource = druidDataSource.cloneDruidDataSource();  // 克隆已有的数据源进行修改
                    // 设定新的数据源的重要参数
                    newDataSource.setUsername(tenant.getDbUsername());
                    newDataSource.setPassword(tenant.getDbPassword());
                    newDataSource.setUrl(tenant.getJdbcUrl());
                    newDataSource.setDriverClassName(tenant.getDriverClassName());  // 其实也可以默认
                    newDataSource.init(); // 初始化数据源
                    TenantDataSourceProvider.addDataSource(tenant.getId().toString(), newDataSource);  // 放入数据源集合中
                    log.info("租户{}的数据源初始化完成！", tenant.getId());
                    // throw new SQLException("测试抛异常");
                } catch (SQLException throwables) {
                    log.error("租户{}的数据源初始化失败！异常内容:{}", tenant.getId(), throwables.getMessage());
                    throwables.printStackTrace();
                    // 致命错误，继续运行可能导致数据错乱，退出系统
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
