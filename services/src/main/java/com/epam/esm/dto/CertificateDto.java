package com.epam.esm.dto;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Certificate;
import org.springframework.lang.NonNull;

import java.util.List;

public class CertificateDto {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDto> tags;

    public CertificateDto(Integer id, String name, String description, Double price,
                          Integer duration, String createDate, String lastUpdateDate, List<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public CertificateDto() {
    }

    public CertificateDto(String name, String description, Double price,
                          Integer duration, List<TagDto> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}
