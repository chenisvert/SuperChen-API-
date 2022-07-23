package com.example.superchen.service;


import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;

public interface RequestService {

    public String https(String urls) throws IOException, Exception;
    public String http(String urls) throws IOException, Exception;
    String request (URL url) throws ProtocolException, Exception;
}
