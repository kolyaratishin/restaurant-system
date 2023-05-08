package com.restaurant.config.objectmapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.restaurant.controller.request.MealRequest;

import java.io.IOException;
import java.math.BigDecimal;

public class MealRequestDeserializer extends StdDeserializer<MealRequest> {
    public MealRequestDeserializer() {
        this(null);
    }

    public MealRequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MealRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        MealRequest mealRequest = new MealRequest();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        JsonNode nameNode = node.get("name");
        String name = nameNode.asText();
        mealRequest.setName(name);

        JsonNode priceNode = node.get("price");
        BigDecimal price = BigDecimal.valueOf(nameNode.asDouble());
        mealRequest.setPrice(price);

        JsonNode mealGroupIdNode = node.get("mealGroupId");
        Long mealGroupId = mealGroupIdNode.asLong();
        mealRequest.setMealGroupId(mealGroupId);
        return mealRequest;
    }
}
