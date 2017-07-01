package com.bluespurs.bestprice.service;

import com.bluespurs.bestprice.bean.BbProduct;
import com.bluespurs.bestprice.bean.Product;
import com.bluespurs.bestprice.bean.WmProduct;
import com.bluespurs.bestprice.exception.ProductNotFoundException;
import com.bluespurs.bestprice.exception.ValidationException;
import com.bluespurs.bestprice.util.Loggable;
import com.bluespurs.bestprice.util.ProductUtil;
import com.bluespurs.bestprice.util.RestUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author lamine
 */
@RequestScoped
@Loggable

public class ProductComparatorService {

    private static final String RATE_EXCHANGE_URL = "http://api.fixer.io/latest?base=USD";
    private static final String CURRENCY_REF = "CAD";

    Properties restApiProperties;

    public ProductComparatorService() {
    }

    @PostConstruct
    public void init() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("restapikey.properties");
            restApiProperties = new Properties();
            restApiProperties.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param productName used to search product by name
     * @return lowest price product by comparing between lowest price walmart
     * and lowest price best buy
     * @throws com.bluespurs.bestprice.exception.ValidationException validate
     * params
     * @throws com.bluespurs.bestprice.exception.ProductNotFoundException
     */
    // we need manufacturer references to compare between products, name is not sufficient condition !
    // or we can do comparison if we filter search with many other attributes
    // when we search only by name we can get : Iphone Blue Case compared, Iphone 7 , Iphone 6 charger etc..
    // so we are comparing price between different product !
    public Product findBestPriceProductByName(final String productName) throws ValidationException, ProductNotFoundException {

        ProductUtil.validateProductSearchInput(productName);

        String wmRestApikey = (String) restApiProperties.get("wmrestapikey");
        String bbRestApikey = (String) restApiProperties.get("bbrestapikey");

        String WmSearchUrl = String.format(wmRestApikey, productName);
        String bbSearchUrl = String.format(bbRestApikey, productName);

        WmProduct wmProduct = RestUtil.buildBeanFromUrlRequest(WmSearchUrl, WmProduct.class);
        BbProduct bbProduct = RestUtil.buildBeanFromUrlRequest(bbSearchUrl, BbProduct.class);

        Product wmLowestProduct = null;

        if (wmProduct != null) {
            wmLowestProduct = wmProduct.getLowestPriceProduct();
            if (wmLowestProduct != null) {
                wmLowestProduct.setLocation("walmart");
            }
        }
        Product bbLowestProduct = null;

        if (bbProduct != null) {
            bbLowestProduct = bbProduct.getLowestPriceProduct();
            if (bbLowestProduct != null) {
                bbLowestProduct.setLocation("bestbuy");
            }
        }

        Product lowestPriceProduct = ProductUtil.getLowestPriceProduct(wmLowestProduct, bbLowestProduct);
       
        if (lowestPriceProduct == null) {
            throw new ProductNotFoundException("No product found !");
        }
        lowestPriceProduct = ProductUtil.getProductWithNewCurrency(lowestPriceProduct, RATE_EXCHANGE_URL, CURRENCY_REF);

        return lowestPriceProduct;
    }

}
