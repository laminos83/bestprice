package com.bluespurs.bestprice.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lamine
 */
public abstract class ProductWrapper {

    private List<Product> products;
    protected String location;

    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.forEach((product) -> {
            product.setLocation(location);
        });
        return products;
    }

    public Product getLowestPriceProduct() {
        return getProducts().isEmpty() ? null : getProducts().get(0);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
