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

}
