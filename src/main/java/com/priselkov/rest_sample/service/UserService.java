package com.priselkov.rest_sample.service;

import com.priselkov.rest_sample.model.RoleArray;
import com.priselkov.rest_sample.model.User;
import com.priselkov.rest_sample.response.BasicResponse;

import java.util.List;

public interface UserService {
    //Получать список пользователей из БД (без ролей)
    List<User> getAllUsers();

    //Получать конкретного пользователя (с его ролями) из БД
    User getUserWithRoles(String login);

    //Удалять пользователя в БД
    BasicResponse deleteUser(String login);

    //Добавлять нового пользователя с ролями в БД.
    BasicResponse addUserWithRoles(User newUser);

    //Редактировать существующего пользователя в БД.
    BasicResponse updateUser(String userLogin, User user);

    //Если в запросе на редактирование передан массив ролей,
    //система должна обновить список ролей пользователя в БД - новые привязки добавить,
    //неактуальные привязки удалить.
    BasicResponse updateUserRoles(String userLogin, RoleArray roleArray);
}
