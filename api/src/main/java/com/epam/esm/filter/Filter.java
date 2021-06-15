package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

public abstract class Filter {

    protected final String param;
    public Filter next;

    public Filter(String param){
        this.param = param;
    }
    abstract List<CertificateDto> filter(List<CertificateDto> soughtList);
    void setNext(Filter next){
        this.next = next;
    }

}
