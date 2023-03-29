package org.example.Exception;

public class DuplicateUserIdException extends RuntimeException{
    public DuplicateUserIdException(Throwable cause){
        super(cause);
    }
}