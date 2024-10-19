package com.github.gluhov.accountmanagementservice.config;

import com.github.gluhov.accountmanagementservice.util.JsonbToMapConverter;
import com.github.gluhov.accountmanagementservice.util.MapToJsonbConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfig {
    @Bean
    public R2dbcCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new JsonbToMapConverter());
        converters.add(new MapToJsonbConverter());
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters);
    }
}