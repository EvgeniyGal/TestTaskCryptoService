package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.io.Closeable;

public interface BaseDBParser<E extends BaseCurrency<? extends Number>> extends BaseParser<E>, Closeable {

}
