package com.example.treeleef1.exception;

public class ServerException extends Exception {
    private final static String SERVER_ERROR_MSG = "Server Error! Please contact to administration. ";

    public ServerException() {
        super(SERVER_ERROR_MSG);
    }
}
