package com.uday.joke.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@RestController
public class JokeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.security.user.name}")
    private String username;
    @Value("${spring.security.user.password}")
    private String password;

    @Value("${geekjoke.url}")
    private String geekJokeUrl;
    @Value("${dadjoke.url}")
    private String dadJokeUrl;

    private static Map<String, String> env;
    /**
     * Get joke by type
     *
     * @return return joke
     */
    @RequestMapping(value = "/joke", method = RequestMethod.GET)
    public String joke(@RequestParam String type){
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();

        boolean basicAuthInterceptorAlreadydded = interceptors.stream().anyMatch(
                interceptor -> interceptor instanceof BasicAuthorizationInterceptor
        );

        // The interceptors are reused from one request to another request. So, we only 
        // want to append the auth header once.
        if (!basicAuthInterceptorAlreadydded) {
            interceptors.add(new BasicAuthorizationInterceptor(username, password));
        }
        
        ResponseEntity<String> response = null;
        String joke;
        env = System.getenv();
        String dataCenter = env.get("DC_NAME");
        String cluster = env.get("CLUSTER_NAME");
        String verion = env.get("VERSION");

        if(type.equals("geek")){
            response = restTemplate.getForEntity(geekJokeUrl, String.class);
        }
        else if(type.equals("dad")){
            response = restTemplate.getForEntity(dadJokeUrl, String.class);
        }
        else {
            throw new RuntimeException("Unsupported type");
        }

        joke = response.getBody();
        String responseOut = dataCenter + "-" + cluster + "-"+verion + "-"+ type + " Joke> "+joke;
        logger.info(responseOut);
        return responseOut;
    }
}
