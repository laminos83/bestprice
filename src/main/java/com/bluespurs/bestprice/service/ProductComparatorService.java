package com.bluespurs.bestprice.service;

import com.bluespurs.bestprice.bean.AbstractProduct;
import com.bluespurs.bestprice.bean.AbstractProductStoreWrapper;
import com.bluespurs.bestprice.exception.ProductNotFoundException;
import com.bluespurs.bestprice.exception.ValidationException;
import com.bluespurs.bestprice.util.Loggable;
import com.bluespurs.bestprice.util.ProductUtil;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 *
 * @author lamine
 */
@ApplicationScoped
@Loggable

public class ProductComparatorService {

    private static final String RATE_EXCHANGE_URL = "http://api.fixer.io/latest?base=USD";
    private static final String CURRENCY_REF = "CAD";

    @Inject
    @Any
    Instance<AbstractStoreApiService<? extends AbstractProductStoreWrapper>> apiStoreServices;

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
    public AbstractProduct findBestPriceProductByName(final String productName) throws ValidationException, ProductNotFoundException {

        ProductUtil.validateProductSearchInput(productName);
        
        AbstractProduct bestPriceProduct = null;

        for (AbstractStoreApiService apiStoreService : apiStoreServices) {
            AbstractProduct foundProduct = apiStoreService.findLowestPriceProduct(productName);
            bestPriceProduct = ProductUtil.getLowestPriceProduct(bestPriceProduct, foundProduct);
        }

        if (bestPriceProduct == null) {
            throw new ProductNotFoundException("No product found !");
        }

        bestPriceProduct = ProductUtil.getProductWithNewCurrency(bestPriceProduct, RATE_EXCHANGE_URL, CURRENCY_REF);

        return bestPriceProduct;
    }

}
