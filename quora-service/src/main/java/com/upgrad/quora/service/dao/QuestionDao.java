package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionDao {

    @PersistenceContext
    private  EntityManager entityManager;

    public List<Question> getAllQuestionsByUser(final String uuid) {
        try {
            return entityManager.createNamedQuery("allQuestionsByUserId", Question.class).setParameter("uuid", uuid).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Question createQuestion(Question newQuestion) {
        entityManager.persist(newQuestion);
        return newQuestion;
    }

}
