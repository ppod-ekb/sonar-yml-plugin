package org.sonar.plugins;

import org.sonar.api.Plugin;
import org.sonar.plugins.language.YmlLanguage;
import org.sonar.plugins.language.YmlQualityProfile;
import org.sonar.plugins.metrics.BlitzUsageMeasureComputer;
import org.sonar.plugins.metrics.YmlMetrics;
import org.sonar.plugins.sensor.YmlSensor;

public final class YmlPlugin implements Plugin {

    @Override
    public void define(Context context) {
        context.addExtension(YmlRulesDefinition.class);
        context.addExtension(YmlSensor .class);
        context.addExtensions(YmlLanguage .class, YmlQualityProfile.class);
        context.addExtensions(YmlMetrics.class, BlitzUsageMeasureComputer.class);
    }
}
