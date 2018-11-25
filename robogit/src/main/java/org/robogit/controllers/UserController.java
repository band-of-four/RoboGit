package org.robogit.controllers;

import lombok.extern.slf4j.Slf4j;
import org.robogit.domain.Controller;
import org.robogit.domain.User;
import org.robogit.repository.ControllerRepository;
import org.robogit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ControllerRepository controllerRepository;

    @GetMapping("/all/{id}")
    public User getAll(@PathVariable("id") Integer id){
        System.out.println("Controller Hello!");

        log.info("User {}", userRepository.findById(id));
        return  userRepository.findById(id).get();
    }

    @GetMapping("/controller/{id}")
    public Controller getController(@PathVariable("id") Integer id){
        System.out.println("Controller Hello!");

        return  controllerRepository.findAll().iterator().next();
    }
}
