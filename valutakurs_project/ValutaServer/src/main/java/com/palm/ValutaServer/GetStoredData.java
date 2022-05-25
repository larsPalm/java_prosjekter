package com.palm.ValutaServer;

import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class GetStoredData{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GetStoredData(){}
} 