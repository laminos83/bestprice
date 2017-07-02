package com.bluespurs.bestprice.bean;

/**
 *
 * @author lamine
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WmProductWrapper extends AbstractProductStoreWrapper<WmProduct> {

    @JsonProperty("items")
    @Override
    public List<WmProduct> getProducts() {
        return super.getProducts();
    }

}
