package com.mccullough.highscore.controller;

import com.mccullough.highscore.dao.UserScoreDao;
import com.mccullough.highscore.model.UserScore;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping (path = "/scores")
public class UserScoreController {

    private UserScoreDao userScoreDao;

    public UserScoreController (UserScoreDao userScoreDao) {
        this.userScoreDao = userScoreDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserScore> list() {
            return userScoreDao.getUserScores();
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserScore add (@RequestBody UserScore userScore) {
        return userScoreDao.createUserScore(userScore);
    }



}
