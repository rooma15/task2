package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

public abstract class Filter {

    protected final String param;

    public Filter(String param){
        this.param = param;
    }

    abstract List<CertificateDto> filter(List<CertificateDto> soughtList);
    public Filter next;
    Filter setNext(Filter next){
        this.next = next;
        return this.next;
    }

}
