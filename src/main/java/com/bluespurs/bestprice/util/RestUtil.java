package com.bluespurs.bestprice.util;

import com.bluespurs.bestprice.service.ProductComparatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lamine
 */
public class RestUtil {

    public static <T extends Object> T buildBeanFromUrlRequest(final String searchUrlRequest, Class<T> clazz) {

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

    private static InputStream restUrlCaller(final String requestedUrl) {

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
                throw new RuntimeException("HTTP Error <> 200:" + conn.getResponseCode());

            }
            is = conn.getInputStream();
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
        return is;
    }
}
