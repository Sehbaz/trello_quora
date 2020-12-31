package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity createAnswer(final String questionUuid, final AnswerEntity answer,
                                     final String authorizationToken) throws AuthorizationFailedException, InvalidQuestionException {

        UserAuthEntity userAuthEntity = userDao.getUserByAccessToken(authorizationToken);
        if (userAuthEntity != null) {

            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime loggedOutTime = userAuthEntity.getLogoutAt();
            final long difference = now.compareTo(loggedOutTime);

            if (difference < 0) {
                long userId = userAuthEntity.getUser().getId();
                QuestionEntity existingQuestion = questionDao.getQuestionByUuid(questionUuid);

                if (existingQuestion != null) {

                        answer.setQuestion(existingQuestion);
                        answer.setUuid(UUID.randomUUID().toString());
                        answer.setUser(userAuthEntity.getUser());
                        answer.setDate(now);

                        return answerDao.createAnswer(answer);
                }
                throw new InvalidQuestionException("QUES-001", "The question entered is invalid");
            }
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to post an answer");
        }
        throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
    }


    public List<AnswerEntity> getAllAnswersForQuestionId(String q_uuid) {
        return answerDao.getAllAnswersForQuestionId(q_uuid);
    }
}
