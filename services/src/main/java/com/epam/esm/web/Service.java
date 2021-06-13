package com.epam.esm.web;

import com.epam.esm.exception.ResourceExistenceException;

import java.util.List;
import java.util.NoSuchElementException;

public interface Service<T> {
    T create(T EntityDto);
    List<T> retrieveAll();
    T retrieveOne(int id);
    int delete(int id);
    default boolean isResourceExist(int id){
        try{
            retrieveOne(id);
        }catch (ResourceExistenceException e){
            return false;
        }
        return true;
    }
}
