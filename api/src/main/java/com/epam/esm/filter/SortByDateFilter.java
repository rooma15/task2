package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.util.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SortByDateFilter extends Filter {

  public SortByDateFilter(String param) {
    super(param);
  }

  private final Comparator<CertificateDto> comparator =
      (cert1, cert2) -> {
        try {
          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
          Date date1 = dateFormat.parse(cert1.getCreateDate());
          Date date2 = dateFormat.parse(cert2.getCreateDate());
          int compareResult = date1.compareTo(date2);
          return param.equalsIgnoreCase("desc") ? -compareResult : compareResult;
        } catch (ParseException e) {
          Util.lOGGER.info(e.getMessage());
        }
        return 0;
      };

  @Override
  List<CertificateDto> filter(List<CertificateDto> soughtList) {
    return soughtList.stream().sorted(comparator).collect(Collectors.toList());
  }
}
