package com.epam.esm.web.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.UpdateResourceException;
import com.epam.esm.model.Certificate;
import com.epam.esm.util.Util;
import com.epam.esm.util.UtilCertificateConverter;
import com.epam.esm.validator.Validator;
import com.epam.esm.web.CertificateRepository;
import com.epam.esm.web.CertificateService;
import com.epam.esm.web.CertificateToTagConnectorRepository;
import com.epam.esm.web.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private TagService tagService;

    private CertificateToTagConnectorRepository connectorRepository;

    @Autowired
    public void setConnectorRepository(CertificateToTagConnectorRepository connectorRepository) {
        this.connectorRepository = connectorRepository;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    private Validator<CertificateDto> validator;

    @Autowired
    public void setCertificateValidator(Validator<CertificateDto> validator) {
        this.validator = validator;
    }

    private Validator<Map<String, Object>> hashMapBasedCertificateValidator;


    @Autowired
    @Qualifier("hashMapBasedCertificateValidator")
    public void setHashMapBasedCertificateValidator
            (Validator<Map<String, Object>> hashMapBasedCertificateValidator) {
        this.hashMapBasedCertificateValidator = hashMapBasedCertificateValidator;
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return elem -> seen.add(keyExtractor.apply(elem));
    }

    @Transactional
    public List<CertificateDto> findByTagName(String name) {
        int tagId = tagService.findByTagName(name).getId();
        List<Certificate> certificateModels = certificateRepository.retrieveCertificatesByTagId(tagId);
        return certificateModels
                .stream()
                .map(certificate -> UtilCertificateConverter
                        .convertModelToDto(certificate, tagService
                                .findTagsByCertificateId(certificate.getId())))
                .collect(Collectors.toList());
    }

    private List<TagDto> createNonExistentTags(CertificateDto certificateDto) {
        List<TagDto> tagDtos = certificateDto.getTags();
        List<TagDto> distinctTags = new ArrayList<>();

        if(tagDtos != null) {
            distinctTags = tagDtos.stream()
                    .filter(distinctByKey(TagDto::getName))
                    .collect(Collectors.toList());
            List<TagDto> existingTags = tagService.findAll();
            List<TagDto> newTags = distinctTags
                    .stream()
                    .filter(tag ->
                            existingTags
                                    .stream()
                                    .noneMatch(distinctTag -> tag.getName().equals(distinctTag.getName())))
                    .collect(Collectors.toList());
            newTags.forEach(tagService::save);
        } else {
            Util.lOGGER.info("tags were null");
        }
        return distinctTags;
    }

    @Override
    @Transactional
    public CertificateDto save(CertificateDto certificateDto) {
        validator.validate(certificateDto);

        certificateDto.setCreateDate(Util.getCurrentFormattedDate());
        certificateDto.setLastUpdateDate(Util.getCurrentFormattedDate());

        List<TagDto> distinctTags = createNonExistentTags(certificateDto);

        Certificate certificate = UtilCertificateConverter.convertDtoToModel(certificateDto);
        int lastId = certificateRepository.create(certificate);
        if(lastId != 0) {
            Util.lOGGER.info("started creating links");
            List<TagDto> currentCertificateTags = distinctTags
                    .stream()
                    .map(tag -> tagService.findByTagName(tag.getName())).collect(Collectors.toList());
            currentCertificateTags.forEach(tag -> connectorRepository.makeLink(lastId, tag.getId()));
            return UtilCertificateConverter.convertModelToDto(certificateRepository.retrieveOne(lastId),
                    currentCertificateTags);
        } else {
            throw new ServiceException("Something went wrong during creation of certificate on server", 50002);
        }

    }

    @Override
    @Transactional
    public List<CertificateDto> findAll() {
        return certificateRepository.retrieveAll()
                .stream()
                .map(model -> UtilCertificateConverter
                        .convertModelToDto(model, tagService.findTagsByCertificateId(model.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CertificateDto findById(int id) {
        try {
            return UtilCertificateConverter
                    .convertModelToDto(certificateRepository.retrieveOne(id),
                            tagService.findTagsByCertificateId(id));
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("certificate with id=[" + id + "] does not exist", 40402);
        }
    }

    @Override
    @Transactional
    public int delete(int id) {
        if(isResourceExist(id)){
            connectorRepository.deleteLink(id);
            return certificateRepository.delete(id);
        }else{
            throw new ResourceNotFoundException("certificate with id=[" + id + "] does not exist", 40402);
        }
    }

    @Override
    @Transactional
    public CertificateDto partitialUpdate(Map<String, Object> inputParamsMap, int id) {
        List<String> columnNames = certificateRepository.getColumnNames();
        CertificateDto certificate = findById(id);
        Map<String, Object> paramsMap = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("update certificate set ");
        for(Map.Entry<String, Object> entry : inputParamsMap.entrySet()) {
            if(columnNames.contains(entry.getKey().trim()) && !entry.getKey().equals("tags")) {
                sql.append(entry.getKey().trim() + "=:" + entry.getKey().trim() + ",");
                paramsMap.put(entry.getKey(), entry.getValue());
            }
        }
        sql.append("last_update_date=:last_update_date");
        paramsMap.put("last_update_date", Util.getCurrentFormattedDate());
        sql.append(" where id=:id;");
        paramsMap.put("id", id);
        hashMapBasedCertificateValidator.validate(paramsMap);
        List<TagDto> currentCertificateTags = new ArrayList<>();
        for(Map.Entry<String, Object> param : inputParamsMap.entrySet()) {
            if(param.getKey().equals("tags")){
                ArrayList<String> tagNames = (ArrayList<String>)param.getValue();
                List<TagDto> tags = tagNames.stream().map(TagDto::new).collect(Collectors.toList());
                certificate.setTags(tags);
                List<TagDto> tagsToBeUpdated = createNonExistentTags(certificate);
                currentCertificateTags = tagsToBeUpdated
                        .stream()
                        .map(tag -> tagService.findByTagName(tag.getName()))
                        .collect(Collectors.toList());

                connectorRepository.deleteLink(certificate.getId());
                currentCertificateTags
                        .forEach(tag -> connectorRepository.makeLink(certificate.getId(), tag.getId()));
            }
        }
        try{
            Certificate finalCertificate = certificateRepository.update(sql.toString(), paramsMap, id);
            return UtilCertificateConverter.convertModelToDto(finalCertificate, currentCertificateTags);
        }catch (UpdateResourceException e){
            throw new ServiceException("something went wrong on server while updating certificate id=" + id + ";", 50002);
        }

    }

    @Override
    @Transactional
    public CertificateDto update(CertificateDto certificate) {
        validator.validate(certificate);
        List<TagDto> tagsToBeUpdated = createNonExistentTags(certificate);
        List<TagDto> currentCertificateTags = tagsToBeUpdated
                .stream()
                .map(tag -> tagService.findByTagName(tag.getName()))
                .collect(Collectors.toList());

        CertificateDto existedCertificate = findById(certificate.getId());
        certificate.setCreateDate(existedCertificate.getCreateDate());
        certificate.setLastUpdateDate(Util.getCurrentFormattedDate());
        connectorRepository.deleteLink(certificate.getId());
        currentCertificateTags.forEach(tag -> connectorRepository.makeLink(certificate.getId(), tag.getId()));
        Certificate newModel = certificateRepository
                .update(UtilCertificateConverter.convertDtoToModel(certificate));
        return UtilCertificateConverter.convertModelToDto(newModel, currentCertificateTags);
    }

}
