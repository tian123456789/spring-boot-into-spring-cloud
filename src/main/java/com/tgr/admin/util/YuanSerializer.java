package com.tgr.admin.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class YuanSerializer extends JsonSerializer<BigDecimal> {
	@Override
	public void serialize(BigDecimal value, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		DecimalFormat df = new DecimalFormat("0.00");
		generator.writeString(df.format(value));
	}
}