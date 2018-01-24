package ru.otus.homework12.database.dbService.jdbcService.dao;

import ru.otus.homework12.database.dataSet.UserDataSet;
import ru.otus.homework12.database.dbService.jdbcService.dao.executor.MyExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataSetDAO {
    private static final String TABLE_NAME = "USER";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            "id bigint(20) NOT NULL auto_increment primary key," +
            "name varchar(255)," +
            "age int(3));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = %o;";
    private static final String INSERT_USER = "INSERT INTO " + TABLE_NAME + " (id, name, age) VALUES (%d, '%s', %d);";
    private static final String INSERT_USER_WITHOUT_ID = "INSERT INTO " + TABLE_NAME + " (name, age) VALUES ('%s', %d);";
    private static final String UPDATE_USER = "UPDATE " + TABLE_NAME + " SET id = %d, name = '%s', age = %d WHERE id = %d;";
    private static final String DELETE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id = %d;";
    private static final String FIND_ALL_USERS = "SELECT * FROM " + TABLE_NAME + ";";

    private MyExecutor executor;

    public UserDataSetDAO(Connection connection){
        this.executor = new MyExecutor(connection);
    }

    public void createTable() throws SQLException{
        String query = CREATE_TABLE;
        System.out.println(query);
        executor.execUpdate(query);
    }

    public void deleteTable() throws SQLException{
        String query = DROP_TABLE;
        System.out.println(query);
        executor.execUpdate(query);
    }

    public UserDataSet findById(long id) throws SQLException{
        String query = String.format(FIND_BY_ID, id);
        System.out.println(query);
        return executor.execQuery(query, resultSet -> {
            resultSet.next();
            return new UserDataSet(resultSet.getLong(1),
                    resultSet.getString(2), resultSet.getInt(3));
        });
    }

    public int insertUser(UserDataSet user) throws SQLException{
        String query = String.format(INSERT_USER, user.getId(), user.getName(), user.getAge());
        System.out.println(query);
        return executor.execUpdate(query);
    }

    public int insertUserWithoutID(UserDataSet user) throws SQLException{
        String query = String.format(INSERT_USER_WITHOUT_ID, user.getName(), user.getAge());
        System.out.println(query);
        return executor.execUpdate(query);
    }

    public int updateUser(UserDataSet user) throws SQLException{
        String query = String.format(UPDATE_USER, user.getId(), user.getName(), user.getAge(), user.getId());
        System.out.println(query);
        return executor.execUpdate(query);
    }

    public int deleteById(long id) throws SQLException{
        String query = String.format(DELETE_BY_ID, id);
        System.out.println(query);
        return executor.execUpdate(query);
    }

    public List<UserDataSet> findAll() throws SQLException{
        String query = FIND_ALL_USERS;
        return executor.execQuery(query, resultSet -> {
            List<UserDataSet> users = new ArrayList<>();
            while(!resultSet.isLast()){
                resultSet.next();
                UserDataSet newUser = new UserDataSet(resultSet.getLong(1),
                        resultSet.getString(2), resultSet.getInt(3));
                users.add(newUser);
            }
            return users;
        });
    }
}
