package com.app.quest.service;

import com.app.quest.exception.NotFoundException;
import com.app.quest.model.entity.User;
import com.app.quest.model.request.UserCreateRequest;
import com.app.quest.model.request.UserUpdateRequest;
import com.app.quest.model.response.UserResponse;
import com.app.quest.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserCreateRequest user){
        User newUser = new User();
        newUser.setUsername(user.username());
        newUser.setPassword(user.password());
        userRepository.save(newUser);

        return UserResponse.convert(newUser);
    }

    public UserResponse update(UserUpdateRequest userUpdate){
        User update = findById(userUpdate.uuid());

        if (update != null){
            update.setUsername(userUpdate.username());
            update.setPassword(userUpdate.password());
            userRepository.save(update);
            return UserResponse.convert(update);
        }
        throw new NotFoundException("User not found");
    }

    public UserResponse getByUser(String uuid){
        User user = findById(uuid);
        return UserResponse.convert(user);
    }

    public void delete(String uuid){
        User deleteUser = findById(uuid);

        userRepository.deleteById(uuid);
    }

    protected User findById(String uuid){
      return userRepository.findById(uuid).orElseThrow(() -> new NotFoundException("User couldn't be found by following User id :" + uuid));
    }
}
