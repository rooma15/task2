package com.epam.esm.web;

import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

public interface Service<T> {
    T save(T EntityDto);
    List<T> findAll();
    T findById(int id);
    int delete(int id);
    default boolean isResourceExist(int id){
        try{
            findById(id);
        }catch (ResourceNotFoundException e){
            return false;
        }
        return true;
    }
}
