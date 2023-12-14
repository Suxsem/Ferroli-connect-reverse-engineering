package org.eclipse.jetty.webapp;

public interface Configuration {
    void cloneConfigure(WebAppContext webAppContext, WebAppContext webAppContext2) throws Exception;

    void configure(WebAppContext webAppContext) throws Exception;

    void deconfigure(WebAppContext webAppContext) throws Exception;

    void destroy(WebAppContext webAppContext) throws Exception;

    void postConfigure(WebAppContext webAppContext) throws Exception;

    void preConfigure(WebAppContext webAppContext) throws Exception;
}
