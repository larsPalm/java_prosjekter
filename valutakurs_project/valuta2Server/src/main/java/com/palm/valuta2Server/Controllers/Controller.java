package com.palm.valuta2Server.Controllers;

import com.palm.valuta2Server.Models.Currency;
import com.palm.valuta2Server.Repository.CurrencyRepository;
import com.palm.valuta2Server.DataHandler.CurrencyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class Controller {

    @Autowired
    private CurrencyRepository cr;

    @Autowired
    private CurrencyHandler currencyHandler;

    @GetMapping(value="/",produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> index() {
        Map<String,String> info = new HashMap<>();
        info.put("name","Lars Palm");
        info.put("Github","https://github.com/larsPalm");
        info.put("Country","Norway");
        info.put("when","Summer 2022");
        info.put("builds on","api_server written in python");
        Map<String, Object> retur = new TreeMap<>();
        retur.put("created by", info);
        retur.put("supported currencies",cr.getBaseCurs());
        retur.put("info","prototype, not end-product");
        retur.put("urls",new String[]{"http://127.0.0.1:8080/get_info/", "http://localhost:8080/get_info/"});
        return retur;
    }

    @GetMapping(value="/new")
    public String getBases(){
        List<Currency> currencies = cr.findAll();
        System.out.println("size of "+currencies.size());
        String cur1 = cr.getNewestDate();
        return String.valueOf(currencies.size());
    }
    @GetMapping(value="/newestDate")
    public String newestDate(){
        return cr.getNewestDate();
    }
    @GetMapping(value="/oldestDate")
    public String oldestDate(){
        return cr.getOldestDate();
    }
    @GetMapping(value="/newestEach")
    public Map<String,Double> getNewestEach(){
        return currencyHandler.newestEach();
    }
    @GetMapping("/allDates")
    public List<String> allDates(){
        return cr.getAllDates();
    }
    @GetMapping("/getAllBasecur")
    public List<String> getBaseCurs(){
        return cr.getBaseCurs();
    }
    @GetMapping(value="/allData",produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Map<String,Double>> getAllDataV2(){
        return currencyHandler.makePresentableDataV2();
    }
    @GetMapping(value="/get_info",produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Map<String,Double>> getAllData(){
        return currencyHandler.makePresentableData();
    }
    @PostMapping("/insert_data")
    public String storeData(@RequestBody String payload){
        //System.out.println(payload);
        return currencyHandler.storeData(payload);
    }
    @GetMapping(value="/allValues",produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> allDataFromBaseline(@RequestParam("base") String baseline){
        if(!currencyHandler.validCurName(baseline)){
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("message","wanted baseline is not supported");
            errorMap.put("code","baseline not found");
            errorMap.put("reason","baseline may be spelled wrong");
            return errorMap;
        }
        return currencyHandler.makePresentableDataUserBaseline(baseline);
    }

    @GetMapping(value="/allValuesInterval",produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> allDataFromBaselineInterval(@RequestParam("base") String baseline,
                                                           @RequestParam("fromValue") String fromValue,
                                                           @RequestParam("toValue") String toValue){
        Map<String,Object> errorMap = new HashMap<>();
        if(!currencyHandler.validCurName(baseline)){
            errorMap.put("baseMessage","wanted baseline is not supported");
            errorMap.put("baseCode","baseline not found");
            errorMap.put("baseReason","baseline may be spelled wrong");
            return errorMap;
        }
        if(!currencyHandler.validDateFormat(fromValue) || !currencyHandler.validDateFormat(toValue)){
            errorMap.put("baseMessage", "incorrect date format provided for toValue and/or fromValue");
            errorMap.put("dateCode","toValue and/or from were incorrect");
            errorMap.put("dateReason","make sure the values are dates and follows yyyy-mm-dd format");
        }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("info", "not implemented yet");
        return returnMap;
    }
    @GetMapping("/testInputValidation")
    public Map<String, Object> testDates(){
        Map<String,Object> theMap = new HashMap<>();
        theMap.put("invalid date format", currencyHandler.validDateFormat("2022/02/02"));
        theMap.put("valid date format", currencyHandler.validDateFormat("2022-02-02"));
        theMap.put("not a date", currencyHandler.validDateFormat("hei"));
        return theMap;
    }
}
