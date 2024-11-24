package ru.artgin.game.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.artgin.game.enums.Status;
import ru.artgin.game.models.User;
import ru.artgin.game.repositoty.UserRepository;

import java.util.List;


/**
 *It is the class, which is which implements the logic of using user's information.
 * In this class methods of finding users information by name, by status and getting
 * parts of information about users are implemented.
 * Also, there is a method of add user information to the table.
 */

@Service
public class UserServiceImpl implements UserService  {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(String name, Status status) {
        User user = new User();
        user.setName(name);
        user.setStatus(status);
        userRepository.save(user);
        return user;
    }

    public boolean existName(String name){
        return userRepository.existsByName(name);
    }

    @Override
    public User getUser(String name) {
        return userRepository.findUserByName(name);
    }

    @Override
    public User getUserByStatus(Status status) {
       return  userRepository.findUserByStatus(status).stream().findFirst().get();
    }

    @Override
    public Status getStatus(String name) {
        return userRepository.findUserByName(name).getStatus();
    }

    @Override
    public Status setStatus(String name, Status status) {
        User user = userRepository.findUserByName(name);
        user.setStatus(status);
        userRepository.save(user);
        return status;
    }

    @Override
    public boolean existsStatus(Status status) {
        if(userRepository.findUserByStatus(status).isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getGamerUser() {
        return userRepository.findUserByStatus(Status.GAMER)
                .stream().findFirst().get();
    }

    @Override
    public boolean isEmpty() {
        return userRepository.existsBy();
    }


}
