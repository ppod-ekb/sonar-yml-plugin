package org.sonar.plugins.language;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonar.plugins.YmlRulesDefinition;
import org.sonar.plugins.rules.CheckBlitzUsage;

public final class YmlQualityProfile implements BuiltInQualityProfilesDefinition {
    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile builtInQualityProfile = context.createBuiltInQualityProfile("Cbr way", YmlLanguage.KEY);
        builtInQualityProfile.setDefault(true);
        builtInQualityProfile.activateRule(YmlRulesDefinition.REPOSITORY_KEY, CheckBlitzUsage.RULE_KEY);
        builtInQualityProfile.done();
    }
}
