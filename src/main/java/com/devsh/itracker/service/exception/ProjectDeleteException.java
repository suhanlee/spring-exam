package com.devsh.itracker.service.exception;

public class ProjectDeleteException extends RuntimeException {
    public ProjectDeleteException() { super("Could not delete project."); }

}
