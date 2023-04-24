package edu.goit.cryptoservice.parser;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class MillisecondsDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        return new Date(parser.getLongValue());
    }

}
