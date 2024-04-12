package com.mccullough.highscore.controller;

import com.mccullough.highscore.dao.UserScoreDao;
import com.mccullough.highscore.model.UserScore;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping (path = "/scores")
public class UserScoreController {

    private UserScoreDao userScoreDao;

    public UserScoreController (UserScoreDao userScoreDao) {
        this.userScoreDao = userScoreDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserScore> list(@RequestParam(defaultValue = "10") int limit) {
            return userScoreDao.getUserScores(limit);
    }

    @RequestMapping(path = "/today", method = RequestMethod.GET)
    public List<UserScore> listTodaysScores(@RequestParam(defaultValue = "10") int limit) { return userScoreDao.getUserScoresToday(limit);}

    @RequestMapping(method = RequestMethod.POST)
    public UserScore add (@RequestBody UserScore userScore) {
        return userScoreDao.createUserScore(userScore);
    }



}
