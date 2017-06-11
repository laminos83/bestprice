
package com.bluespurs.test.bean;

/**
 *
 * @author lamine
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WmProduct {
    
    @JsonProperty("items")
    private List<Product> products;
    
    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.forEach((product) -> {
            product.setLocation("walmart");
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
