package ru.otus.homework10;

import ru.otus.homework10.dataSet.AddressDataSet;
import ru.otus.homework10.dataSet.PhoneDataSet;
import ru.otus.homework10.dataSet.UserDataSet;
import ru.otus.homework10.dbService.DBService;
import ru.otus.homework10.dbService.hibernateService.HibernateUserDBService;
import ru.otus.homework10.dbService.jdbcService.JDBCUserDBService;

public class Main {
    public static void main(String[] args) throws Exception{

            try (DBService<UserDataSet, Long> dbService = new JDBCUserDBService()) {
                dbService.deleteTable();
                dbService.prepareTable();
                dbService.save(new UserDataSet("Alexander", 25));
                dbService.save(new UserDataSet("Kirill", 35));
                dbService.save(new UserDataSet( 44, "Dmitriy", 28));
                System.out.println(dbService.findAll());
                UserDataSet user = dbService.findById(Long.valueOf(2));
                user.setAge(45);
                dbService.update(user);
                System.out.println(dbService.findAll());
                dbService.deleteById(Long.valueOf(1));
                System.out.println(dbService.findAll());
                dbService.deleteTable();
            }catch(DBException ex){
                ex.printStackTrace();
            }

           try(DBService<UserDataSet, Long> dbService = new HibernateUserDBService()){
               AddressDataSet address = new AddressDataSet("Moscow", "Stroitelei");
               PhoneDataSet phone1 = new PhoneDataSet("987654321");
               PhoneDataSet phone2 = new PhoneDataSet("123456789");
               PhoneDataSet phone3 = new PhoneDataSet("456123789");

               UserDataSet user = new UserDataSet(35, "Ivan", 34);
               dbService.save(user);
               UserDataSet takenUser = dbService.findById(Long.valueOf(35));
               takenUser.setAddress(address);
               takenUser.addPhone(phone1);
               takenUser.addPhone(phone2);
               takenUser.addPhone(phone3);

               dbService.update(takenUser);
               System.out.println(dbService.findAll());
           }catch (DBException ex){
               ex.printStackTrace();
           }

    }
}
