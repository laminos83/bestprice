package com.bluespurs.bestprice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

/**
 *
 * @author lamine
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyObject {

    private int timestamp;
    private String base;
    private Map<String, Double> rates;

    public CurrencyObject() {
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

}
