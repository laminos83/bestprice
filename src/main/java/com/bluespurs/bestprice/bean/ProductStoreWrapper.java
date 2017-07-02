package com.bluespurs.bestprice.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lamine
 */
public abstract class ProductStoreWrapper<T extends AbstractProduct> {

    private List<T> products;

    public List<T> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public AbstractProduct getLowestPriceProduct() {
        return getProducts().isEmpty() ? null : getProducts().get(0);
    }

    public void setProducts(List<T> products) {
        this.products = products;
    }

}
