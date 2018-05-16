package com.example.springbootmultidbsample.tenanta;



import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "tenantAEntityManagerFactory",
    transactionManagerRef = "tenantATransactionManager", basePackages = {"com.example.springbootmultidbsample.tenanta"})
public class TenantADBConfig {

	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.first")
	public DataSourceProperties firstDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "tenantADataSource")
	@Primary
	@ConfigurationProperties("app.datasource.first")
	public DataSource firstDataSource() {
		return firstDataSourceProperties().initializeDataSourceBuilder().build();
	}
	

  @Bean(name = "tenantAEntityManagerFactory")
  @Primary
  public LocalContainerEntityManagerFactoryBean tenant_a_EntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("tenantADataSource") DataSource dataSource) {
    return builder.dataSource(dataSource).packages("com.example.springbootmultidbsample.domain").persistenceUnit("tenant_a")
        .build();
  }

  @Bean(name = "tenantATransactionManager")
  @Primary
  public PlatformTransactionManager tenant_a_TransactionManager(
      @Qualifier("tenantAEntityManagerFactory") EntityManagerFactory tenant_AEntityManagerFactory) {
    return new JpaTransactionManager(tenant_AEntityManagerFactory);
  }

}