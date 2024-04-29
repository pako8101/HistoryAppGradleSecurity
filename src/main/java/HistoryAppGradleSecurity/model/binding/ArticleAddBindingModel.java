package HistoryAppGradleSecurity.model.binding;

import HistoryAppGradleSecurity.model.enums.CategoryNameEnum;
import HistoryAppGradleSecurity.model.enums.PeriodEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class ArticleAddBindingModel {
    @Size(min = 3,max = 30, message = "Article title must be between 3 and 30 characters")
    @NotEmpty
    private String title;
    @Size(min = 10,message = "Article content must be more then 10 characters!")
    private String content;
    @NotNull
    private PeriodEnum category;
    private Set<CategoryNameEnum> categories;

    public ArticleAddBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public ArticleAddBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ArticleAddBindingModel setContent(String content) {
        this.content = content;
        return this;
    }

    public PeriodEnum getCategory() {
        return category;
    }

    public ArticleAddBindingModel setCategory(PeriodEnum category) {
        this.category = category;
        return this;
    }

    public Set<CategoryNameEnum> getCategories() {
        return categories;
    }

    public ArticleAddBindingModel setCategories(Set<CategoryNameEnum> categories) {
        this.categories = categories;
        return this;
    }
}
