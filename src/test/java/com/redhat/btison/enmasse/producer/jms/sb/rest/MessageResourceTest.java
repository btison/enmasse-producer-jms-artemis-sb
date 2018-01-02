package com.redhat.btison.enmasse.producer.jms.sb.rest;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.redhat.btison.enmasse.producer.jms.sb.service.MessageProducerService;

import io.restassured.RestAssured;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageResourceTest {

    @LocalServerPort
    private int port;

    @MockBean
    MessageProducerService messageProducerService;

    @Before
    public void beforeTest() throws Exception {
        RestAssured.baseURI = String.format("http://localhost:%d", port);
    }

    @Test
    public void testPostMessage() {
        given()
          .contentType("application/json")
          .body("{\"message\":\"test\"}")
          .when()
          .post("/message")
          .then()
          .assertThat()
          .statusCode(200);

        verify(messageProducerService).sendText("test");
    }

}
