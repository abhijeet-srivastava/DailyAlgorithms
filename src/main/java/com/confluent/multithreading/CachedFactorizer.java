package com.confluent.multithreading;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;

@ThreadSafe
public class CachedFactorizer implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private long cacheHits;
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private BigInteger[] lastFactors;

    public synchronized long getHits() { return hits; }
    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = null;
        synchronized (this) {
            this.hits += 1;
            if(i.equals(lastNumber)) {
                cacheHits += 1;
                factors = this.lastFactors.clone();
            }
        }
        if(factors == null) {
            factors = factor(i);
            synchronized (this) {
                this.lastNumber = i;
                this.lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(servletResponse, factors);
    }

    private BigInteger[] factor(BigInteger i) {
        // Create factor of i
        return new BigInteger[]{};
    }

    private void encodeIntoResponse(ServletResponse servletResponse, BigInteger[] factors) {
    }

    private BigInteger extractFromRequest(ServletRequest servletRequest) {
        return new BigInteger(servletRequest.getParameter("number"));
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
