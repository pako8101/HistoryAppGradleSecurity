package HistoryAppGradleSecurity.web;


import HistoryAppGradleSecurity.model.binding.ArticleAddBindingModel;
import HistoryAppGradleSecurity.model.entity.Article;
import HistoryAppGradleSecurity.model.service.ArticleServiceModel;
import HistoryAppGradleSecurity.model.view.ArticleViewModel;
import HistoryAppGradleSecurity.repository.ArticleRepository;
import HistoryAppGradleSecurity.repository.UserRepository;
import HistoryAppGradleSecurity.service.ArticleService;
import HistoryAppGradleSecurity.session.LoggedUser;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final LoggedUser loggedUser;
    private final ModelMapper modelMapper;
private final UserRepository userRepository;
private final ArticleRepository articleRepository;

    public ArticleController(ArticleService articleService,
                             LoggedUser loggedUser, ModelMapper modelMapper, UserRepository userRepository, ArticleRepository articleRepository) {
        this.articleService = articleService;
        this.loggedUser = loggedUser;

        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @ModelAttribute
    public ArticleAddBindingModel articleAddBindingModel() {
        return new ArticleAddBindingModel();
    }

    @GetMapping("/all")
    public String allArticles(Model model) {

        List<ArticleViewModel> articleViewModelList =
                articleService.findAllArticlesView();
        model.addAttribute("articles", articleViewModelList);

        return "articles";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article== null) throw  new NoSuchElementException();

        model.addAttribute("article",
                articleService.findArticleBId(id));

        return "article-details";
    }

    @GetMapping("/add")
    public String add() {
        if (loggedUser.getUsername() == null) {
            return "redirect:/users/login";

        }
        return "add-article";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid ArticleAddBindingModel articleAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if (!loggedUser.isLogged()){
            throw  new IllegalArgumentException();
        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("articleAddBindingModel",articleAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework" +
                    ".validation.BindingResult" +
                    ".routeAddBindingModel", bindingResult);
            return "redirect:add";
        }
        ArticleServiceModel articleServiceModel = modelMapper.map(articleAddBindingModel, ArticleServiceModel.class);

        articleService.addNewArticle(articleServiceModel);

        return "redirect:all";



    }
}
