package ru.otus.homework09;

import ru.otus.homework09.dataSet.DataSet;
import ru.otus.homework09.dataSet.UserDataSet;
import ru.otus.homework09.dbService.DBService;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws Exception{
        try (DBService dbService = new DBService()){
            System.out.println("Create table if not exists...");
            dbService.prepareTable();
            try {
                System.out.println("Create UserDataSet with: id=3, name=xz, age = 42");
                DataSet userDataSet = new UserDataSet(3,"xz", 42);
                System.out.println("Add UserDataSet to database...");
                dbService.save(userDataSet);
            }catch(SQLException ex){
                ex.printStackTrace();
            }

            System.out.println("Get data with id = 3 from database...");
            UserDataSet myDataSet = dbService.load(3, UserDataSet.class);
            System.out.println(myDataSet);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
