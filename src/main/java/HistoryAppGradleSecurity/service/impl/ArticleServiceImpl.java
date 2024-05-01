package HistoryAppGradleSecurity.service.impl;

import HistoryAppGradleSecurity.model.entity.Article;
import HistoryAppGradleSecurity.model.service.ArticleServiceModel;
import HistoryAppGradleSecurity.model.view.ArticleDetailsViewModel;
import HistoryAppGradleSecurity.model.view.ArticleViewModel;
import HistoryAppGradleSecurity.repository.ArticleRepository;
import HistoryAppGradleSecurity.service.ArticleService;
import HistoryAppGradleSecurity.service.CategoryService;
import HistoryAppGradleSecurity.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper, UserService userService, CategoryService categoryService) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public List<ArticleViewModel> findAllArticlesView() {
        return articleRepository
                .findAll()
                .stream()
                .map(article -> {
                    ArticleViewModel articleViewModel
                            = modelMapper.map(article, ArticleViewModel.class);

                    articleViewModel.setPictureUrl(article.getPictures()
                            .isEmpty() ? "images/rome.jpg" : article.getPictures().stream().findFirst()
                            .get().getUrl());
                    return articleViewModel;
                }).collect(Collectors.toList());
    }

    @Override
    public void addNewArticle(ArticleServiceModel articleServiceModel) {
        Article article = modelMapper.map(articleServiceModel,Article.class);

        article.setAuthor(userService.findCurrentUserLoginEntity());

        article.setCategories(articleServiceModel.getCategories()
                .stream()
                .map(categoryService::findCategoryByName)
                .collect(Collectors.toSet()));

        articleRepository.saveAndFlush(article);
    }

    @Override
    public ArticleDetailsViewModel findArticleBId(Long id) {
        return articleRepository.findById(id)
                .map(article -> modelMapper.map(article, ArticleDetailsViewModel.class))
                .orElse(null);
    }
}
