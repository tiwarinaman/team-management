package com.techienaman.teammanagement.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(Long id) {
        super("Couldn't find the team with id " + id);
    }

}
