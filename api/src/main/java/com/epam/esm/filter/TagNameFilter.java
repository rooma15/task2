package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.util.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class TagNameFilter extends Filter {
    public TagNameFilter(String param) {
        super(param);
    }

    @Override
    List<CertificateDto> filter(List<CertificateDto> soughtList) {
        Utils.lOGGER.info("зашел в tag");
        soughtList = soughtList
                .stream()
                .filter(certificate -> certificate.getTags()
                        .stream().anyMatch(tag -> tag.getName().contains(param)))
                .collect(Collectors.toList());
        if(next != null) {
            Utils.lOGGER.info("not all in tag");
            return next.filter(soughtList);
        } else {
            Utils.lOGGER.info("all in tag");
            return soughtList;
        }
    }
}
