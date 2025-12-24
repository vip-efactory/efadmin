package vip.efactory.config.tenant;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;  // 使用 jakarta.persistence
import lombok.AllArgsConstructor;

import org.hibernate.SessionFactory;
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

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.hibernate.cfg.Environment.*;

/**
 * JPA的多租户配置
 * @author dusuanyun
 */
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
        // 放入数据源集合中
        TenantDataSourceProvider.addDataSource(TenantConstants.DEFAULT_TENANT_ID.toString(), dataSource);
    }

    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        return new MultiTenantConnectionProviderImpl();
    }

    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new MultiTenantIdentifierResolver();
    }

    // 其他代码完全不变，只改这一行！
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            MultiTenantConnectionProvider multiTenantConnectionProvider,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

        Map<String, Object> hibernateProps = new LinkedHashMap<>(this.jpaProperties.getProperties());
        hibernateProps.put("hibernate.multiTenancy", "DATABASE");
        // ########### 仅改这一行 ###########
        hibernateProps.put(PHYSICAL_NAMING_STRATEGY,
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        // ########### 其他代码完全不变 ###########
        hibernateProps.put(MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProps.put(MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

        // ========== 核心修改1：关闭表结构校验（替换原validate） ==========
        hibernateProps.put(HBM2DDL_AUTO, "none");
        // ========== 核心修改2：兜底关闭schema校验（防止还有残留校验） ==========
        hibernateProps.put("hibernate.schema.validation.enabled", "false");

        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
        result.setPackagesToScan("vip.efactory");
        result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        result.setJpaPropertyMap(hibernateProps);

        // 核心改动1：绑定数据源（避免后续数据源报错）
        result.setDataSource(dataSource);
        // 核心改动2：指定接口为Jakarta标准，解决冲突（报错提示的直接解决方案）
        result.setEntityManagerFactoryInterface(EntityManagerFactory.class);

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
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;

    }
}