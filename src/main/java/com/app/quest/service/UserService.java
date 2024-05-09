package com.app.quest.service;

import com.app.quest.config.PasswordEncoderConfig;
import com.app.quest.event.UserCreateEvent;
import com.app.quest.exception.NotFoundException;
import com.app.quest.model.entity.User;
import com.app.quest.model.enums.Role;
import com.app.quest.model.request.UserCreateRequest;
import com.app.quest.model.request.UserUpdateRequest;
import com.app.quest.model.response.UserResponse;
import com.app.quest.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final RabbitTemplate rabbitTemplate;

    public UserService(UserRepository userRepository,  PasswordEncoderConfig passwordEncoderConfig,RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.rabbitTemplate = rabbitTemplate;
    }


    public UserResponse createUser(UserCreateRequest user){
        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setUsername(user.username());
        newUser.setPassword(passwordEncoderConfig.bCryptPasswordEncoder().encode(user.password()));
        newUser.setAuthorities(Set.of(Role.USER));
        newUser.setAccountNonExpired(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);
        newUser.setAccountNonLocked(true);

        userRepository.save(newUser);

        UserCreateEvent userCreateEvent = new UserCreateEvent(newUser, user.email());
        rabbitTemplate.convertAndSend("default", "firstRoute", userCreateEvent);

        return UserResponse.convert(newUser);
    }

    public UserResponse update(UserUpdateRequest userUpdate){
        User update = findById(userUpdate.uuid());

        if (update != null){
            update.setUsername(userUpdate.username());
            update.setPassword(passwordEncoderConfig.bCryptPasswordEncoder().encode(userUpdate.password()));
            userRepository.save(update);
            return UserResponse.convert(update);
        }
        throw new NotFoundException("User not found");
    }

    public UserResponse getByUser(String uuid){
        User user = findById(uuid);
        return UserResponse.convert(user);
    }

    public Optional<User> getByUsername(String userName){
        return userRepository.getByUsername(userName);
    }

    public void delete(String uuid){
        User deleteUser = findById(uuid);

        userRepository.deleteById(uuid);
    }

    protected User findById(String uuid){
      return userRepository.findById(uuid).orElseThrow(() -> new NotFoundException("User couldn't be found by following User id :" + uuid));
    }
}
