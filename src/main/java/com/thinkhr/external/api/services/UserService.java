package com.thinkhr.external.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkhr.external.api.repositories.UserRepository;

/**
* The UserService class provides a collection of all
* services related with users
*
* @author  Surabhi Bhawsar
* @since   2017-11-01 
*/

@Service
public class UserService {
	
    @Autowired	
    private UserRepository userRepository;

}