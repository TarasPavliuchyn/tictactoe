package com.play.tictactoe.model;

public enum Status {
    DRAW("Draw"),
    IN_PROGRESS("In progress"),
    O_WON("0 won"),
    X_WON("X won");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}