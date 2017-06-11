package com.bluespurs.test.exception;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author lamine
 */
@Provider
public class ExceptionMapperProvider implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        final Response result;
        if (exception instanceof NotFoundException) {
            result = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            Logger.getLogger(ExceptionInterceptorRest.class.getName()).log(Level.SEVERE, "bad request {}", exception.getMessage());
            result = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return result;
    }
}
