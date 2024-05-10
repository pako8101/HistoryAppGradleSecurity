package HistoryAppGradleSecurity.web;


import HistoryAppGradleSecurity.model.AppUserDetails;
import HistoryAppGradleSecurity.service.PictureService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    private final PictureService pictureService;

    public HomeController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal AppUserDetails appUserDetails){

        if (appUserDetails != null) {
            model.addAttribute("fullName", appUserDetails.getFullName());
            model.addAttribute("country", appUserDetails.getCountry());
        }

        model.addAttribute("pictures",pictureService.findAllUrls());

        return "index";
    }
    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping("/pages/all")
    public String all() {
        return "all";
    }

    @GetMapping("/pages/admins")
    public String admins() {
        return "admins";
    }

    @GetMapping("/pages/moderators")
    public String moderators() {
        return "moderators";
    }

}
