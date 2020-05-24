package com.ftgo.orderservice.api.json;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * The JsonDeserializer for deserializing {@code LocalDateTime}.
 * 
 * <p>Vanilla Jackson doesn't have a way to deserialize a {@code LocalDateTime} object from any JSON string value.
 *
 * @author  Wuyi Chen
 * @date    05/23/2020
 * @version 1.0
 * @since   1.0
 */
public class ParseDeserializer extends StdDeserializer<LocalDateTime> {
	private static final long serialVersionUID = 1L;

	public ParseDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return LocalDateTime.parse(p.getValueAsString()); // or overloaded with an appropriate format
    }
}
