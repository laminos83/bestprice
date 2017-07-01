package com.bluespurs.bestprice.util;

import com.bluespurs.bestprice.bean.CurrencyObject;
import com.bluespurs.bestprice.bean.Product;
import com.bluespurs.bestprice.exception.ValidationException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author lamine
 */
public class ProductUtil {
    
    private final static String PATTERN_INVALID_INPUT="[^A-Za-z0-9]";

    public static void validateProductSearchInput(final String productName) throws ValidationException {

        if (productName == null || productName.isEmpty()) {
            throw new ValidationException("name is mandatory !");
        }
        // only valid chars
        Pattern p = Pattern.compile(PATTERN_INVALID_INPUT);
        if (p.matcher(productName).find()) {
            throw new ValidationException("Invalid input !");
        }
    }

    public static Product getLowestPriceProduct(Product FirstProduct, Product SecondeProduct) {

        Product bestPriceProdcut = null;

        try {

            String outputLog = MessageFormat.format("First Product :{0} ### Seconde Product :{1}", FirstProduct, SecondeProduct);
            Logger.getLogger(Product.class.getName()).log(Level.FINE, outputLog);

            if (FirstProduct == null) {
                bestPriceProdcut = SecondeProduct;
            } else if (SecondeProduct == null) {
                bestPriceProdcut = FirstProduct;
            } else {
                bestPriceProdcut = FirstProduct.getSalePrice() < SecondeProduct.getSalePrice() ? FirstProduct : SecondeProduct;
            }
            return bestPriceProdcut;

        } finally {
            Logger.getLogger(Product.class.getName()).log(Level.FINE, "Best price product {0}:", bestPriceProdcut);
        }
    }

    public static Product getProductWithNewCurrency(Product product, final String urlRateExchange, final String currencyToConvert) throws ValidationException {

        if (product == null) {
            throw new ValidationException("Product is null !");
        }

        if (urlRateExchange == null || urlRateExchange.isEmpty()) {
            throw new ValidationException("Url is Mandatory !");
        }
        if (currencyToConvert == null || currencyToConvert.isEmpty()) {
            throw new ValidationException("Url is Mandatory !");
        }

        product.setCurrency(currencyToConvert);

        String requestRateUrl = String.format(urlRateExchange, currencyToConvert);
        CurrencyObject currencyObject = RestUtil.buildBeanFromUrlRequest(requestRateUrl, CurrencyObject.class);

        double rate = 0;
        if (currencyObject != null) {
            rate = currencyObject.getRates().get(currencyToConvert);
        }
        product.setSalePrice(rate * product.getSalePrice());

        return product;
    }

}
