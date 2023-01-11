package com.midwesttape.project.challengeapplication.rest;

import com.midwesttape.project.challengeapplication.model.ResponseTemplateVO;
import com.midwesttape.project.challengeapplication.model.User;
import com.midwesttape.project.challengeapplication.service.UserService;
import com.midwesttape.project.challengeapplication.service.UserDetailService;
import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @Autowired
    private UserDetailService userDetailService;

     @GetMapping("/v1/users/{userId}")
    public ResponseTemplateVO user(@PathVariable final Long userId) {
        return userDetailService.getUserWithAddress(userId);
    }

    /*     @GetMapping("/v1/users/{userId}")
    public User user(@PathVariable final Long userId) {
        return userService.user(userId);
    }*/
    @PutMapping("/v1/users/update")
    public User updateUser(@RequestBody User user) {
        return userDetailService.updateUser(user);
    }

    @PostMapping("/v1/users")
    public ResponseEntity<Object> getUsers(@RequestBody String query) {
        ExecutionResult execute = userDetailService.getGraphQL().execute(query);

        return new ResponseEntity<>(execute, HttpStatus.OK);
    }
}
