package HistoryAppGradleSecurity.model.entity;

import HistoryAppGradleSecurity.model.enums.PeriodEnum;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity{
    @Column(name = "title",nullable = false,unique = true)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    private UserEnt author;
    @Enumerated(EnumType.STRING)
    private PeriodEnum period;
    @DateTimeFormat
    private LocalDate created;
    @OneToMany(mappedBy = "article",fetch = FetchType.EAGER)
    private Set<Picture>pictures;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category>categories;

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Article setContent(String content) {
        this.content = content;
        return this;
    }

    public UserEnt getAuthor() {
        return author;
    }

    public Article setAuthor(UserEnt author) {
        this.author = author;
        return this;
    }

    public PeriodEnum getPeriod() {
        return period;
    }

    public Article setPeriod(PeriodEnum period) {
        this.period = period;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Article setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public Article setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
