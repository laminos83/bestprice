package com.bluespurs.bestprice.bean;

import org.dozer.Mapping;

/**
 *
 * @author lamine
 */
public abstract class AbstractProduct {

    private String name;
    private double salePrice;
    private String location;
    private String currency;

    public AbstractProduct(String location) {
        this.location = location;
    }

    @Mapping("productName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Mapping("bestPrice")
    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
