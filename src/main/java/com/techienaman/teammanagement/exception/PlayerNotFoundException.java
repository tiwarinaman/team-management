package com.techienaman.teammanagement.exception;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(Long id) {
        super("Couldn't find the player with id " + id);
    }
}
