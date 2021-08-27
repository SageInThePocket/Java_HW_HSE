package com.hw7;

public class Request {
    String requestType;
    String obj;
    String objPath;

    public Request(String request) {
        if (!checkRequest(request))
            throw new IllegalArgumentException("Invalid request");

        String[] requestSplit = request.split(" ");
        requestType = requestSplit[0];
        obj = requestSplit[1];
        objPath = obj.equals("DATA") ? "" : requestSplit[2];
    }

    private static boolean checkRequest(String request) {
        String[] requestSplit = request.split(" ");
        return requestSplit.length >= 2 && requestSplit.length <= 3 &&
                (requestSplit[0].equals("ADD") || requestSplit[0].equals("GET")) &&
                (requestSplit[1].equals("DATA") && requestSplit.length == 2
                        || requestSplit.length == 3 && requestSplit[1].equals("FILE"));
    }
}
