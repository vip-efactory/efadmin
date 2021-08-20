package vip.efactory.config.tenant;

import lombok.AllArgsConstructor;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.tool.schema.Action;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vip.efactory.ejpa.tenant.database.MultiTenantConnectionProviderImpl;
import vip.efactory.ejpa.tenant.database.MultiTenantIdentifierResolver;
import vip.efactory.ejpa.tenant.database.TenantDataSourceProvider;
import vip.efactory.ejpa.tenant.identifier.TenantConstants;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hibernate.cfg.Environment.*;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({ JpaProperties.class })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"vip.efactory"})
// @EnableTransactionManagement(proxyTargetClass = true)
// @EnableJpaRepositories(basePackages = {"vip.efactory" }, transactionManagerRef = "txManager")
public class MultiTenantJpaConfiguration {

    private JpaProperties jpaProperties; // yml文件中的jpa配置
    private DataSource dataSource; // druid默认数据源

    /**
     * 初始化默认租户的数据源
     */
    @PostConstruct
    public void initDefaultDataSources() {
        // 先初始化租户表所在的数据源，然后从租户表中读取其他租户的数据源然后再进行初始化,详见：DataSourceBeanPostProcessor类
        TenantDataSourceProvider.addDataSource(TenantConstants.DEFAULT_TENANT_ID.toString(), dataSource); // 放入数据源集合中
    }

    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        return new MultiTenantConnectionProviderImpl();
    }

    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new MultiTenantIdentifierResolver();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            MultiTenantConnectionProvider multiTenantConnectionProvider,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

        Map<String, Object> hibernateProps = new LinkedHashMap<>();
        hibernateProps.putAll(this.jpaProperties.getProperties());
        hibernateProps.put(MULTI_TENANT, MultiTenancyStrategy.DATABASE); // 使用基于独立数据库的多租户模式
        hibernateProps.put(PHYSICAL_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy"); // 属性及column命名策略
        hibernateProps.put(MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProps.put(MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        hibernateProps.put(HBM2DDL_AUTO, Action.UPDATE); // 自动更新表结构,仅默认数据源有效且控制台会报警告可以不用管！
        // hibernateProps.put(Environment.SHOW_SQL, true); // 显示SQL,如果需要可以打开
        // hibernateProps.put(Environment.FORMAT_SQL, true); // 格式化SQL,如果需要可以打开

        // No dataSource is set to resulting entityManagerFactoryBean
        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();

        // result.setPackagesToScan(new String[] { Tenant.class.getPackage().getName() });
        result.setPackagesToScan("vip.efactory");
        result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        result.setJpaPropertyMap(hibernateProps);

        return result;
    }

    @Bean
    @Primary // 注意我们自己定义的bean，最好都加此注解，防止与自动配置的重复而不知道如何选择
    public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return entityManagerFactoryBean.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary // 注意我们自己定义的bean，最好都加此注解，防止与自动配置的重复而不知道如何选择
    public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        // 此处在SpringDataJpa中不使用hibernate的事务管理，否则可能导致log持久层save方法不写数据库的问题
        // HibernateTransactionManager result = new HibernateTransactionManager();
        // result.setAutodetectDataSource(false); // 不自动检测数据源
        // result.setSessionFactory(sessionFactory);
        // result.setRollbackOnCommitFailure(true);
        // return result;

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;

    }
}
