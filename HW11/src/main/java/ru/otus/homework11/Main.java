package ru.otus.homework11;

import ru.otus.homework11.dataSet.AddressDataSet;
import ru.otus.homework11.dataSet.PhoneDataSet;
import ru.otus.homework11.dataSet.UserDataSet;
import ru.otus.homework11.dbService.DBService;
import ru.otus.homework11.dbService.hibernateService.HibernateCachedUserDBService;
import ru.otus.homework11.dbService.hibernateService.HibernateUserDBService;
import ru.otus.homework11.dbService.jdbcService.JDBCUserDBService;

public class Main {
    public static void main(String[] args) throws Exception{
           try(DBService<UserDataSet, Long> dbService = new HibernateCachedUserDBService()){
               UserDataSet newUser = new UserDataSet("Ivan", 34);
               dbService.save(newUser);
               newUser.setAddress(new AddressDataSet("Rostov", "Stroitelei"));
               newUser.addPhone(new PhoneDataSet("987654321"));
               newUser.addPhone(new PhoneDataSet("123456789"));
               newUser.addPhone(new PhoneDataSet("456123789"));
               dbService.update(newUser);

               System.out.println("-------------------------");

               System.out.println("\nFind all:");
               System.out.println(dbService.findAll());

               System.out.println("\nFind by id:");
               UserDataSet user = dbService.findById(Long.valueOf(1));
               System.out.println(user);

               System.out.println("\nUpdate:");
               user.setAge(24);
               dbService.update(user);

               System.out.println("\nFind by id:");
               System.out.println(dbService.findById(Long.valueOf(1)));

               System.out.println("-------------------------");

               for(int i = 2; i < 20; i++){
                    dbService.save(new UserDataSet(i, String.valueOf(i), i));
               }

               System.out.println("\nFind with id > capacity (must be in the cache)");
               System.out.println(dbService.findById(Long.valueOf(15)));

               System.out.println("\nFind with id < capacity");
               System.out.println(dbService.findById(Long.valueOf(1)));

           }catch (DBException ex){
               ex.printStackTrace();
           }

    }
}
