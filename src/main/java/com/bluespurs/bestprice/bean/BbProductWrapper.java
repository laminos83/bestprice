package com.bluespurs.bestprice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author lamine
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BbProductWrapper extends ProductStoreWrapper<BbProduct> {

    @JsonProperty("products")
    @Override
    public List<BbProduct> getProducts() {
        return super.getProducts();
    }

}
