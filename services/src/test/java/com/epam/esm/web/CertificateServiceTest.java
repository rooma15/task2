package com.epam.esm.web;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.util.UtilCertificateConverter;
import com.epam.esm.util.UtilTagConverter;
import com.epam.esm.validator.impl.CertificateValidator;
import com.epam.esm.web.impl.CertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CertificateServiceTest {

  @Mock private CertificateRepository certificateRepository;

  @Mock private TagService tagService;

  @Mock private CertificateValidator certificateValidator;


  @InjectMocks
  private CertificateServiceImpl certificateService;
  private Tag biologyTag = new Tag(38, "biology");
  private Tag chemistryTag = new Tag(37, "chemistry");
  private Tag CsharpTag = new Tag(41, "Csharp");
  private Tag dfgTag = new Tag(43, "dfg");

  Certificate cert1 =
      new Certificate(
          55, "roman", "hello roman",
              10.00, 1, "2021-06-11T02:17Z", "2021-06-11T19:57Z");
  Certificate cert2 =
      new Certificate(
          56, "andrew", "hello andrew",
              2.00, 5, "2021-06-11T02:17Z", "2021-06-11T02:17Z");


  @BeforeEach
  public void setUp(){
    MockitoAnnotations.openMocks(this);
  }


  @Test
  public void findByTagName() {
    List<CertificateDto> certificates = new ArrayList<>();
    List<Certificate> certificateModels = new ArrayList<>();
    List<TagDto> tags = new ArrayList<>();
    when(tagService.findByTagName("biology")).thenReturn(UtilTagConverter.convertModelToDto(biologyTag));
    //when(tagService.findByTagName("biology").getId()).thenReturn(38);
    certificateModels.add(cert2);
    when(certificateRepository.retrieveCertificatesByTagId(38)).thenReturn(certificateModels);
    tags.add(UtilTagConverter.convertModelToDto(biologyTag));
    tags.add(UtilTagConverter.convertModelToDto(chemistryTag));
    when(tagService.findTagsByCertificateId(56)).thenReturn(tags);
    certificates.add(UtilCertificateConverter.convertModelToDto(cert2, tags));
    assertEquals(certificates, certificateService.findByTagName("biology"));
  }
}
