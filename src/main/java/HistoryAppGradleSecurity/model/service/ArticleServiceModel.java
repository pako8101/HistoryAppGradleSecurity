package HistoryAppGradleSecurity.model.service;

import HistoryAppGradleSecurity.model.entity.UserEnt;
import HistoryAppGradleSecurity.model.enums.PeriodEnum;

public class ArticleServiceModel {
    private Long id;

    private PeriodEnum category;
    private String name;
    private UserEnt author;

    public ArticleServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public ArticleServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public PeriodEnum getCategory() {
        return category;
    }

    public ArticleServiceModel setCategory(PeriodEnum category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public ArticleServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public UserEnt getAuthor() {
        return author;
    }

    public ArticleServiceModel setAuthor(UserEnt author) {
        this.author = author;
        return this;
    }
}
