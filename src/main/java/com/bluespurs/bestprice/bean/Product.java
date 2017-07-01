package com.bluespurs.bestprice.bean;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 *
 * @author lamine
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private String name;
    private double salePrice;
    private String currency;
    private String location;

    public Product() {
    }

    @JsonGetter("productName")
    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter("bestPrice")
    public double getSalePrice() {
        return salePrice;
    }

    @JsonSetter("salePrice")
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    @JsonGetter("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonSetter("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonGetter("location")
    public String getLocation() {
        return location;
    }

    @JsonSetter("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Product{" + "name=" + name + ", salePrice=" + salePrice + ", currency=" + currency + ", location=" + location + '}';
    }

}
