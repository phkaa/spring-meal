package com.project;

import com.project.domain.Domains;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackageClasses = Domains.class)
@EntityScan(basePackageClasses = Domains.class)
@EnableJpaRepositories(basePackageClasses = Domains.class)
@Configuration
public class DomainConfig {
}
