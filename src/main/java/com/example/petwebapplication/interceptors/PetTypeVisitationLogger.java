package com.example.petwebapplication.interceptors;


import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Interceptor
@LoggedInvocation
public class PetTypeVisitationLogger implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PetTypeVisitationLogger.class);

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {

        Object[] params = context.getParameters();

        logger.info("Pet type visited with id: " + params[0]);

        return context.proceed();
    }
}

