package ru.otus.homework09.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyExecutor {
    private final Connection connection;

    public MyExecutor(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException{
        try(Statement statement = connection.createStatement()){
            statement.execute(query);
            ResultSet result = statement.getResultSet();
            T value = handler.handle(result);
            result.close();

            return value;
        }
    }


    public int execUpdate(String update) throws SQLException{
        try(Statement statement = connection.createStatement()){
            statement.execute(update);
            return statement.getUpdateCount();
        }
    }


}
