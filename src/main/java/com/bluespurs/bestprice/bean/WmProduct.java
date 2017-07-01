
package com.bluespurs.bestprice.bean;

/**
 *
 * @author lamine
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WmProduct extends ProductWrapper{
    
    @JsonProperty("items")
    @Override
   public List<Product> getProducts() {
        return super.getProducts();
    }
    
}
