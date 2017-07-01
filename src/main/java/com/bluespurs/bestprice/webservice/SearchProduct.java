package com.bluespurs.bestprice.webservice;

import com.bluespurs.bestprice.bean.Product;
import com.bluespurs.bestprice.exception.CatchRestException;
import com.bluespurs.bestprice.exception.ProductNotFoundException;
import com.bluespurs.bestprice.exception.ValidationException;
import com.bluespurs.bestprice.service.ProductComparatorService;
import com.bluespurs.bestprice.util.Loggable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author lamine
 *
 * Rest web service class SearchProduct contains method to find best price
 * product comparing between Walmart and best buy
 */
@Path("product")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@CatchRestException
@Loggable

public class SearchProduct {

    @Inject
    private ProductComparatorService productComparatorService;

    /**
     * <b>Description : search lowest price product by name</b>
     * <ul>
     * <li>http method : get</li>
     * <li>url :http://localhost:8080/bluespurs_test-1.0/rs/product/search?name=$name</li>
     * </ul>
     *
     * 
     * @param productName : keyword to search product by name
     * @return <b>Response json format example</b>
     * <br/>
     * {"productName": "Dynexâ„¢ - Case for AppleÂ® iPhoneÂ® 6 - Green/White","bestPrice": 1.337391, "currency": "CAD", "location": "bestbuy" }
     * @throws com.bluespurs.bestprice.exception.ValidationException
     */
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response productByBestPrice(@QueryParam("name") final String productName) throws ValidationException, ProductNotFoundException {
        
        Product lowestProduct = productComparatorService.findBestPriceProductByName(productName);
        
        return Response.ok(lowestProduct).build();

    }

}
