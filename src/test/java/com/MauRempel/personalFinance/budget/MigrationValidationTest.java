package com.MauRempel.personalFinance.budget;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("flyway-test")

public class MigrationValidationTest {

    @Test
    void contextLoadsWithFlywayMigrations() {

    }
}
