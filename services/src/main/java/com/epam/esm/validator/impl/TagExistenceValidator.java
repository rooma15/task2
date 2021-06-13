package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.validator.Validator;
import com.epam.esm.web.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("tagExistenceValidator")
public class TagExistenceValidator implements Validator<TagDto> {

    private final Validator<TagDto> tagValidator;

    private TagService tagService;

    @Autowired
    public TagExistenceValidator(Validator<TagDto> tagValidator) {
        this.tagValidator = tagValidator;
    }

    @Autowired
    public void setTagService(TagService tagService){
        this.tagService = tagService;
    }

    @Override
    public void validate(TagDto tag) {
        tagValidator.validate(tag);
        if(tagService.isResourceExist(tag.getName())){
            throw new DuplicateResourceException("Tag with name + " + tag.getName() + " already exists", 40901);
        }
    }
}
