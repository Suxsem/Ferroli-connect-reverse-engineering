package org.eclipse.jetty.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class PatternMatcher {
    public abstract void matched(URI uri) throws Exception;

    public void match(Pattern pattern, URI[] uriArr, boolean z) throws Exception {
        if (uriArr != null) {
            String[] split = pattern == null ? null : pattern.pattern().split(",");
            ArrayList<Pattern> arrayList = new ArrayList<>();
            int i = 0;
            while (split != null && i < split.length) {
                arrayList.add(Pattern.compile(split[i]));
                i++;
            }
            if (arrayList.isEmpty()) {
                arrayList.add(pattern);
            }
            if (arrayList.isEmpty()) {
                matchPatterns((Pattern) null, uriArr, z);
                return;
            }
            for (Pattern matchPatterns : arrayList) {
                matchPatterns(matchPatterns, uriArr, z);
            }
        }
    }

    public void matchPatterns(Pattern pattern, URI[] uriArr, boolean z) throws Exception {
        for (int i = 0; i < uriArr.length; i++) {
            String uri = uriArr[i].toString();
            if ((pattern == null && z) || (pattern != null && pattern.matcher(uri).matches())) {
                matched(uriArr[i]);
            }
        }
    }
}
