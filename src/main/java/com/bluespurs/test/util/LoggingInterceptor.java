package com.bluespurs.test.util;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author lamine
 */
@Loggable
@Interceptor
public class LoggingInterceptor implements Serializable {

    @AroundInvoke
    private Object intercept(InvocationContext ic) throws Exception {
        Object proceed = null;
        try {
            Object[] parameters = ic.getParameters();
            Logger.getLogger(LoggingInterceptor.class.getName()).log(Level.INFO, "### method:{0} ###", ic.getMethod().getName());
            Logger.getLogger(LoggingInterceptor.class.getName()).log(Level.INFO, "parameters:{0}", parameters);
            proceed = ic.proceed();
            return proceed;
        } finally {
            String toString = proceed != null ? proceed.toString() : "";
            Logger.getLogger(LoggingInterceptor.class.getName()).log(Level.INFO, "return:{0}", toString);
        }
    }

}
