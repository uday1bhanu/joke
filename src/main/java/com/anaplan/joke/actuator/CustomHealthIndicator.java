package com.uday.joke.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {

        if(isAppUp()){
            return Health.up()
                    .withDetail("app is up", "online")
                    .build();
        }

        return Health.down()
                .withDetail("app is down", "offline")
                .build();
    }

    private boolean isAppUp(){
        return true;
    }
}