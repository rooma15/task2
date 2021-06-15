package com.epam.esm.web.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.Tag;
import com.epam.esm.util.UtilTagConverter;
import com.epam.esm.validator.Validator;
import com.epam.esm.web.TagRepository;
import com.epam.esm.web.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    private Validator<TagDto> tagDtoValidator;

    @Autowired
    @Qualifier("tagExistenceValidator")
    public void setTagDtoValidator(Validator<TagDto> tagDtoValidator) {
        this.tagDtoValidator = tagDtoValidator;
    }

    @Override
    @Transactional
    public TagDto save(TagDto tagDto) {
        tagDtoValidator.validate(tagDto);
        Tag tag = UtilTagConverter.convertDtoToModel(tagDto);
        int lastId = tagRepository.create(tag);
        if(lastId != 0){
            return findById(lastId);
        }else {
            throw new ServiceException("Something went wrong while creating tag on server", 50001);
        }
    }

    @Override
    public boolean isResourceExist(String name) {
        try {
            findByTagName(name);
            return true;
        }catch (ResourceNotFoundException e){
            return false;
        }
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.retrieveAll().stream()
                .map(UtilTagConverter::convertModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int delete(int id) {
        if(isResourceExist(id)){
            return tagRepository.delete(id);
        }else {
            throw new ResourceNotFoundException("Tag with id = " + id + " does not exist", 40401);
        }
    }

    @Override
    public TagDto findById(int id) {
        try {
            return UtilTagConverter.convertModelToDto(tagRepository.retrieveOne(id));
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Tag with id = " + id + " does not exist", 40401);
        }
    }

    public TagDto findByTagName(String name){
        try {
            return UtilTagConverter.convertModelToDto(tagRepository.retrieveByName(name));
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Tag with name = " + name + " does not exist", 40401);
        }

    }

    @Override
    public List<TagDto> findTagsByCertificateId(int certId) {
        return tagRepository.retrieveTagsByCertificateId(certId)
                .stream()
                .map(UtilTagConverter::convertModelToDto)
                .collect(Collectors.toList());
    }
}
