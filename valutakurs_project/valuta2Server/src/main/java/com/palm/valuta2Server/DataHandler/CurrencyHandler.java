package com.palm.valuta2Server.DataHandler;

import com.palm.valuta2Server.Models.Currency;
import com.palm.valuta2Server.Repository.CurrencyRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CurrencyHandler {

    @Autowired
    private CurrencyRepository cr;

    public Map<String,Double> newestEach(){
        List<Currency> ne = cr.getNewestEachCur();
        Map<String,Double> newestEach = new HashMap<>();
        for(Currency cur: ne){
            newestEach.put(cur.getCur_name(), cur.getValue());
        }
        return newestEach;
    }

    public Map<String,Map<String,Double>> makePresentableDataV2(){
        List<String> bases = cr.getBaseCurs();
        Collections.sort(bases);
        Map<String,List<Currency>> rawData = new LinkedHashMap<>();
        for(String base:bases){
            rawData.put(base,cr.getAllValuesCurrency(base));
        }
        Map<String,Map<String,Double>> presentableData = new LinkedHashMap<>();
        for(Map.Entry<String, List<Currency>> set :
                rawData.entrySet()){
            Map<String,Double> data = new TreeMap<>();
            for(Currency cur:set.getValue()){
                data.put(cur.getDato(), cur.getValue());
            }
            presentableData.put(set.getKey(),data);
        }
        return sortHashmap(presentableData);
    }

    public Map<String,Map<String,Double>> makePresentableData(){
        List<String> dates = getDates();
        //Collections.sort(dates);
        Map<String,List<Currency>> rawData = getRawData(dates);
        Map<String,Map<String,Double>> presentableData = new LinkedHashMap<>();
        for(Map.Entry<String, List<Currency>> set :
                rawData.entrySet()){
            Map<String,Double> data = new TreeMap<>();
            for(Currency cur:set.getValue()){
                data.put(cur.getCur_name(), cur.getValue());
            }
            presentableData.put(set.getKey(),data);
        }
        return sortHashmap(presentableData);
    }

    public Map<String, Object> makePresentableDataUserBaseline(String base){
        List<String> dates = getDates();
        //Collections.sort(dates);
        Map<String,List<Currency>> rawData = getRawData(dates);
        Map<String,Map<String,Double>> presentableData = new LinkedHashMap<>();
        for(Map.Entry<String, List<Currency>> set :
                rawData.entrySet()){
            Map<String,Double> data = new TreeMap<>();
            for(Currency cur:set.getValue()){
                Currency baselineValue = cr.getSample(cur.getDato(), base.toUpperCase());
                data.put(cur.getCur_name(), (cur.getValue()/baselineValue.getValue()));
            }
            presentableData.put(set.getKey(),data);
        }
        return sortHashmap2(presentableData);
    }
    public Map<String, Object> makePresentableDataUserBaselineInterval(String base, String fromValue, String toValue){
        List<String> dates = cr.getAllDatesInInterval(fromValue, toValue);
        Map<String,List<Currency>> rawData = getRawData(dates);
        Map<String,Map<String,Double>> presentableData = new LinkedHashMap<>();
        for(Map.Entry<String, List<Currency>> set :
                rawData.entrySet()){
            Map<String,Double> data = new TreeMap<>();
            for(Currency cur:set.getValue()){
                Currency baselineValue = cr.getSample(cur.getDato(), base.toUpperCase());
                data.put(cur.getCur_name(), (cur.getValue()/baselineValue.getValue()));
            }
            presentableData.put(set.getKey(),data);
        }
        return sortHashmap2(presentableData);
    }

    public String storeData(String payload){
        try{
            JSONObject jsonObj = new JSONObject((String) payload);
            String dato = jsonObj.getString("dato");
            String base = jsonObj.getString("cur_name");
            Currency cur = cr.getSample(dato, base);
            if (cur == null){
                cr.save(new Currency(dato, base, jsonObj.getDouble("value")));
                return "Insert OK";
            }else{
                cur.setCur_name(base);
                cur.setDato(dato);
                cur.setValue(jsonObj.getDouble("value"));
                cr.save(cur);
                return "update OK";
            }
            //return "OK";
        }catch (Exception e){return "NOT OK";}
    }

    private Map<String,Map<String,Double>> sortHashmap(Map<String,Map<String,Double>> inputData){
        //taken from geeks for geeks: https://www.geeksforgeeks.org/sorting-hashmap-according-key-value-java/
        TreeMap<String, Map<String,Double>> sorted = new TreeMap<>(inputData);
        Map<String,Map<String,Double>> sortedHashmap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String,Double>> entry : sorted.entrySet()){
            //System.out.println("dato= "+entry.getKey());
            sortedHashmap.put(entry.getKey(),entry.getValue());
        }
        return sortedHashmap;
    }

    private Map<String,Object> sortHashmap2(Map<String,Map<String,Double>> inputData){
        //taken from geeks for geeks: https://www.geeksforgeeks.org/sorting-hashmap-according-key-value-java/
        TreeMap<String, Map<String,Double>> sorted = new TreeMap<>(inputData);
        Map<String,Object> sortedHashmap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String,Double>> entry : sorted.entrySet()){
            //System.out.println("dato= "+entry.getKey());
            sortedHashmap.put(entry.getKey(),entry.getValue());
        }
        return sortedHashmap;
    }

    private List<String >getDates(){
        return cr.getAllDates();
    }

    private List<String> getDatesInInterval( String toValue, String fromValue){
        return cr.getAllDatesInInterval(fromValue,toValue);
    }

    private Map<String,List<Currency>> getRawData(List<String> dates){
        Map<String,List<Currency>> rawData = new LinkedHashMap<>();
        for(String date:dates){
            rawData.put(date,cr.getAllValuesDate(date));
        }
        return rawData;
    }

    public boolean insideInterval(String date){
        return date.compareTo(cr.getOldestDate()) >= 0 && date.compareTo(cr.getNewestDate())<=0;
    }

    public boolean validDateFormat(String date){
        String format = "yyyy-mm-dd";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
        try {
            dateFormatter.parse(date);
            return true;
        } catch (Exception e){return false;}
    }

    public boolean validCurName(String base){
        for(String element: cr.getBaseCurs()){
            if(base.toUpperCase().equals(element)){
                return true;
            }
        }
        return false;
    }
}
