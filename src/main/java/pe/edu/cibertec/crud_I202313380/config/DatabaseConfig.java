package pe.edu.cibertec.crud_I202313380.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    @Value("${DB_SAKILA_URL}")
    private String dbSakilaUrl;

    @Value("${DB_SAKILA_USER}")
    private String dbSakilaUser;

    @Value("${DB_SAKILA_PASS}")
    private String dbSakilaPass;

    @Value("${DB_SAKILA_DRIVER}")
    private String dbSakilaDriver;

    @Bean
    public HikariDataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();

        // Configurar la conexión a la base de datos
        config.setJdbcUrl(dbSakilaUrl);
        config.setUsername(dbSakilaUser);
        config.setPassword(dbSakilaPass);
        config.setDriverClassName(dbSakilaDriver);

        // Configurar propiedades del pool de conexiones
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000); // 5 minutos
        config.setConnectionTimeout(30000); // 30 segundos

        System.out.println("###### HikariCP initialized for film_db ######");

        return new HikariDataSource(config);
    }
}
