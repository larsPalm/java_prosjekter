package com.palm.valuta2Server.Repository;

import com.palm.valuta2Server.Models.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import javax.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public  interface CurrencyRepository  extends JpaRepository<Currency, Long>{

    @Query("SELECT max(dato) FROM endpoints_currencyvalue")
    String getNewestDate();

    @Query("SELECT min(dato) FROM endpoints_currencyvalue")
    String getOldestDate();

    @Query("SELECT DISTINCT ec.dato FROM endpoints_currencyvalue ec")
    List<String> getAllDates();

    @Query("SELECT ec FROM endpoints_currencyvalue ec Where ec.dato = (SELECT max(dato) FROM endpoints_currencyvalue)")
    List<Currency> getNewestEachCur();

    @Query("SELECT ec FROM endpoints_currencyvalue ec WHERE ec.cur_name = :cur_name")
    List<Currency> getAllValuesCurrency(@Param("cur_name") String name);

    @Query("SELECT DISTINCT ec.cur_name from endpoints_currencyvalue ec")
    List<String> getBaseCurs();

    @Query("SELECT ec FROM endpoints_currencyvalue ec WHERE ec.dato = :dato")
    List<Currency> getAllValuesDate(@Param("dato") String dato);

    @Query("SELECT ec FROM endpoints_currencyvalue ec WHERE ec.dato = :dato AND ec.cur_name =:cur_name")
    Currency getSample(@Param("dato") String dato, @Param("cur_name") String name);

    List<Currency> getAllCurrencyValues();

    List<Currency> getAllValuesInInterval(String fromValue, String toValue);

    List<Currency> getAllValuesInIntervalOneCurrency(String fromValue, String toValue, String curName);

    List<String> getAllDatesInInterval(String fromValue, String toValue);

}


