package org.eclipse.jetty.webapp;

public class CloneConfiguration extends AbstractConfiguration {
    final WebAppContext _template;

    CloneConfiguration(WebAppContext webAppContext) {
        this._template = webAppContext;
    }

    public void configure(WebAppContext webAppContext) throws Exception {
        for (Configuration cloneConfigure : this._template.getConfigurations()) {
            cloneConfigure.cloneConfigure(this._template, webAppContext);
        }
    }

    public void deconfigure(WebAppContext webAppContext) throws Exception {
        for (Configuration deconfigure : this._template.getConfigurations()) {
            deconfigure.deconfigure(webAppContext);
        }
    }
}
