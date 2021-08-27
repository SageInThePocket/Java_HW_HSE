package com.hw7.network;

public class GetDataException extends RuntimeException {

    public GetDataException(String message) {
        super(message);
    }

    public GetDataException() {
        super("");
    }
}
