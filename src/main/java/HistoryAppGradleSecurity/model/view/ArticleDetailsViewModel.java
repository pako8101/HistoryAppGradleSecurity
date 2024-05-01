package HistoryAppGradleSecurity.model.view;

import HistoryAppGradleSecurity.model.entity.Picture;
import HistoryAppGradleSecurity.model.enums.PeriodEnum;

import java.util.Set;

public class ArticleDetailsViewModel {

    private PeriodEnum period;

    private String title;

    private String content;

    private Set<Picture>pictures;

    public ArticleDetailsViewModel() {
    }

    public PeriodEnum getPeriod() {
        return period;
    }

    public ArticleDetailsViewModel setPeriod(PeriodEnum period) {
        this.period = period;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticleDetailsViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ArticleDetailsViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public ArticleDetailsViewModel setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }
}
