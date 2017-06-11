package com.bluespurs.test.service;

import com.bluespurs.test.bean.BbProduct;
import com.bluespurs.test.bean.CurrencyObject;
import com.bluespurs.test.bean.Product;
import com.bluespurs.test.bean.WmProduct;
import com.bluespurs.test.exception.ValidationException;
import com.bluespurs.test.util.Loggable;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        loadProperties();
    }

    /**
     *
     * @param productName used to search product by name
     * @return lowest price product by comparing between lowest price walmart
     * and lowest price best buy
     * @throws com.bluespurs.test.exception.ValidationException validate params
     */
    // we need manufacturer references to compare between products, name is not sufficient condition !
    // or we can do comparison if we filter search with many other attributes
    // when we search only by name we can get : Iphone Blue Case compared, Iphone 7 , Iphone 6 charger etc..
    // so we are comparing price between different product !
    public Product findBestPriceProductByName(String productName) throws ValidationException {

        if (productName == null || productName.isEmpty()) {
            throw new ValidationException("name is mandatory !");
        }
        // only valid chars
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        if (p.matcher(productName).find()) {
            throw new ValidationException("Invalid input !");
        }

        String wmRestApikey = (String) restApiProperties.get("wmrestapikey");
        String bbRestApikey = (String) restApiProperties.get("bbrestapikey");

        String WmSearchUrl = String.format(wmRestApikey, productName);
        String bbSearchUrl = String.format(bbRestApikey, productName);

        WmProduct wmProduct = buildBeanFromUrlRequest(WmSearchUrl, WmProduct.class);
        BbProduct bbProduct = buildBeanFromUrlRequest(bbSearchUrl, BbProduct.class);

        Product wmLowestProduct = null;
        if (wmProduct != null) {
            wmLowestProduct = wmProduct.getLowestPriceProduct();
        }
        Product bbLowestProduct = null;
        if (bbProduct != null) {
            bbLowestProduct = bbProduct.getLowestPriceProduct();
        }

        Product lowestPriceProduct = Product.getLowestPriceProduct(wmLowestProduct, bbLowestProduct);
        if (lowestPriceProduct == null) {
            lowestPriceProduct = new Product();
            lowestPriceProduct.setName("No product found !");
        } else {
            lowestPriceProduct = getProductWithNewCurrency(lowestPriceProduct, CURRENCY_REF);
        }
        return lowestPriceProduct;
    }

    private Product getProductWithNewCurrency(Product p, String currency) {

        p.setCurrency(currency);
        Double salePriceConverted = convertAmountToCurrency(p.getSalePrice(), currency);
        p.setSalePrice(salePriceConverted);

        return p;
    }

    // used because walmart amount with USD and Best buy inspecified currency (assumption USD)
    private Double convertAmountToCurrency(Double amount, String currencyToConvert) {

        String requestRateUrl = String.format(RATE_EXCHANGE_URL, currencyToConvert);
        CurrencyObject currencyObject = buildBeanFromUrlRequest(requestRateUrl, CurrencyObject.class);

        double rate = 0;
        if (currencyObject != null) {
            rate = currencyObject.getRates().get(currencyToConvert);
        }
        return rate * amount;
    }

    private <T extends Object> T buildBeanFromUrlRequest(final String searchUrlRequest, Class<T> clazz) {

        T mappedObject = null;

        try {

            ObjectMapper mapper = new ObjectMapper();
            InputStream istreamResult = restUrlCaller(searchUrlRequest);
            if (istreamResult != null) {
                mappedObject = mapper.readValue(new InputStreamReader(istreamResult), clazz);
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return mappedObject;
    }

    private InputStream restUrlCaller(final String requestedUrl) {

        Logger.getLogger(ProductComparatorService.class.getName()).log(Level.FINE, "requested url:{0}", requestedUrl);

        InputStream is = null;

        try {
            // escape illegals caracters
            URL url = new URL(requestedUrl);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            url = uri.toURL();
            //end

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                Logger.getLogger(ProductComparatorService.class.getName()).log(Level.SEVERE, "Erreur de connexion HTTP:{0}", conn.getResponseCode());

            }
            is = conn.getInputStream();
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
        return is;
    }

    private void loadProperties() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("restapikey.properties");
            restApiProperties = new Properties();
            restApiProperties.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
