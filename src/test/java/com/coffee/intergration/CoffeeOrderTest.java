package com.coffee.intergration;


import com.coffee.domain.menu.entity.Menu;
import com.coffee.domain.menu.entity.MenuRepository;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.repository.OrderRepository;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.payment.entity.Payment;
import com.coffee.domain.payment.entity.PaymentRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    MenuRepository menuRepository;

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
        request.header("Content-Type", "application/json");

        Map<String, Long> requestParams = new HashMap<>();
        requestParams.put("memberId", 1L);
        requestParams.put("menuId", 1L);
        request.body(requestParams);

        Response response = request.post("/order");
        JsonPath jsonPath = response.jsonPath();
        Menu menu = menuRepository.findById(1L).get();
        int price = menu.getPrice();
        Member member = memberRepository.findById(1L).get();
        int memberPoint = member.getPoint();

        long orderId = jsonPath.getLong("orderId");
        Order order = orderRepository.findById(orderId).get();

        Assertions.assertThat(orderId).isEqualTo(order.getId());
        Assertions.assertThat(price).isEqualTo(1000);
        Assertions.assertThat(memberPoint).isEqualTo(10000);

        request = RestAssured.given();
        request.pathParam("orderId", orderId);
        request.header("Content-Type", "application/json");

        response = request.get("/pay/{orderId}");
        jsonPath = response.jsonPath();

        int remainPoint = jsonPath.getInt("remainPoint");
        Payment payment = paymentRepository.findById(1L).get();
        Assertions.assertThat(payment.getOrder().getId()).isEqualTo(1L);
        Assertions.assertThat(remainPoint).isEqualTo(9000);
    }
    private void saveMenuAndMember() {
        for (int i=1; i<=5; i++) {
            menuRepository.save(Menu.builder()
                    .name(i + "커피")
                    .price(i * 1000)
                    .cnt(i+1)
                    .build());
        }
        memberRepository.save(Member.builder()
                .point(10000)
                .build());
    }
}
