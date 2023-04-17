package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.BaseCurrency;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DBParser<E extends BaseCurrency<? extends Number>> implements BaseDBParser<E>{

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
