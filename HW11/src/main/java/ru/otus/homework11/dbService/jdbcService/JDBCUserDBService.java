package ru.otus.homework11.dbService.jdbcService;

import ru.otus.homework11.DBException;
import ru.otus.homework11.dbService.jdbcService.dao.UserDataSetDAO;
import ru.otus.homework11.dataSet.UserDataSet;
import ru.otus.homework11.dbService.DBService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCUserDBService implements DBService<UserDataSet, Long> {
    private Connection connection;

    public JDBCUserDBService(){
        this.connection = ConnectionHelper.getH2Connection();
    }

    @Override
    public void prepareTable() throws DBException{
        try {
            UserDataSetDAO dao = new UserDataSetDAO(connection);
            dao.createTable();
        }catch(SQLException ex){
            System.out.println("Can not create new table");
            throw new DBException(ex);
        }
    }

    @Override
    public void deleteTable() throws DBException{
        try{
            UserDataSetDAO dao = new UserDataSetDAO(connection);
            dao.deleteTable();
        }catch(SQLException ex){
            System.out.println("Can not delete table, probably it does not exist");
            throw new DBException(ex);
        }
    }

    @Override
    public List<UserDataSet> findAll() throws DBException{
        UserDataSetDAO dao = new UserDataSetDAO(connection);
        try {
            return dao.findAll();
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    @Override
    public UserDataSet findById(Long id) throws DBException{
        try{
            connection.setAutoCommit(false);
            UserDataSetDAO dao = new UserDataSetDAO(connection);
            return dao.findById(id);
        }catch(SQLException ex){
            ex.printStackTrace();
            try {
                connection.rollback();
            }catch(SQLException e){
                e.printStackTrace();
            }
            throw new DBException(ex);

        }finally{
            try {
                connection.setAutoCommit(true);
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void save(UserDataSet user)  throws DBException{
        try{
            connection.setAutoCommit(false);
            UserDataSetDAO dao = new UserDataSetDAO(connection);
            if(user.getId() < 0){
                dao.insertUserWithoutID(user);
            }else {
                dao.insertUser(user);
            }
        }catch(SQLException ex){
            System.out.println("Probably user with this id is already exist");
            try {
                connection.rollback();
            }catch(SQLException e){
            }
            throw new DBException(ex);
        }finally{
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(UserDataSet user) throws DBException {
        try{
            UserDataSetDAO dao = new UserDataSetDAO(connection);
            dao.updateUser(user);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    @Override
    public void deleteById(Long id) throws DBException {
        try{
            UserDataSetDAO dao = new UserDataSetDAO(connection);
            dao.deleteById(id);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new DBException(ex);
        }
    }

    @Override
    public void delete(UserDataSet dataSet) throws DBException {
        try{
            connection.setAutoCommit(false);

        }catch(SQLException ex){
            ex.printStackTrace();
            throw new DBException(ex);
        }finally{
            try{
                connection.setAutoCommit(true);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close(){
        try {
            connection.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

}
