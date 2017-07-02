package com.bluespurs.bestprice.service;

import com.bluespurs.bestprice.bean.AbstractProduct;
import com.bluespurs.bestprice.bean.AbstractProductStoreWrapper;
import com.bluespurs.bestprice.util.RestUtil;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.Properties;
import javax.annotation.PostConstruct;

/**
 *
 * @author lamine
 */
public abstract class AbstractStoreApiService<E extends AbstractProductStoreWrapper> {

    private Properties restApiProperties;
    private Class<E> productStoreClass;
    private final String restApiKey;

    public AbstractStoreApiService(String restApiKey) {
        this.restApiKey = restApiKey;
    }

    @PostConstruct
    public void init() {
        try {
           // resolve generic class
            productStoreClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("restapikey.properties");
            restApiProperties = new Properties();
            restApiProperties.load(inputStream);
            
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public AbstractProduct findLowestPriceProduct(final String productName) {

        String restApi = (String) restApiProperties.get(restApiKey);
        String searchUrl = String.format(restApi, productName);

        E productWrapper = RestUtil.buildBeanFromUrlRequest(searchUrl, productStoreClass);

        AbstractProduct lowestPriceProduct = null;
        if (productWrapper != null) {
            lowestPriceProduct = productWrapper.getLowestPriceProduct();
        }

        return lowestPriceProduct;
    }

}
