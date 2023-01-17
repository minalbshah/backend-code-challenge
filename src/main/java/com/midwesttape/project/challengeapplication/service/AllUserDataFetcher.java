package com.midwesttape.project.challengeapplication.service;


import com.midwesttape.project.challengeapplication.model.User;
import com.midwesttape.project.challengeapplication.repository.UserRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllUserDataFetcher implements DataFetcher<List<User>> {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return userRepository.findAll();
    }
}