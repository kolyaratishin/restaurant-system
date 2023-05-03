package com.restaurant.exception;

public class EntityByIdNotFoundException extends RuntimeException {
    public EntityByIdNotFoundException(Long id) {
        super("Entity with id=" + id + " is not found");
    }
}
