package com.bluespurs.bestprice.util;

import com.bluespurs.bestprice.bean.AbstractProduct;
import com.bluespurs.bestprice.bean.CurrencyObject;
import com.bluespurs.bestprice.dto.ProductDTO;
import com.bluespurs.bestprice.exception.ValidationException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 *
 * @author lamine
 */
public class ProductUtil {

    private final static String PATTERN_INVALID_INPUT = "[^A-Za-z0-9\\s	]";

    public static void validateProductSearchInput(final String productName) throws ValidationException {

        if (productName == null || productName.trim().isEmpty()) {
            throw new ValidationException("name is mandatory !");
        }
        // only valid chars
        Pattern patternInvalidChars = Pattern.compile(PATTERN_INVALID_INPUT);
        if (patternInvalidChars.matcher(productName).find()) {
            throw new ValidationException("Invalid input !");
        }
    }

    public static AbstractProduct getLowestPriceProduct(AbstractProduct firstProduct, AbstractProduct secondeProduct) {

        String outputLog = MessageFormat.format("First Product :{0} ### Seconde Product :{1}", firstProduct, secondeProduct);
        Logger.getLogger(ProductUtil.class.getName()).log(Level.FINE, outputLog);

        AbstractProduct bestPriceProdcut = null;

        if (firstProduct == null) {
            bestPriceProdcut = secondeProduct;
        } else if (secondeProduct == null) {
            bestPriceProdcut = firstProduct;
        } else {
            bestPriceProdcut = firstProduct.getSalePrice() < secondeProduct.getSalePrice() ? firstProduct : secondeProduct;
        }
        Logger.getLogger(ProductUtil.class.getName()).log(Level.FINE, "Best price product {0}:", bestPriceProdcut);

        return bestPriceProdcut;

    }

    public static AbstractProduct getProductWithNewCurrency(AbstractProduct product, final String urlRateExchange, final String currencyToConvert) throws ValidationException {

        if (product == null) {
            throw new ValidationException("Product is null !");
        }

        if (urlRateExchange == null || urlRateExchange.isEmpty()) {
            throw new ValidationException("Url is Mandatory !");
        }
        if (currencyToConvert == null || currencyToConvert.isEmpty()) {
            throw new ValidationException("Currency is Mandatory !");
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

    public static ProductDTO MapProductToDTO(AbstractProduct sourceProduct) {
        
        Mapper mapper = new DozerBeanMapper();
        
        return mapper.map(sourceProduct, ProductDTO.class);
    }

}
