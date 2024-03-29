package com.restaurant.config.objectmapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.restaurant.controller.dto.TableDto;
import com.restaurant.controller.request.MealGroupRequest;
import com.restaurant.controller.request.MealRequest;
import com.restaurant.controller.request.ReceiptAddMealRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("RequestDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(MealRequest.class, new MealRequestDeserializer());
        module.addDeserializer(MealGroupRequest.class, new MealGroupRequestDeserializer());
        module.addDeserializer(ReceiptAddMealRequest.class, new ReceiptAddMealRequestDeserializer());
        module.addDeserializer(TableDto.class, new TableDtoDeserializer());
        mapper.registerModule(module);
        return mapper;
    }
}
