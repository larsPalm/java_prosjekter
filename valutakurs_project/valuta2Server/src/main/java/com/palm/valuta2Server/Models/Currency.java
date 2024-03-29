package com.palm.valuta2Server.Models;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name= "endpoints_currencyvalue")
@NamedNativeQuery(name="Currency.getAllCurrencyValues",
        query = "SELECT * FROM endpoints_currencyvalue",
        resultClass = Currency.class
)
@NamedNativeQuery(name="Currency.getAllValuesInInterval",
        query = "SELECT * FROM endpoints_currencyvalue WHERE dato > :fromValue AND dato < :toValue"
)
@NamedNativeQuery(name="Currency.getAllValuesInIntervalOneCurrency",
        query = "SELECT * FROM endpoints_currencyvalue WHERE dato > :fromValue AND " +
                "dato < :toValue AND cur_name= :curName"
)
@NamedNativeQuery(name="Currency.getAllDatesInInterval",
        query = "SELECT DISTINCT dato FROM endpoints_currencyvalue WHERE dato > :fromValue AND " +
                "dato < :toValue"
)
@NamedNativeQuery(name="Currency.getAllValuesInIntervalDoneProcessed",
        query = "SELECT value/(SELECT c.value FROM endpoints_currencyvalue c WHERE c.dato = :dato AND c.cur_name = :base) FROM endpoints_currencyvalue WHERE cur_name =:fromValue AND dato =:dato"
)
public class Currency {

    public Currency(){}
    public Currency(String dato, String base, double value){
        this.dato =dato;
        this.cur_name = base;
        this.value = value;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false, unique = false)
    String dato;

    @Column(nullable = false, unique = false)
    String cur_name;

    @Column(nullable = false, unique = false)
    Double value;

    @Override
    public String toString(){
        return "cur name: "+cur_name+", dato: "+dato+", value: "+value;
    }
}
