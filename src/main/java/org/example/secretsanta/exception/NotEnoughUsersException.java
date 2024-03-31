package org.example.secretsanta.exception;

/**
 * Кастомное исключение при проведении жеребьевки если недостаточно пользователй для ее провдения
 */
public class NotEnoughUsersException extends RuntimeException {
    public NotEnoughUsersException(String message) {
        super(message);
    }

}
