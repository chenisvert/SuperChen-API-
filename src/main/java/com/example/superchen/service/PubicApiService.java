package com.example.superchen.service;

import com.example.superchen.domain.ro.Result;

import java.sql.ResultSet;

public interface PubicApiService {

    Result getImage(int id,String token);
}
