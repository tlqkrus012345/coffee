package com.coffee.intergration;


import com.coffee.api.order.request.CreateOrderRequest;
import com.coffee.domain.cafe.entity.Menu;
import com.coffee.domain.order.dto.OrderDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.entity.OrderRepository;
import com.coffee.domain.cafe.service.CafeService;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.payment.entity.PaymentRepository;
import com.coffee.external.ExternalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//프로파일 설정 관리,
class CoffeeOrderTest extends AbstractIntegrationTest{
    @LocalServerPort
    private Integer port;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CafeRepository cafeRepository;

    @BeforeEach
    void setUp() {
        System.out.println("===========================");
        System.out.println("setUp");
        RestAssured.baseURI = "http://localhost:" + port;
        orderRepository.deleteAll();
        paymentRepository.deleteAll();
        saveMenuAndMember();
        System.out.println("===========================");
    }

    //@Test
    void HikariTest() throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(mysqlContainer.getJdbcUrl());
        hikariConfig.setUsername(mysqlContainer.getUsername());
        hikariConfig.setPassword(mysqlContainer.getPassword());
        System.out.println("===========================");
        System.out.println(mysqlContainer.getJdbcUrl());
        System.out.println(mysqlContainer.getDatabaseName());
        System.out.println(mysqlContainer.getHost());
        System.out.println("===========================");
        HikariDataSource hds = new HikariDataSource(hikariConfig);

        Statement statement = hds.getConnection().createStatement();
        statement.execute("SELECT 1");
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();

        int resultSetInt = resultSet.getInt(1);
        Assertions.assertThat(resultSetInt).isEqualTo(1);
    }

    @Test
    @DisplayName("커피 주문/결제 테스트")
    void coffeeOrderTest() throws Exception {
        RequestSpecification request = RestAssured.given();

        Map<String, Long> requestParams = new HashMap<>();
        requestParams.put("memberId", 1L);
        requestParams.put("menuId", 1L);
        request.header("Content-Type", "application/json");
        request.body(requestParams);

        Response response = request.post("/order");
        JsonPath jsonPath = response.jsonPath();
        long orderId = jsonPath.getLong("orderId");
        Order order = orderRepository.findById(1L).get();
        Assertions.assertThat(orderId).isEqualTo(order.getId());

        given()
                .pathParam("orderId", orderId)
                .header("Content-Type", "application/json").
        when()
                .get("/pay/{orderId}").
        then()
                .body(".", eq(1));
    }
    private void saveMenuAndMember() {
        for (int i=0; i<5; i++) {
            cafeRepository.save(Menu.builder()
                    .name(i + "커피")
                    .price(i * 100)
                    .cnt(1)
                    .build());
        }
        memberRepository.save(Member.builder()
                .point(10000)
                .build());
    }
}
