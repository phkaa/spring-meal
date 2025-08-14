package com.project.api;

import com.project.DomainConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(DomainConfig.class)
@Configuration
public class ApiConfig {
}
