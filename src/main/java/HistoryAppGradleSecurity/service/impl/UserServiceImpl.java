package HistoryAppGradleSecurity.service.impl;

import HistoryAppGradleSecurity.model.binding.UserLoginBindingModel;
import HistoryAppGradleSecurity.model.binding.UserSubscribeBindingModel;
import HistoryAppGradleSecurity.model.entity.UserEnt;
import HistoryAppGradleSecurity.model.entity.UserRoleEnt;
import HistoryAppGradleSecurity.model.enums.UserRoleEnum;
import HistoryAppGradleSecurity.model.service.UserServiceModel;
import HistoryAppGradleSecurity.model.view.UserViewModel;
import HistoryAppGradleSecurity.repository.UserRepository;
import HistoryAppGradleSecurity.repository.UserRoleRepository;
import HistoryAppGradleSecurity.service.UserService;
import HistoryAppGradleSecurity.session.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final LoggedUser loggedUser;
    private final ModelMapper modelMapper;

    private final UserRoleRepository userRoleRepository;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, LoggedUser loggedUser, ModelMapper modelMapper, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.loggedUser = loggedUser;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public void subscribeUser(UserSubscribeBindingModel userSubscribeBindingModel,
                              Consumer<Authentication>successfulLoginProcessor) {
        UserEnt userEntity = new UserEnt().
                setUsername(userSubscribeBindingModel.getUsername()).
                setFullName(userSubscribeBindingModel.getFullName()).
                setEmail(userSubscribeBindingModel.getEmail()).
                setPassword(passwordEncoder.encode(userSubscribeBindingModel.getPassword()));

        userRepository.save(userEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userSubscribeBindingModel.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);
    }

    @Override
    public UserEnt findCurrentUserLoginEntity() {
        return userRepository.findByUsername(loggedUser.getUsername())
                .orElse(null);
    }

    @Override
    public UserViewModel getUserProfile() {
        String username = loggedUser.getUsername();
        UserEnt user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " was not found!"));

        return modelMapper.map(user,UserViewModel.class);
    }

    @Override
    public boolean userNameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void registerAndLoginUser(UserServiceModel userServiceModel) {
        UserEnt newUser = modelMapper.map(userServiceModel, UserEnt.class);
        newUser.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        UserRoleEnt userRole = userRoleRepository.
                findUserRoleEntityByRole(UserRoleEnum.MODERATOR).orElseThrow(
                        () -> new IllegalStateException("USER role not found. Please seed the roles."));

        newUser.addRole(userRole);

        newUser = userRepository.save(newUser);

        UserDetails principal = userDetailsService.loadUserByUsername(newUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void login(UserLoginBindingModel userLoginBindingModel) {
        String username = userLoginBindingModel.getUsername();

        UserEnt user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with username: [" + username + "] is not present"));

        boolean passwordMatch = passwordEncoder.matches(userLoginBindingModel.getPassword(),
                user.getPassword());

        if (!passwordMatch){
            throw new IllegalStateException("User entered incorrect password");
        }

        loggedUser.setUsername(user.getUsername())
                .setLogged(true)
                .setRoles(user.getRoles());
    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username,password)
                .map(user -> modelMapper.map(user,UserServiceModel.class))
                .orElse(null);
    }
}


