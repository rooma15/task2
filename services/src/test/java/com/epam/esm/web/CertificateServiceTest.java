package com.epam.esm.web;


import com.epam.esm.dto.CertificateDto;
import com.epam.esm.web.impl.CertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class CertificateServiceTest {


    @Mock
    private CertificateRepository certificateRepository;

    @Autowired
    @InjectMocks
    private CertificateServiceImpl certificateService;
    List<CertificateDto> certificates;

    @BeforeEach
    public void setUp(){
        certificates = new ArrayList<>();
    }

    @Test
    public void retrieveCertificateByTagNameTest(){

    }
}
