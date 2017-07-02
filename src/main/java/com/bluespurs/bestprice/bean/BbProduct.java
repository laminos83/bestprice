package com.bluespurs.bestprice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author lamine
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BbProduct extends AbstractProduct {

    public BbProduct() {
        super("Best buy");
    }

}
