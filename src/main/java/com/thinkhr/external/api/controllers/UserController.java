package com.thinkhr.external.api.controllers;

import java.util.List;

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
import com.thinkhr.external.api.exception.ApplicationException;
import com.thinkhr.external.api.exception.APIErrorCodes;
import com.thinkhr.external.api.model.UserModel;
import com.thinkhr.external.api.repositories.UserRepository;
import com.thinkhr.external.api.services.UserService;

/**
 * User Controller for performing operations
 * related with User object.
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-01
 * 
 */
@RestController
@RequestMapping(path="/v1/users")
public class UserController {
	
    @Autowired
    UserRepository userRepo;
    
    @Autowired
    UserService userService;
    
    /**
     * Get all users from repository
     * @return
     * 
     */

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public @ResponseBody
    List<UserModel> getAllUsers() {
        return userService.getAllUser();
    }
    
    /**
     * Get user with given id from repository
     * @param id user id
     * @return User object
     * 
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public ResponseEntity<User> getById(@PathVariable(name="id",value = "id") Long id) throws Exception {
        User user = userRepo.findOne(id);
        if (user == null) {
        	throw new ApplicationException(APIErrorCodes.ENTITY_NOT_FOUND, "user" , "id="+id); 
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    /**
     * Create a new User in repository
     * @param user to be created 
     * @param builder builder object
     * @return null
     * 
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<Void> create(@RequestBody User user, UriComponentsBuilder builder) {
        userRepo.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}

