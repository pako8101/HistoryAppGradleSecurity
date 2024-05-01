package HistoryAppGradleSecurity.service.impl;

import HistoryAppGradleSecurity.model.binding.UserSubscribeBindingModel;
import HistoryAppGradleSecurity.model.entity.UserEnt;
import HistoryAppGradleSecurity.model.view.UserViewModel;
import HistoryAppGradleSecurity.repository.UserRepository;
import HistoryAppGradleSecurity.service.UserService;
import HistoryAppGradleSecurity.session.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, LoggedUser loggedUser, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.loggedUser = loggedUser;
        this.modelMapper = modelMapper;
    }


    @Override
    public void subscribeUser(UserSubscribeBindingModel userSubscribeBindingModel,
                              Consumer<Authentication>successfulLoginProcessor) {
        UserEnt userEntity = new UserEnt().
                setFullName(userSubscribeBindingModel.getFullName()).
                setEmail(userSubscribeBindingModel.getEmail()).
                setPassword(passwordEncoder.encode(userSubscribeBindingModel.getPassword()));

        userRepository.save(userEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userSubscribeBindingModel.getEmail());

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
}

