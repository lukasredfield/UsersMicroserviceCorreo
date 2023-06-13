package com.correoargentino.services.user;

import com.correoargentino.services.user.infrastructure.persistence.mapper.imp.UserMapperImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-local.properties")
public class AppConfig {
    @Bean
    @Primary
    public UserMapperImp userMapperImp() {
        return new UserMapperImp();
    }
}

