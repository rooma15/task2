package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("hashMapBasedCertificateValidator")
public class HashMapBasedCertificateValidator implements Validator<Map<String, Object>> {

    private Validator<TagDto> tagValidator;

    @Autowired
    @Qualifier("tagValidator")
    public void setTagDtoValidator(Validator<TagDto> tagValidator) {
        this.tagValidator = tagValidator;
    }

    @Override
    public void validate(Map<String, Object> certificateMap) {
        for(Map.Entry<String, Object> entry : certificateMap.entrySet()) {
            switch (entry.getKey()) {
                case "name": {
                    String name = entry.getValue().toString();
                    if(name.length() <= 3){
                        throw new ValidationException("Name length must be more than 3 symbols", 40301);
                    }
                    if(name.length() > 20){
                        throw new ValidationException("Name length must be below 20 symbols", 40301);
                    }
                }
                break;
                case "description": {
                    String description = entry.getValue().toString();
                    if(description.length() < 10){
                        throw new ValidationException("Description length must be more than 10 symbols", 40301);
                    }
                    if(description.length() > 120){
                        throw new ValidationException("Description length must be below 120 symbols", 40301);
                    }
                }
                break;
                case "price": {
                    try {
                        Double price = Double.parseDouble(entry.getValue().toString());
                        if(price < 0) {
                            throw new ValidationException("Field 'price' must be more than zero", 40301);
                        }
                    } catch (NumberFormatException e) {
                        throw new ValidationException("price must be only numeric format", 40301);
                    }
                }
                break;
                case "duration": {
                    try {
                        int duration = Integer.parseInt(entry.getValue().toString());
                        if(duration < 0) {
                            throw new ValidationException("duration must be more than zero", 40301);
                        }
                    } catch (NumberFormatException e) {
                        throw new ValidationException("duration must be only numeric format", 40301);
                    }
                }
                break;
                case "tags" :{
                    List<String> tags = (ArrayList<String>)entry.getValue();
                    tags.forEach(tag -> tagValidator.validate(new TagDto(tag)));
                }
            }
        }
    }
}
