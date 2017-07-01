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

    public static void validateProductSearchInput(final String productName) throws ValidationException {

        if (productName == null || productName.isEmpty()) {
            throw new ValidationException("name is mandatory !");
        }
        // only valid chars
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        if (p.matcher(productName).find()) {
            throw new ValidationException("Invalid input !");
        }
    }

    public static Product getLowestPriceProduct(Product p1, Product p2) {

        Product bestPriceProdcut = null;

        try {

            String outputLog = MessageFormat.format("Product p1 :{0} ### Product p2 :{1}", p1, p2);
            Logger.getLogger(Product.class.getName()).log(Level.FINE, outputLog);

            if (p1 == null) {
                return p2;
            }
            if (p2 == null) {
                return p1;
            }
            bestPriceProdcut = p1.getSalePrice() < p2.getSalePrice() ? p1 : p2;
           
            return bestPriceProdcut;
            
        } finally {
            Logger.getLogger(Product.class.getName()).log(Level.FINE, "Best price product {0}:", bestPriceProdcut);
        }
    }

    public static Product getProductWithNewCurrency(Product p, final String urlRateExchange, final String currencyToConvert) throws ValidationException {

        if (p == null) {
            throw new ValidationException("product is null !");
        }

        if (urlRateExchange == null || urlRateExchange.isEmpty()) {
            throw new ValidationException("Url is Mandatory !");
        }
        if (currencyToConvert == null || currencyToConvert.isEmpty()) {
            throw new ValidationException("Url is Mandatory !");
        }

        p.setCurrency(currencyToConvert);

        String requestRateUrl = String.format(urlRateExchange, currencyToConvert);
        CurrencyObject currencyObject = RestUtil.buildBeanFromUrlRequest(requestRateUrl, CurrencyObject.class);

        double rate = 0;
        if (currencyObject != null) {
            rate = currencyObject.getRates().get(currencyToConvert);
        }
        p.setSalePrice(rate * p.getSalePrice());

        return p;
    }

}
