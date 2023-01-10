package com.midwesttape.project.challengeapplication.service;

import com.midwesttape.project.challengeapplication.model.Address;
import com.midwesttape.project.challengeapplication.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final JdbcTemplate template;


 /*   public User user(final Long userId) {
        try {

            return template.queryForObject(
                "select " +
                    "id, " +
                    "firstName, " +
                    "lastName, " +
                    "username, " +
                    "password " +
                    "from User " +
                    "where id = ?",
                new BeanPropertyRowMapper<>(User.class),
                userId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }*/
    public User user(final Long userId) {
        try {

            return template.queryForObject(
                "select * " +
                    "from User u, address a" +
                    "where id = ?",
                new BeanPropertyRowMapper<>(User.class),
                userId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }
   /* public Address address(final Long addressId) {
        try {

            return template.queryForObject(
                "select " +
                    "id, " +
                    "address1, " +
                    "address2, " +
                    "city, " +
                    "state " +
                    "postal " +
                    "from Address " +
                    "where id = ?",
                new BeanPropertyRowMapper<>(Address.class),
                addressId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }*/


}
