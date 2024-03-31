package org.example.secretsanta.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс для проверки работоспособности базы данных
 */
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    /**
     * Метод для проверки работтоспособности базы
     *
     * @return Health с информацией о состоянии базы данных
     * @throws SQLException если возникает исключение при работе с базой данных
     */
    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                return Health.up().withDetail("database", "UP").build();
            } else {
                return Health.down().withDetail("database", "DOWN").build();
            }
        } catch (SQLException e) {
            return Health.down().withDetail("database", "DOWN").withException(e).build();
        }
    }
}
