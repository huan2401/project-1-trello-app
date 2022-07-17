package com.example.projecti_trello_app_backend.security.user_detail;

import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import com.example.projecti_trello_app_backend.security.user_detail.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String loginAcc) throws UsernameNotFoundException {
           Optional<User> userOptional = userRepo.findByUserNameOrEmail(loginAcc,loginAcc);
           if(userOptional.isPresent())
               return new CustomUserDetails(userOptional.get());
           else throw new UsernameNotFoundException("Username or email not found");
    }
}
