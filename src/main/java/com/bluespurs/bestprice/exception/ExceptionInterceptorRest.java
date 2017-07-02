package com.bluespurs.bestprice.exception;

import com.bluespurs.bestprice.util.JsonResponse;
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

    JsonResponse jsonResponse;
    private String message;

    @AroundInvoke
    public Object catchException(InvocationContext ic) throws Exception {
        try {
            return ic.proceed();

        } catch (ValidationException | ProductNotFoundException e) {
            message = e.getMessage();
        } catch (Exception e) {
            Logger.getLogger(ExceptionInterceptorRest.class.getName()).log(Level.SEVERE, "###Technical error###:", e);
            message = "Technical error ! please contact administrator ";
        }
        jsonResponse= new JsonResponse();
        jsonResponse.setMessage(message);
        jsonResponse.setStatus(JsonResponse.Status.KO);
        return Response.ok(jsonResponse).build();
    }

}
