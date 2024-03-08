package com.mccullough.highscore.dao;

import com.mccullough.highscore.model.UserScore;

import java.util.List;

public interface UserScoreDao {
    /**
     * Get a list of all scores from the datastore.
     * The list is never null. It is empty if there are no scores in the datastore.
     *
     * @return all scores as a list of UserScore objects
     */
    List<UserScore> getUserScores();

    /**
     * Get a user score from the datastore that has the given id.
     * If the id is not found, return null.
     *
     * @param id the user score id to get from the datastore
     * @return a fully populated UserScore object
     */
    UserScore getUserScoreById(int id);

    /**
     *
     * @param newUserScore the UserScore object to be added to the datastore
     * @return the updated UserScore object
     */
    UserScore createUserScore (UserScore newUserScore);

}
