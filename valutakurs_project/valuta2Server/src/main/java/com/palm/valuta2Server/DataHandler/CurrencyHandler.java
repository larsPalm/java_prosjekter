package com.palm.valuta2Server.DataHandler;

import com.palm.valuta2Server.Models.Currency;
import com.palm.valuta2Server.Repository.CurrencyRepository;
import org.json.JSONObject;
import java.util.*;

public class CurrencyHandler {

    public Map<String,Double> newestEach(CurrencyRepository cr){
        List<Currency> ne = cr.getNewestEachCur();
        Map<String,Double> newestEach = new HashMap<>();
        for(Currency cur: ne){
            newestEach.put(cur.getCur_name(), cur.getValue());
        }
        return newestEach;
    }

    public Map<String,Map<String,Double>> makePresentableDataV2(CurrencyRepository cr){
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

    public Map<String,Map<String,Double>> makePresentableData(CurrencyRepository cr){
        List<String> dates = cr.getAllDates();
        //Collections.sort(dates);
        Map<String,List<Currency>> rawData = new LinkedHashMap<>();
        for(String date:dates){
            rawData.put(date,cr.getAllValuesDate(date));
        }
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

    public String storeData(String payload, CurrencyRepository cr){
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
}
