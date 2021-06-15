package com.epam.esm.web;

import com.epam.esm.dto.CertificateDto;

import java.util.List;
import java.util.Map;

public interface CertificateService extends Service<CertificateDto> {
    CertificateDto partitialUpdate(Map<String, Object> paramsMap, int id);
    CertificateDto update(CertificateDto certificate);
    List<CertificateDto> findByTagName(String name);
}
