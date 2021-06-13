package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.filter.Filter;
import com.epam.esm.util.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class CertificateNameFilter extends Filter {

    public CertificateNameFilter(String param) {
        super(param);
    }

    @Override
    List<CertificateDto> filter(List<CertificateDto> soughtList) {
        Utils.lOGGER.info("зашел в name");
        soughtList =  soughtList
                .stream()
                .filter(certificate -> certificate.getName().contains(param))
                .collect(Collectors.toList());
        if(next != null){
            Utils.lOGGER.info("not all name");
           return next.filter(soughtList);
        }else {
            Utils.lOGGER.info("all name");
            return soughtList;
        }
    }
}
