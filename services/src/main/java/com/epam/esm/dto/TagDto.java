package com.epam.esm.dto;

import com.epam.esm.model.Tag;

public class TagDto {
    private Integer id;
    private String name;

    public TagDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDto(String name) {
        this.name = name;
    }

    public TagDto() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
