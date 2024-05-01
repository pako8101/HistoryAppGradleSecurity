package HistoryAppGradleSecurity.model.service;

import HistoryAppGradleSecurity.model.entity.Picture;
import HistoryAppGradleSecurity.model.entity.UserEnt;
import HistoryAppGradleSecurity.model.enums.CategoryNameEnum;
import HistoryAppGradleSecurity.model.enums.PeriodEnum;

import java.util.Set;

public class ArticleServiceModel {
    private Long id;

    private PeriodEnum category;
    private String name;
    private UserEnt author;
    private String description;
    private Set<Picture> pictures;

    private Set<CategoryNameEnum>categories;

    public ArticleServiceModel() {
    }

    public String getDescription() {
        return description;
    }

    public ArticleServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public ArticleServiceModel setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Set<CategoryNameEnum> getCategories() {
        return categories;
    }

    public ArticleServiceModel setCategories(Set<CategoryNameEnum> categories) {
        this.categories = categories;
        return this;
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
