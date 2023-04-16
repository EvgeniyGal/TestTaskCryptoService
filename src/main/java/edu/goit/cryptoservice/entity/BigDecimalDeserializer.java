package edu.goit.cryptoservice.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            return new BigDecimal(Long.parseLong(p.getValueAsString())).divide(BigDecimal.valueOf(10000), 4, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            return new BigDecimal(p.getValueAsString());
        }
    }
}
