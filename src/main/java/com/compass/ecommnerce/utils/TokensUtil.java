package com.compass.ecommnerce.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;

public class TokensUtil {

    private  static Instant getTime(){
        return Instant.now().plus(Duration.ofHours(2));
    }
}
