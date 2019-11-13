package com.westpac.democode_review.exception;


public class ProjectNotFoundException extends Exception{
    public ProjectNotFoundException() {
        super("No Projects were found in our database");
    }
    public ProjectNotFoundException(String id) {
        super("No Project with id "+ id + " was found");
    }
}
