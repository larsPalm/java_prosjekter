package com.palm.ValutaServer;

import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoreData{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public StoreData(){}
} 