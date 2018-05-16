package com.example.springbootmultidbsample.tenantc;



import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "tenantCEntityManagerFactory",
    transactionManagerRef = "tenantCTransactionManager", basePackages = {"com.example.springbootmultidbsample.tenantc"})
public class TenantC_DBConfig {

	@Bean
	@ConfigurationProperties("app.datasource.third")
	public DataSourceProperties tenant_C_DataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "tenantCDataSource")
	@ConfigurationProperties("app.datasource.third")
	public DataSource tenanct_C_DataSource() {
		return tenant_C_DataSourceProperties().initializeDataSourceBuilder().build();
	}
 
	Map<String,?> additionalJpaProperties(){
        Map<String,String> map = new HashMap<>();
        map.put("hibernate.hbm2ddl.auto", "none");
        map.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        map.put("hibernate.show_sql", "true");
        return map;
    }

  @Bean(name = "tenantCEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean tenant_c_EntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("tenantCDataSource") DataSource dataSource) {
    return builder
    		.dataSource(dataSource)
    		.packages("com.example.springbootmultidbsample.domain")
    		.persistenceUnit("tenant_c")
    		.properties(additionalJpaProperties())
    		.build();
  }

  @Bean(name = "tenantCTransactionManager")
  public PlatformTransactionManager tenant_c_TransactionManager(
      @Qualifier("tenantCEntityManagerFactory") EntityManagerFactory tenant_CEntityManagerFactory) {
    return new JpaTransactionManager(tenant_CEntityManagerFactory);
  }

}