package com.thinkhr.external.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.thinkhr.external.api.db.entities.User;
import com.thinkhr.external.api.repositories.UserRepository;

/**
 * User Controller for performing operations
 * related with User object.
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-01
 * 
 */
@RestController
@RequestMapping(path="/v1")
public class UserController {
	
    @Autowired
    UserRepository userRepo;
    
    /**
     * Get all users from repository
     * @return
     * 
     */

    @GetMapping(path="/users")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }
    
    /**
     * Get user with given id from repository
     * @param id user id
     * @return User object
     * 
     */
    @GetMapping("users/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public ResponseEntity<User> getById(@PathVariable(name="id",value = "id") Long id) {
        User user = userRepo.findOne(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    /**
     * Create a new User in repository
     * @param user to be created 
     * @param builder builder object
     * @return null
     * 
     */
    @PostMapping("users")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<Void> create(@RequestBody User user, UriComponentsBuilder builder) {
        userRepo.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}

