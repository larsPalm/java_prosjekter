package com.palm.ValutaServer;

import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetStoredData{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GetStoredData(){}

    public Map<String, Double> getNewestValues(){
        String datoQuery = "SELECT max(dato) FROM endpoints_currencyvalue";
        String valueQuery = "SELECT * FROM endpoints_currencyvalue WHERE dato = ?";
        return null;
    }
} 