package com.epam.esm.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Class {@code SpringConfig} contains spring configuration for web subproject.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
}
