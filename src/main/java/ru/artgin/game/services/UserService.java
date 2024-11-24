package ru.artgin.game.services;


import ru.artgin.game.enums.Status;
import ru.artgin.game.models.User;

import java.util.List;

public interface UserService {
    public User addUser(String name, Status status);
    public boolean existName(String name);
    public User getUser(String name);
    public User getUserByStatus(Status status);

    public Status getStatus(String name);
    public Status setStatus(String name, Status gamer);
    public boolean existsStatus(Status status);

    public List<User> getAll();
    public User getGamerUser();

    public boolean isEmpty();

}
