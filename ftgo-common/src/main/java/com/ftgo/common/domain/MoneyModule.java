package com.ftgo.common.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.ftgo.common.model.Money;

import java.io.IOException;

/**
 * The module class for serializing and deserializing the {@code Money} entity.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class MoneyModule extends SimpleModule {
	private static final long serialVersionUID = 1L;

	class MoneyDeserializer extends StdScalarDeserializer<Money> {
		private static final long serialVersionUID = 1L;

		protected MoneyDeserializer() {
			super(Money.class);
		}

		@Override
		public Money deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonToken token = jp.getCurrentToken();
			if (token == JsonToken.VALUE_STRING) {
				String str = jp.getText().trim();
				if (str.isEmpty()) {
					return null;
				} else {
					return new Money(str);
				}
			} else {
				throw ctxt.mappingException(getValueClass());
			}
		}
	}

	class MoneySerializer extends StdScalarSerializer<Money> {
		private static final long serialVersionUID = 1L;

		public MoneySerializer() {
			super(Money.class);
		}

		@Override
		public void serialize(Money value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			jgen.writeString(value.asString());
		}
	}

	@Override
	public String getModuleName() {
		return "FtgoCommonMOdule";
	}

	public MoneyModule() {
		addDeserializer(Money.class, new MoneyDeserializer());
		addSerializer(Money.class, new MoneySerializer());
	}
}
