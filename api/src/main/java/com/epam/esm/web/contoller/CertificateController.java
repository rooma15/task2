package com.epam.esm.web.contoller;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.filter.*;
import com.epam.esm.web.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;


    @Autowired
    public CertificateController(CertificateService certificateService){
        this.certificateService = certificateService;
    }

    @RequestMapping(method=GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateDto> getCertificates(@RequestParam(required = false) String tagName,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) String description,
                                       @RequestParam(required = false) String sortByDate,
                                       @RequestParam(required = false) String sortByName) {

        FilterManager filterManager = new FilterManager(certificateService.retrieveAll());

        if(tagName != null) {
            filterManager.add(new TagNameFilter(tagName));

        }
        if(name != null) {
            filterManager.add(new CertificateNameFilter(name));
        }
        if(description != null) {
            filterManager.add(new DescriptionCertificateFilter(description));
        }
        if(sortByDate != null) {
            filterManager.add(new SortByDateFilter(sortByDate));
        }

        if(sortByName != null) {
            filterManager.add(new SortByNameFilter(sortByName));
        }
        filterManager.start();
        return filterManager.getCertificates();
    }

    @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto getCertificate(@PathVariable int id){
        return certificateService.retrieveOne(id);
    }

    @RequestMapping(value = "/{id}", method = PUT, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto update(@PathVariable int id, @RequestBody CertificateDto certificate){
        certificate.setId(id);
        return certificateService.update(certificate);
    }


    @RequestMapping(value = "/{id}", method = PATCH, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updatePart(@PathVariable int id, @RequestBody HashMap<String, Object> map){
        return certificateService.update(map, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = POST, consumes = "application/json", produces = "application/json")
    public CertificateDto create( @RequestBody CertificateDto certificate){
        return certificateService.create(certificate);
    }

    @RequestMapping(value = "/{id}",  method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        certificateService.delete(id);
    }

}
