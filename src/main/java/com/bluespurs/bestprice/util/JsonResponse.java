package com.bluespurs.bestprice.util;

import com.bluespurs.bestprice.dto.ProductDTO;

/**
 *
 * @author lamine
 */
public class JsonResponse {

    public enum Status {
        OK, KO
    }

    private String message="";
    private Status status;
    private final String version = "1.0";
    private ProductDTO product;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

}
