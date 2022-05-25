package com.palm.ValutaServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.*;

@RestController
public class Controller {

	@GetMapping(value="/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
    @GetMapping(value="/dummy")
    public String dummy(){
        return "fucking dummy";
    }
    @GetMapping(value="/more",produces = "application/json")
    @ResponseBody
    public Map<String, Double> gjson(){
        Map<String, Double> rtn = new LinkedHashMap<>();
        rtn.put("1",12.0);
        rtn.put("2",13.0);
        rtn.put("3",14.0);
        rtn.put("4",15.0);
        return rtn;
    }

}
