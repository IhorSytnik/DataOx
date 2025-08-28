package com.test.DataOx.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.shell.command.annotation.CommandScan;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.test.DataOx.controllers", "com.test.DataOx.services"})
@EnableJpaRepositories(basePackages = {"com.test.DataOx.repositories"})
@EntityScan(basePackages = {"com.test.DataOx.model"})
@CommandScan("com.test.DataOx.controllers")
public class AppConfig {
}
