package com.bluespurs.bestprice.service;

import com.bluespurs.bestprice.bean.BbProductWrapper;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author lamine
 */
@Named
@ApplicationScoped
public class BestBuyApiService extends AbstractStoreApiService<BbProductWrapper> {

    public BestBuyApiService() {
        super("bbrestapikey");
    }

}
