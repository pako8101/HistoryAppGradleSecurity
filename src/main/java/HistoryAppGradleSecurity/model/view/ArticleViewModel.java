package HistoryAppGradleSecurity.model.view;

public class ArticleViewModel {

    private  Long id;

    private String title;

    private String pictureUrl;

    private String content;

    public ArticleViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ArticleViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticleViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public ArticleViewModel setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ArticleViewModel setContent(String content) {
        this.content = content;
        return this;
    }
}
