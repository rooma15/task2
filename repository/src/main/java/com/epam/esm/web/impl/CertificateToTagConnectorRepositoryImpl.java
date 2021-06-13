package com.epam.esm.web.impl;

import com.epam.esm.web.CertificateToTagConnectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CertificateToTagConnectorRepositoryImpl implements CertificateToTagConnectorRepository {

    private final String ADD_LINK = "insert into certificateTags(id, certificate_id, tag_id)" +
            "VALUES(null, ?, ?)";

    private final String DELETE_LINK = "delete from certificateTags where certificate_id=? and tag_id=?";

    private final String DELETE_LINK_BY_CERTIFICATE_ID = "delete from certificateTags where certificate_id=?";

    private final NamedParameterJdbcOperations namedJdbcOperations;

    @Autowired
    public CertificateToTagConnectorRepositoryImpl(NamedParameterJdbcOperations namedJdbcOperations){
        this.namedJdbcOperations = namedJdbcOperations;
    }

    @Override
    @Transactional
    public int makeLink(int certId, int tagId) {
        return namedJdbcOperations.getJdbcOperations().update(ADD_LINK, certId, tagId);
    }

    @Override
    @Transactional
    public int deleteLink(int certId, int tagId) {
        return namedJdbcOperations.getJdbcOperations().update(DELETE_LINK, certId, tagId);
    }

    @Override
    @Transactional
    public int deleteLink(int certId) {
        return namedJdbcOperations.getJdbcOperations().update(DELETE_LINK_BY_CERTIFICATE_ID, certId);
    }
}
