package org.sonar.plugins.language;

import org.sonar.api.resources.AbstractLanguage;

public final class YmlLanguage extends AbstractLanguage {

    public static final String KEY = "yaml";

    public YmlLanguage() {
        super(KEY);
    }

    @Override
    public String[] getFileSuffixes() {
        return new String[]{"yml", "yaml"};
    }
}
