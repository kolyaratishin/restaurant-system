package com.restaurant.config.objectmapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.restaurant.controller.dto.TableDto;

import java.io.IOException;

public class TableDtoDeserializer extends StdDeserializer<TableDto> {

    public TableDtoDeserializer() {
        this(null);
    }

    public TableDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TableDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TableDto tableDto = new TableDto();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        JsonNode nameNode = node.get("name");
        String name = nameNode.asText();
        tableDto.setName(name);

        JsonNode restaurantIdNode = node.get("restaurantId");
        Long restaurantId = restaurantIdNode.asLong();
        tableDto.setRestaurantId(restaurantId);

        return tableDto;
    }
}
