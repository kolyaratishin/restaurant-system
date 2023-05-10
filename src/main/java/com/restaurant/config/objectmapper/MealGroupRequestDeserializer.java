package com.restaurant.config.objectmapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.restaurant.controller.request.MealGroupRequest;
import com.restaurant.controller.request.MealRequest;

import java.io.IOException;
import java.math.BigDecimal;

public class MealGroupRequestDeserializer extends StdDeserializer<MealGroupRequest> {
    public MealGroupRequestDeserializer() {
        this(null);
    }

    public MealGroupRequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MealGroupRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        MealGroupRequest mealGroupRequest = new MealGroupRequest();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        JsonNode nameNode = node.get("name");
        String name = nameNode.asText();
        mealGroupRequest.setName(name);

        JsonNode restaurantIdNode = node.get("restaurantId");
        Long restaurantId = restaurantIdNode.asLong();
        mealGroupRequest.setRestaurantId(restaurantId);
        return mealGroupRequest;
    }
}
