package com.epam.esm.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

import java.time.Clock;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
