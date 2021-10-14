package com.priselkov.rest_sample.util;

import com.priselkov.rest_sample.model.User;

import java.util.LinkedList;
import java.util.List;

public class UserValidator {

    private static List<UserDataStatus> isValid(final User user) {
        List<UserDataStatus> statuses = new LinkedList<>();
        if (user.getLogin() == null || user.getLogin().equals("")) statuses.add(UserDataStatus.INVALID_LOGIN);
        if (user.getPass() == null || !PasswordValidator.isValid(user.getPass()))
            statuses.add(UserDataStatus.INVALID_PASS);
        if (user.getName() == null || user.getName().equals("")) statuses.add(UserDataStatus.INVALID_NAME);
        return statuses;
    }

    private enum UserDataStatus {
        VALID,
        INVALID_LOGIN,
        INVALID_PASS,
        INVALID_NAME
    }

    public static List<String> getDescription(final User user/*final List<UserDataStatus> statuses*/) {

        List<UserDataStatus> statuses = isValid(user);

        if (statuses.isEmpty()) return null;

        List<String> errors = new LinkedList<>();
        for (UserDataStatus status : statuses) {
            switch (status) {
                case INVALID_LOGIN:
                    errors.add("Invalid login");
                    break;
                case INVALID_PASS:
                    errors.add("Invalid password");
                    break;
                case INVALID_NAME:
                    errors.add("Invalid name");
                    break;
                default:
                    errors.add("Invalid user data");
                    break;
            }
        }
        return errors;
    }
}
