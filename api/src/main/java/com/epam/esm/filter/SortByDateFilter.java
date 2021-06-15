package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.util.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SortByDateFilter extends Filter{

    public SortByDateFilter(String param) {
        super(param);
    }

    @Override
    List<CertificateDto> filter(List<CertificateDto> soughtList) {
        Comparator<CertificateDto> comparator = (cert1, cert2) -> {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                Date date1 = df.parse(cert1.getCreateDate().toString());
                Date date2 = df.parse(cert2.getCreateDate().toString());
                int compareResult = date1.compareTo(date2);
                return param.equalsIgnoreCase("desc") ? -compareResult : compareResult;
            } catch (ParseException e) {
                Utils.lOGGER.info(e.getMessage());
            }
            return 0;
        };
        return soughtList.stream().sorted(comparator).collect(Collectors.toList());
    }
}
