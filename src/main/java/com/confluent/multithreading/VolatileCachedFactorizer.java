package com.confluent.multithreading;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;

public class VolatileCachedFactorizer implements Servlet {
    private  volatile OneValueCache cache = new  OneValueCache(null, null);
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = this.cache.getFactors(i.intValue());
        if(factors == null) {
            factors = factors(i);
            this.cache = new OneValueCache(i, factors);
        }
        encodeIntoResponse(servletResponse, factors);
    }

    private void encodeIntoResponse(ServletResponse servletResponse, BigInteger[] factors) {
    }

    private BigInteger[] factors(BigInteger i) {
        return new BigInteger[]{};
    }

    private BigInteger extractFromRequest(ServletRequest servletRequest) {
        return new BigInteger(servletRequest.getParameter("number"));
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
