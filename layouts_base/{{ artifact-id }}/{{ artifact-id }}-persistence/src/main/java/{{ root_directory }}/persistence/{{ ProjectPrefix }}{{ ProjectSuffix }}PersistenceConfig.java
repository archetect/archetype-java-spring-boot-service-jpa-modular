package {{ root_package }}.persistence;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author Archetect
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "{{root_package}}.persistence.repositories",
        },
        entityManagerFactoryRef = "{{projectPrefix}}EMF",
        transactionManagerRef = "{{projectPrefix}}TM")
@ComponentScan
public class {{ ProjectPrefix }}{{ ProjectSuffix }}PersistenceConfig {
    private static final Logger logger = LoggerFactory.getLogger({{ ProjectPrefix }}{{ ProjectSuffix }}PersistenceConfig.class );
    private final Environment env;

    @Autowired
    public {{ ProjectPrefix }}{{ ProjectSuffix }}PersistenceConfig(final Environment env) {
        this.env = env;
    }

    @Bean(name = "{{projectPrefix}}TM")
    @Qualifier("{{projectPrefix}}")
    public JpaTransactionManager {{projectPrefix}}TM(
    @Qualifier("{{projectPrefix}}DS") final DataSource dataSource,
    @Qualifier("{{projectPrefix}}EMF") final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean {{projectPrefix}}EMF(
    @Qualifier("{{projectPrefix}}DS") final DataSource dataSource,
    @Qualifier("{{projectPrefix}}VA") final JpaVendorAdapter vendorAdapter) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPersistenceUnitName("{{projectPrefix}}");
        factory.setPackagesToScan(
            "{{root_package}}.persistence.entities"
        );
        return factory;
    }

    @Bean
    @Qualifier("{{projectPrefix}}")
    public JdbcTemplate {{projectPrefix}}JdbcTemplate(@Qualifier("{{projectPrefix}}DS") final DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    @ConditionalOnProperty(name = "liquibase", havingValue = "true", matchIfMissing = true)
    public SpringLiquibase {{projectPrefix}}Liquibase(@Qualifier("{{projectPrefix}}DS") final DataSource dataSource) {
        logger.info("Applying Liquibase");
        SpringLiquibase liquibase = new SpringLiquibase();
//         liquibase.setContexts(RuntimeMode.current().name());
        liquibase.setDataSource(dataSource);
        if (env.containsProperty("initdb") || env.containsProperty("gateway.initdb")) {
            liquibase.setDropFirst(true);
        }
        liquibase.setChangeLog("classpath:db/{{artifact-id}}/changelog-master.xml");
        return liquibase;
    }
}