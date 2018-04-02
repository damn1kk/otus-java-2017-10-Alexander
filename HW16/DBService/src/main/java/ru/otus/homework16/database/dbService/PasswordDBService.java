package ru.otus.homework16.database.dbService;


import ru.otus.homework16.database.dataSet.PasswordDataSet;

interface PasswordDBService extends DBService<PasswordDataSet, Long> {
    String findPasswordByLogin(String login);
}
