package com.epam.esm.web;

import com.epam.esm.model.Tag;

import java.util.List;

public interface TagRepository extends EntityRepository<Tag>{
    Tag retrieveByName(String name);
    List<Tag> retrieveTagsByCertificateId(int certId);
}
