package com.epam.esm.util;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Certificate;

import java.util.List;

public class UtilCertificateConverter {
    static public CertificateDto convertModelToDto(Certificate certificate, List<TagDto> tags){
        return new CertificateDto(certificate.getId(),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate(), tags);
    }

    static public Certificate convertDtoToModel(CertificateDto certificate){
        return new Certificate(certificate.getId(),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate());
    }
}
