package com.epam.esm.validator;

public interface Validator<T> {
    abstract void validate(T entity);
}
