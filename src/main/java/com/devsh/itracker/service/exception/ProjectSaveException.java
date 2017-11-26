package com.devsh.itracker.service.exception;

public class ProjectSaveException extends RuntimeException {
    public ProjectSaveException() { super("Could not save project."); }
}
