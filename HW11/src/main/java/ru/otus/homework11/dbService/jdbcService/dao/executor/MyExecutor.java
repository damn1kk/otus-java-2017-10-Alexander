package ru.otus.homework11.dbService.jdbcService.dao.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyExecutor {
    private final Connection connection;

    public MyExecutor(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection(){
        return this.connection;
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException{
        try(Statement statement = connection.createStatement()){
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            T value = handler.handle(resultSet);
            resultSet.close();

            return value;
        }
    }

    public int execUpdate(String query) throws SQLException{
        try(Statement statement = connection.createStatement()){
            statement.execute(query);
            return statement.getUpdateCount();
        }
    }
}
