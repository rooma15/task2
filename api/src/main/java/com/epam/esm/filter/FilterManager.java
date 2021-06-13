package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.util.Utils;

import java.util.List;


public class FilterManager {
    private Filter head;
    private Filter current;
    private int size;

    private List<CertificateDto> certificates;

    public FilterManager(List<CertificateDto> certificates) {
        this.certificates = certificates;
    }

    public void add(Filter newFilter){
        if(size == 0){
            head = newFilter;
            current = newFilter;
        }else{
            Utils.lOGGER.info("добавлен " + size + " фильтр!");
            current.next = newFilter;
            current = current.next;
        }
        size++;
    }
    public void start(){
        if(head != null){
            certificates = head.filter(certificates);
        }
    }

    public List<CertificateDto> getCertificates() {
        return certificates;
    }
}
