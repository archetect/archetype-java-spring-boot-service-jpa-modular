package {{root_package}}.persistence.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
/**
 * @author Payment Gateway Archetype
 */
@Configuration
@ConditionalOnProperty(name = "db", havingValue = "mysql")

public class {{ ProjectPrefix }}{{ ProjectSuffix }}PersistenceMySQLConfig {

    private static final Logger logger = LoggerFactory.getLogger({{ProjectPrefix}}{{ ProjectSuffix }}PersistenceMySQLConfig.class);

    private final Environment env;

    @Autowired
    public {{ ProjectPrefix }}{{ ProjectSuffix }}PersistenceMySQLConfig(Environment env) {
        this.env = env;
    }


    @Bean
    public DataSource {{projectPrefix}}DS() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName("{{projectPrefix}}");
        logger.info("Configuring {{ProjectPrefix}} Persistence with a MySQL database");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl(env.getRequiredProperty("{{ property-prefix }}.db.mysql.url"));
        dataSource.setUsername(env.getRequiredProperty("{{ property-prefix }}.db.mysql.user"));
        dataSource.setPassword(env.getRequiredProperty("{{ property-prefix }}.db.mysql.password"));
        if (env.containsProperty("{{ property-prefix }}.db.mysql.pool.maximumPoolSize")) {
            dataSource.setMaximumPoolSize(env.getProperty("{{ property-prefix }}.db.mysql.pool.maximumPoolSize", Integer.class));
        }
        if (env.containsProperty("{{ property-prefix }}.db.mysql.pool.connectionTimeout")) {
            dataSource.setConnectionTimeout(env.getProperty("{{ property-prefix }}.db.mysql.pool.connectionTimeout", Long.class));
        }
        if (env.containsProperty("{{ property-prefix }}.db.mysql.pool.maxLifetime")) {
            dataSource.setMaxLifetime(env.getProperty("{{ property-prefix }}.db.mysql.pool.maxLifetime", Long.class));
        }
        if (env.containsProperty("{{ property-prefix }}.db.mysql.pool.idleTimeout")) {
            dataSource.setIdleTimeout(env.getProperty("{{ property-prefix }}.db.mysql.pool.idleTimeout", Long.class));
        }
        return dataSource;
    }

    @Bean
    public HibernateJpaVendorAdapter {{projectPrefix}}VA() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        // TODO: make switchable
//        vendorAdapter.setShowSql(Switches.showSql.isEnabled());
        return vendorAdapter;
    }
}
