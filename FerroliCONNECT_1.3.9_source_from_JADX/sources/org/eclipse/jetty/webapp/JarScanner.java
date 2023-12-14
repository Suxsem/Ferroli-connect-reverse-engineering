package org.eclipse.jetty.webapp;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Pattern;
import org.eclipse.jetty.util.PatternMatcher;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

public abstract class JarScanner extends PatternMatcher {
    private static final Logger LOG = Log.getLogger((Class<?>) JarScanner.class);

    public abstract void processEntry(URI uri, JarEntry jarEntry);

    public void scan(Pattern pattern, URI[] uriArr, boolean z) throws Exception {
        super.match(pattern, uriArr, z);
    }

    public void scan(Pattern pattern, ClassLoader classLoader, boolean z, boolean z2) throws Exception {
        URL[] uRLs;
        while (classLoader != null) {
            if ((classLoader instanceof URLClassLoader) && (uRLs = ((URLClassLoader) classLoader).getURLs()) != null) {
                URI[] uriArr = new URI[uRLs.length];
                int length = uRLs.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    uriArr[i2] = uRLs[i].toURI();
                    i++;
                    i2++;
                }
                scan(pattern, uriArr, z);
            }
            classLoader = z2 ? classLoader.getParent() : null;
        }
    }

    public void matched(URI uri) throws Exception {
        InputStream inputStream;
        LOG.debug("Search of {}", uri);
        if (uri.toString().toLowerCase(Locale.ENGLISH).endsWith(".jar") && (inputStream = Resource.newResource(uri).getInputStream()) != null) {
            JarInputStream jarInputStream = new JarInputStream(inputStream);
            try {
                for (JarEntry nextJarEntry = jarInputStream.getNextJarEntry(); nextJarEntry != null; nextJarEntry = jarInputStream.getNextJarEntry()) {
                    processEntry(uri, nextJarEntry);
                }
            } finally {
                jarInputStream.close();
            }
        }
    }
}
