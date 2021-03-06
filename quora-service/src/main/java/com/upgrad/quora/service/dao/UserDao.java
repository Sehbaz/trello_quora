package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity createUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }

    public UserEntity getUserByEmail(final String email) {
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter(
                    "email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UserEntity getUserByUserName(final String userName) {
        try {
            return entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter(
                    "userName", userName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


    public UserAuthEntity createAuthToken(UserAuthEntity userAuthTokenEntity) {
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    public void updateUser(final UserEntity userEntity) {
        entityManager.merge(userEntity);
    }

    public UserAuthEntity getUserByAccessToken(String accessToken) {
        try
        {
            return entityManager.createQuery("select u from UserAuthEntity u where u.accessToken = :accessToken",UserAuthEntity.class).setParameter("accessToken",accessToken).getSingleResult();
        }
        catch (NoResultException nre)
        {
            return null;
        }
    }

    public void updateUserAuthEntity(final UserAuthEntity userAuthEntity) {
        entityManager.merge(userAuthEntity);
    }

    public UserEntity getUserByUuid(final String Uuid) {
        try {
            return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter(
                    "uuid", Uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public void deleteUserUuid(UserEntity userEntity,final String Uuid) {
        entityManager.remove(userEntity);
        entityManager.createQuery("delete from UserEntity u where u.uuid = :uuid").setParameter(
                "uuid", Uuid);
    }

}
