package com.epam.esm.web;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService extends Service<TagDto> {
    TagDto retrieveByTagName(String name);
    List<TagDto> retrieveTagsByCertificateId(int certId);
    boolean isResourceExist(String name);
}
