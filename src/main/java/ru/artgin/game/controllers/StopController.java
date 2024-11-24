package ru.artgin.game.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.artgin.game.enums.Status;
import ru.artgin.game.services.UserService;

/**
 *It is the controller class, which implements stop of the game.
 *
 * User can stop the game, passing tne nickname of Gamer. If nickname is not exist,
 * nothing happens. If user this nickname is Guest, also nothing happens.In other situations,
 * status of nickname will be changed to Guest. In means, what he can't pass the game answers to the game part.
 *
 * REST API:
 *  GET /stop/{name} - get the stop of the game
 *                  name - nickname of user, whose game should be stopped
 */

@RestController
@RequestMapping(value ="/stop")
public class StopController {

    private UserService userService;

    @Autowired
    public StopController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{name}")
    public String stop(@PathVariable String name){
        if(!userService.existName(name)) {
            return "Gamer with this name doesn't played!";

        }else if(userService.getStatus(name).equals(Status.GAMER)){
            userService.setStatus(name, Status.GUEST);
            return "Game stopped!";

        }else {
            return "You didn't start the game!";
        }
    }
}
