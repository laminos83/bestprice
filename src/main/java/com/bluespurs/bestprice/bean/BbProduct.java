package com.bluespurs.bestprice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author lamine
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BbProduct extends ProductWrapper {

    @JsonProperty("products")
    @Override
    public List<Product> getProducts() {
        return super.getProducts();
    }

}
