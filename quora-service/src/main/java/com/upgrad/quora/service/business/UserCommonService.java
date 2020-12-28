package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCommonService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthTokenValidifierService userAuthTokenValidifierService;


    public UserEntity getUser(final String userUuid, final String authorizationToken) throws
            AuthorizationFailedException, UserNotFoundException {

        UserAuthEntity userAuthTokenEntity = userDao.getUserAuthToken(authorizationToken);
/*
        //Check if user has signed-in
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }

        //Check if user has signed-out
        if (userAuthTokenValidifierService.userSignOutStatus(authorizationToken)) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get user details");
        }

 */

        //Check if user is present
        UserEntity userEntity = userDao.getUserByUserName(userUuid);
        if (userEntity == null) {
            throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
        }

        return userEntity;
    }

}