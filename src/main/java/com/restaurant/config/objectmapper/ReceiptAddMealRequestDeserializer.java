package com.restaurant.config.objectmapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.restaurant.controller.request.MealGroupRequest;
import com.restaurant.controller.request.ReceiptAddMealRequest;

import java.io.IOException;

public class ReceiptAddMealRequestDeserializer extends StdDeserializer<ReceiptAddMealRequest> {
    public ReceiptAddMealRequestDeserializer() {
        this(null);
    }

    public ReceiptAddMealRequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ReceiptAddMealRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ReceiptAddMealRequest receiptAddMealRequest = new ReceiptAddMealRequest();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        JsonNode mealIdNode = node.get("mealId");
        Long mealId = mealIdNode.asLong();
        receiptAddMealRequest.setMealId(mealId);

        JsonNode receiptIdNode = node.get("receiptId");
        Long receiptId = receiptIdNode.asLong();
        receiptAddMealRequest.setReceiptId(receiptId);
        return receiptAddMealRequest;
    }
}
