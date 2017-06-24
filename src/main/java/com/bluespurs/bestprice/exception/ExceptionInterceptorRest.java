package com.bluespurs.bestprice.exception;

import com.bluespurs.bestprice.bean.Product;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

/**
 * @author lamine
 */
@Interceptor
@CatchRestException
public class ExceptionInterceptorRest implements Serializable {

    @AroundInvoke
    public Object catchException(InvocationContext ic) throws Exception {
        // should create Json wrapper for product and add attribute message, version, status etc for better handling client side
        // just reuse name to show message to respect the specification provided.
        Product p = null;
        try {
            return ic.proceed();

        } catch (ValidationException | ProductNotFoundException e) {
            p = new Product();
            p.setName(e.getMessage());
            return Response.ok(p).build();
        } catch (Exception e) {
            Logger.getLogger(ExceptionInterceptorRest.class.getName()).log(Level.SEVERE, "###Technical error###:", e);

            p = new Product();
            p.setName("Technical error ! please contact administrator ");
            return Response.ok(p).build();
        }
    }

}
