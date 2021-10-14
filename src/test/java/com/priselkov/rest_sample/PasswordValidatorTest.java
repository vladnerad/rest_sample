package com.priselkov.rest_sample;

import com.priselkov.rest_sample.util.PasswordValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordValidatorTest {

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("validPasswordProvider")
    void test_password_regex_valid(String password) {
        assertTrue(PasswordValidator.isValid(password));
    }

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("invalidPasswordProvider")
    void test_password_regex_invalid(String password) {
        assertFalse(PasswordValidator.isValid(password));
    }

    static Stream<String> validPasswordProvider() {
        return Stream.of(
                "passworD1",
                "passwor1D",
                "Password1",
                "pa1sswoRd",
                "pa1ss234woRd",
                "pA1ss234woRd"
        );
    }

    static Stream<String> invalidPasswordProvider() {
        return Stream.of(
                "password",
                "passw4ord",
                "pasSword",
                "paSSword",
                "passw45ord",
                "________",
                " ",
                "");
    }
}
