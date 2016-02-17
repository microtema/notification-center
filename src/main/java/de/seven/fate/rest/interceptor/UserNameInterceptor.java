package de.seven.fate.rest.interceptor;

import de.seven.fate.util.ClassUtil;
import de.seven.fate.util.ContextUtil;
import org.apache.log4j.Logger;

import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Created by Mario on 17.02.2016.
 */
public class UserNameInterceptor {

    private static final Logger logger = Logger.getLogger(UserNameInterceptor.class);

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        try {

            int indexOfParameter = ClassUtil.getIndexOfParameter(context.getMethod().getParameterAnnotations(), UserName.class);

            if (indexOfParameter != -1) {

                Object[] parameterValues = context.getParameters();

                parameterValues[indexOfParameter] = getUserName();

                context.setParameters(parameterValues);
            }

            return context.proceed();

        } catch (Exception e) {
            logger.debug("unable to handle REST#call " + context.getMethod().getName() + " with data: " + context.getParameters(), e);
            throw e;
        }
    }


    private String getUserName() {
        // return getSessionContext().getCallerPrincipal().getName();

        return "mtema";
    }

    /**
     * NewsEntityListener is not a part of CDI,
     * so we have to lookup for the service through ContextUtil
     *
     * @return SessionContext
     */
    private SessionContext getSessionContext() {
        return (SessionContext) ContextUtil.getComponent(EJBContext.class);
    }
}
