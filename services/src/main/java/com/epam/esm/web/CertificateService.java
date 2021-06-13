package com.epam.esm.web;

import com.epam.esm.dto.CertificateDto;

import java.util.List;
import java.util.Map;

public interface CertificateService extends Service<CertificateDto> {
    CertificateDto update(Map<String, Object> paramsMap, int id);
    CertificateDto update(CertificateDto certificate);
    List<CertificateDto> retrieveCertificatesByTagName(String name);
}
