package com.project;

import com.project.domain.Domains;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = Domains.class)
@EntityScan(basePackageClasses = Domains.class)
@Configuration
public class DomainConfig {
}
