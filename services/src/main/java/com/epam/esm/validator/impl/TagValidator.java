package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.validator.Validator;
import org.springframework.stereotype.Component;

@Component("tagValidator")
public class TagValidator implements Validator<TagDto> {

    @Override
    public void validate(TagDto tag) {
        if(tag.getName().length() <= 2) {
            throw new ValidationException("Tag length must be more than 2 characters", 40301);
        }
        if(tag.getName().length() > 20){
            throw new ValidationException("Tag length must me below 20 characters", 40301);
        }
    }
}
