package com.fordevs.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
/**
 * Configuración de la base de datos.
 * @author Enoc.Velza | for-devs.com
 * @version 1.0
 */
@Configuration
public class DatabaseConfig {
	/**
	 * Configura el DataSource Principal para la configuración
	 * de la base de datos de Spring Batch.
	 * @return DataSource.
	 */
	/*@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource datasource() {
		return DataSourceBuilder.create().build();
	}*/

	/**
	 * Configura el DataSource para la base de datos PostgreSQL.
	 *
	 * @return Un DataSource configurado para PostgreSQL.
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.postgresdatasource")
	public DataSource postgresdatasource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * Configura el EntityManagerFactory para la base de datos PostgreSQL.
	 *
	 * @return Un EntityManagerFactory configurado para PostgreSQL.
	 */
	@Bean
	public EntityManagerFactory postgresqlEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lem = 
				new LocalContainerEntityManagerFactoryBean();
		
		lem.setDataSource(postgresdatasource());
		lem.setPackagesToScan("com.fordevs.postgresql.entity");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lem.afterPropertiesSet();
		
		return lem.getObject();
	}
}
