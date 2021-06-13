package com.epam.esm.model;

import org.springframework.lang.NonNull;

import java.util.List;

public class Certificate {
    private final Integer id;
    private final String name;
    private final String description;
    private final Double price;
    private final Integer duration;
    private final String createDate;
    private final String lastUpdateDate;

    public Certificate(Integer id, @NonNull String name, @NonNull String description, @NonNull Double price,
                       @NonNull Integer duration, @NonNull String createDate, @NonNull String lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
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

}
