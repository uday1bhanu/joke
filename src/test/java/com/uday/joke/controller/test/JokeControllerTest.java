package com.uday.joke.controller.test;

import com.uday.joke.JokeApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT, classes={ JokeApplication.class })
public class JokeControllerTest {
    @Value("${local.server.port}")
    private int port;
    private String base;
    private TestRestTemplate template;
    private Map<String, String> params = new HashMap<>();
    private String parametrizedArgs;

    private HttpHeaders headers = new HttpHeaders();

    private HttpEntity<String> entity;

    @Before
    public void setUp() throws Exception {
        params.put("type", "geek");
        this.base = "http://localhost:" + port + "/joke?%s";
        this.parametrizedArgs = params.keySet().stream().map(k ->
                String.format("%s={%s}", k, k)
        ).collect(Collectors.joining("&"));
        template = new TestRestTemplate().withBasicAuth("ap", "apapp!");
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        entity  = new HttpEntity<>(headers);
    }

    @Test
    public void shouldGetJokeByType() {
        ResponseEntity<String> response = template.exchange(String.format(base, parametrizedArgs), HttpMethod.GET, entity, String.class, params);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getJokeByTypeShouldSendResponseHeaders() {
        ResponseEntity<String> response = template.exchange(String.format(base, parametrizedArgs), HttpMethod.GET, entity, String.class, params);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        HttpHeaders httpHeaders = response.getHeaders();
        assertEquals("nosniff", httpHeaders.get("X-Content-Type-Options").get(0));
        assertEquals("1; mode=block", httpHeaders.get("X-XSS-Protection").get(0));
        assertEquals("no-cache, no-store, max-age=0, must-revalidate", httpHeaders.getCacheControl());
        assertEquals("no-cache", httpHeaders.getPragma());
    }

    @After
    public void tearDown() {
        this.base = null;
        template = null;
    }
}
