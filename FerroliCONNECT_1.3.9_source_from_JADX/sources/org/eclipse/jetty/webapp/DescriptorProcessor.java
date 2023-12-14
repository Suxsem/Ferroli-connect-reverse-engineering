package org.eclipse.jetty.webapp;

public interface DescriptorProcessor {
    void process(WebAppContext webAppContext, Descriptor descriptor) throws Exception;
}
