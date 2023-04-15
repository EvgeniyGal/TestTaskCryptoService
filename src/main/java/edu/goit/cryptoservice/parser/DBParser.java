package edu.goit.cryptoservice.parser;

import lombok.SneakyThrows;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DBParser<E> implements Closeable, BaseParser<E> {

    private final Connection connection;
    private final Class<E> entityClass;

    public DBParser(Connection connection, Class<E> entityClass) {
        this.connection = connection;
        this.entityClass = entityClass;
    }


    //TODO
    @Override
    public Optional<List<E>> parse() {
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public void close() {
        connection.close();
    }
}
