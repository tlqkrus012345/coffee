package com.coffee.intergration;

import com.coffee.CoffeeApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(
        classes = CoffeeApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class AbstractIntegrationTest {

    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.3.0")
            .withDatabaseName("testDB")
            .withUsername("testUser")
            .withPassword("testPassword");

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        System.out.println("===========================");
        System.out.println("dynamicProperties");
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        System.out.println("===========================");
    }
    @BeforeAll
    static void beforeAll() {
        System.out.println("===========================");
        System.out.println("mysqlContainer Start !");
        mysqlContainer.start();
        System.out.println("===========================");
    }
    @AfterAll
    static void afterAll() {
        System.out.println("===========================");
        System.out.println("mysqlContainer Stop !");
        mysqlContainer.stop();
        System.out.println("===========================");
    }
}
