package com.bluespurs.bestprice.service;

import com.bluespurs.bestprice.bean.WmProductWrapper;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author lamine
 */
@Named
@ApplicationScoped
public class WalmartApiService extends AbstractStoreApiService<WmProductWrapper> {

    public WalmartApiService() {
        super("wmrestapikey");
    }
}
