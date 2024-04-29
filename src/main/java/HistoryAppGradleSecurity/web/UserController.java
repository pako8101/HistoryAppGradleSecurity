package HistoryAppGradleSecurity.web;

import HistoryAppGradleSecurity.model.binding.UserLoginBindingModel;
import HistoryAppGradleSecurity.model.binding.UserSubscribeBindingModel;
import HistoryAppGradleSecurity.model.view.UserViewModel;
import HistoryAppGradleSecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;


    public UserController(UserService userService, SecurityContextRepository securityContextRepository) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
    }


@ModelAttribute
    public UserSubscribeBindingModel userSubscribeBindingModel(){
        return new UserSubscribeBindingModel();
}
@ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
}

@GetMapping("/subscribe")
    public String subscribe(){
        return "subscribe";
}
@PostMapping("/subscribe")
    public String subscribeConfirm(UserSubscribeBindingModel userSubscribeBindingModel,
                                   HttpServletRequest request,
                                   HttpServletResponse response){

        userService.subscribeUser(userSubscribeBindingModel,successfulAuth ->{
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();

            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(successfulAuth);

            strategy.setContext(context);
            securityContextRepository.saveContext(context,request,response);
        });

        return "redirect:/login";


}
@GetMapping("/login")
    public String login(){
        return "login";
}
@PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter
        .SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter
                .SPRING_SECURITY_FORM_USERNAME_KEY,username);
        redirectAttributes.addFlashAttribute("bad-credentials",true);

        return "redirect:/login";

}
    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";

    }
    @GetMapping("/profile")
    public ModelAndView profile() {
        UserViewModel userViewModel = userService.getUserProfile();

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("userProfileViewModel", userViewModel);

        return modelAndView;
    }
}
