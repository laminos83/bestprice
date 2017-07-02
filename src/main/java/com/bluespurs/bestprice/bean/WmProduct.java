package com.bluespurs.bestprice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author lamine
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WmProduct extends AbstractProduct {

    public WmProduct() {
        super("Walmart");
    }

}
