package ru.otus.homework09.dbService;

import ru.otus.homework09.ReflectionHelper;
import ru.otus.homework09.dataSet.DataSet;
import ru.otus.homework09.executor.MyExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBService implements AutoCloseable {

    private static final String TABLE_NAME = "HomeworkTable";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            "id bigint(20) NOT NULL auto_increment primary key," +
            "name varchar(255)," +
            "age int(3))";
    private static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;

    private static final String GET_DATA_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=";
    private static final String SAVE_DATA = "INSERT INTO " + TABLE_NAME + " VALUES(";
    private static final String DELETE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id=";

    Connection connection;
    MyExecutor executor;

    public DBService(){
        connection = ConnectionHelper.getH2Connection();
        executor = new MyExecutor(connection);
    }


    public void prepareTable() throws SQLException{
        executor.execUpdate(CREATE_TABLE);
    }
    public void dropTable() throws SQLException{
        executor.execUpdate(DROP_TABLE);
    }

    public <T extends DataSet> void save(T user) throws SQLException{

        String fieldsToString = ReflectionHelper.getFieldsValueToString(user);
        String newUpdateQuery = SAVE_DATA + fieldsToString;

        executor.execUpdate(newUpdateQuery);
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException{
        return executor.execQuery(GET_DATA_BY_ID + id, resultSet -> {
            resultSet.next();
            int columnsNumber = resultSet.getMetaData().getColumnCount();
            Object[] parameters = new Object[columnsNumber];
            for(int i = 0; i < parameters.length; i++){
                parameters[i] = resultSet.getObject(i + 1);
            }

            try {
                return ReflectionHelper.newInstanceWithParameters(clazz, parameters);
            }catch(NoSuchMethodException | InstantiationException | IllegalStateException | IllegalAccessException |
                    InvocationTargetException ex){
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        });
    }

    public void removeById(long id) throws SQLException{
        executor.execUpdate(DELETE_BY_ID + id);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
