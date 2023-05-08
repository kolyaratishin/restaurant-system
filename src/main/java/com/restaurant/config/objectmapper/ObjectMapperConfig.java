package com.restaurant.config.objectmapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.restaurant.controller.request.MealRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper mealRequestObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("MealRequestDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(MealRequest.class, new MealRequestDeserializer());
        mapper.registerModule(module);
        return mapper;
    }
}
