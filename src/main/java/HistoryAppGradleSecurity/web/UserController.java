package HistoryAppGradleSecurity.web;

import HistoryAppGradleSecurity.model.binding.UserLoginBindingModel;
import HistoryAppGradleSecurity.model.binding.UserSubscribeBindingModel;
import HistoryAppGradleSecurity.model.service.UserServiceModel;
import HistoryAppGradleSecurity.model.view.UserViewModel;
import HistoryAppGradleSecurity.service.UserService;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, SecurityContextRepository securityContextRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
        this.modelMapper = modelMapper;
    }


    @ModelAttribute("userSubscribeBindingModel")
    public UserSubscribeBindingModel userSubscribeBindingModel() {
        return new UserSubscribeBindingModel();
    }

    @ModelAttribute("userLoginBindingModel")
    public UserLoginBindingModel userLoginBindingModel() {
        return new UserLoginBindingModel();
    }

    @GetMapping("/subscribe")
    public String subscribe(Model model) {
        if (!model.containsAttribute("userSubscribeBindingModel")) {
            model.addAttribute("userSubscribeBindingModel", new UserSubscribeBindingModel());
        }
        return "subscribe";
    }

//    @PostMapping("/subscribe")
//    public String registerAndLoginUser(
//            @Valid UserSubscribeBindingModel subscribeBindingModel,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes) {
//
//        if (bindingResult.hasErrors() || !subscribeBindingModel.getPassword()
//                .equals(subscribeBindingModel.getConfirmPassword())) {
//            redirectAttributes.addFlashAttribute("subscribeBindingModel", subscribeBindingModel);
//            redirectAttributes.addFlashAttribute(
//                    "org.springframework.validation.BindingResult.subscribeBindingModel", bindingResult);
//
//            return "redirect:/users/subscribe";
//        }
//
//        if (userService.userNameExists(subscribeBindingModel.getUsername())) {
//            redirectAttributes.addFlashAttribute("subscribeBindingModel", subscribeBindingModel);
//            redirectAttributes.addFlashAttribute("userExistsError", true);
//
//            return "redirect:/users/subscribe";
//        }
//
//        UserServiceModel userServiceModel = modelMapper
//                .map(subscribeBindingModel, UserServiceModel.class);
//
//        userService.registerAndLoginUser(userServiceModel);
//
//        return "redirect:/home";
//    }

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

        return "redirect:/";


}
    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("isFound")) {
            model.addAttribute("isFound", true);
        }
        return "login";
    }




@PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter
        .SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter
                .SPRING_SECURITY_FORM_USERNAME_KEY,username);
        redirectAttributes.addFlashAttribute("bad_credentials",true);
//    redirectAttributes.addFlashAttribute("bad_credentials", true);
//    redirectAttributes.addFlashAttribute("username", username);
        return "redirect:/users/login";

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
