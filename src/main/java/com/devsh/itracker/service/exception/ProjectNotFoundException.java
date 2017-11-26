package com.devsh.itracker.service.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() { super("Could not found project."); }
}
