package com.epam.esm.web;

import com.epam.esm.model.Certificate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CertificateRepository extends EntityRepository<Certificate>{
    Certificate update(String sql, Map<String, Object> parameterSource, int id);
    List<Certificate> retrieveCertificatesByTagId(int tagId);
    Certificate update(Certificate certificate);
    List<String> getColumnNames();
}
