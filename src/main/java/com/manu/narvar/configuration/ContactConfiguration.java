package com.manu.narvar.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;


@Configuration
@EnableAutoConfiguration
public class ContactConfiguration {
	
	@Autowired
	private Environment environment;

	/**
	 * Data source.
	 *
	 * @return the driver manager data source
	 * @throws Exception 
	 */
	@Bean
	public DataSource dataSource() {
		 DriverManagerDataSource dataSource = new DriverManagerDataSource();
		    dataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
		    dataSource.setUrl(environment.getProperty("spring.datasource.url"));
		    dataSource.setUsername(environment.getProperty("spring.datasource.username"));
		    dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		    return dataSource;
	}

	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
	         HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
	         factory.setEntityManagerFactory(emf);
	         return factory;
	}
	



}
