package com.uday.joke.actuator.controller.test;

import com.uday.joke.JokeApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT, classes={ JokeApplication.class })
public class ActuatorControllerTest {
    @Value("${local.server.port}")
    private int port;
    private URL base;
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/actuator");
        template = new TestRestTemplate().withBasicAuth("ap", "apapp!");
    }

    @Test
    public void shouldGetActuatorEndpoints() {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldGetActuatorHealthEndpoints() {
        ResponseEntity<String> response = template.getForEntity(base.toString()+"/health", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldGetActuatorEnvEndpoints() {
        ResponseEntity<String> response = template.getForEntity(base.toString()+"/env", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldGetActuatorMetricsEndpoints() {
        ResponseEntity<String> response = template.getForEntity(base.toString()+"/metrics", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldGetActuatorInfoEndpoints() {
        ResponseEntity<String> response = template.getForEntity(base.toString()+"/info", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }


    @After
    public void tearDown() {
        this.base = null;
        template = null;
    }
}
