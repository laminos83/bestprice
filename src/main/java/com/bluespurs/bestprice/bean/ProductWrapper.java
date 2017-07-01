package com.bluespurs.bestprice.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lamine
 */
public abstract class ProductWrapper {

    private List<Product> products;

    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public Product getLowestPriceProduct() {
        return getProducts().isEmpty() ? null : getProducts().get(0);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
}
