package com.bluespurs.bestprice.webservice;

import com.bluespurs.bestprice.bean.AbstractProduct;
import com.bluespurs.bestprice.dto.ProductDTO;
import com.bluespurs.bestprice.exception.CatchRestException;
import com.bluespurs.bestprice.exception.ProductNotFoundException;
import com.bluespurs.bestprice.exception.ValidationException;
import com.bluespurs.bestprice.service.ProductComparatorService;
import com.bluespurs.bestprice.util.JsonResponse;
import com.bluespurs.bestprice.util.Loggable;
import com.bluespurs.bestprice.util.ProductUtil;
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
    private JsonResponse jsonResponse;

    /**
     * <b>Description : search lowest price product by name</b>
     * <ul>
     * <li>http method : get</li>
     * <li>url
     * :http://localhost:8080/bluespurs_test-1.0/rs/product/search?name=$name</li>
     * </ul>
     *
     *
     * @param productName : keyword to search product by name
     * @return <b>Response json format example</b>
     * <br/>
     * { "message":"","status":"OK","version":"1.0","product":
     * { "productName":"Rocketfish™ - Apple MFi Certified Premium Vehicle Charger for Apple iPad, iPhone and iPod - Black",
     *   "bestPrice":6.465044000000001,
     *   "currency":"CAD",
     *   "location":"Best buy"}
     *  }
     * @throws com.bluespurs.bestprice.exception.ValidationException
     * @throws com.bluespurs.bestprice.exception.ProductNotFoundException
     */
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response productByBestPrice(@QueryParam("name") final String productName) throws ValidationException, ProductNotFoundException {

        AbstractProduct lowestProduct = productComparatorService.findBestPriceProductByName(productName);

        ProductDTO productDTO = ProductUtil.MapProductToDTO(lowestProduct);

        jsonResponse = new JsonResponse();
        jsonResponse.setProduct(productDTO);
        jsonResponse.setStatus(JsonResponse.Status.OK);

        return Response.ok(jsonResponse).build();

    }

}
