package {{root_package}}.persistence.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * @author Service Archetype
 */
@Configuration
@ConditionalOnProperty(name = "db", havingValue = "postgres")
public class {{ProjectPrefix}}{{ ProjectSuffix }}PersistencePostgresConfig {

    private static final Logger logger = LoggerFactory.getLogger({{ProjectPrefix}}{{ ProjectSuffix }}PersistencePostgresConfig.class);

    private final Environment env;

    @Autowired
    public {{ProjectPrefix}}{{ ProjectSuffix }}PersistencePostgresConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource {{projectPrefix}}DS() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName("{{projectPrefix}}");
        logger.info("Configuring {{ ProjectPrefix }} Persistence with a PostgreSQL database");
        dataSource.setJdbcUrl(env.getRequiredProperty("{{ property-prefix }}.db.postgres.url"));
        dataSource.setUsername(env.getRequiredProperty("{{ property-prefix }}.db.postgres.user"));
        dataSource.setPassword(env.getRequiredProperty("{{ property-prefix }}.db.postgres.password"));
        if (env.containsProperty("{{ property-prefix }}.db.postgres.pool.maximumPoolSize")) {
            dataSource.setMaximumPoolSize(env.getProperty("{{ property-prefix }}.db.postgres.pool.maximumPoolSize", Integer.class));
        }
        if (env.containsProperty("{{ property-prefix }}.db.postgres.pool.connectionTimeout")) {
            dataSource.setConnectionTimeout(env.getProperty("{{ property-prefix }}.db.postgres.pool.connectionTimeout", Long.class));
        }
        if (env.containsProperty("{{ property-prefix }}.db.postgres.pool.maxLifetime")) {
            dataSource.setMaxLifetime(env.getProperty("{{ property-prefix }}.db.postgres.pool.maxLifetime", Long.class));
        }
        if (env.containsProperty("{{ property-prefix }}.db.postgres.pool.idleTimeout")) {
            dataSource.setIdleTimeout(env.getProperty("{{ property-prefix }}.db.postgres.pool.idleTimeout", Long.class));
        }
        return dataSource;
    }

    @Bean
    public HibernateJpaVendorAdapter {{ projectPrefix }}VA() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
//         vendorAdapter.setShowSql(Switches.showSql.isEnabled());
        return vendorAdapter;
    }
}
