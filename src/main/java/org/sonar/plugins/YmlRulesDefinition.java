package org.sonar.plugins;

import org.sonar.api.rules.RuleType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonar.plugins.language.YmlLanguage;
import org.sonar.plugins.rules.CheckBlitzUsage;

public final class YmlRulesDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = "yml-cbr-rules-repository";

    @Override
    public void define(Context context) {
        NewRepository ymlRepository = context
                .createRepository(REPOSITORY_KEY, YmlLanguage.KEY)
                .setName("Правила конфигурации");

        new RulesDefinitionAnnotationLoader().load(ymlRepository, CheckBlitzUsage.class);

        NewRule ymlRule = ymlRepository.rule(CheckBlitzUsage.RULE_KEY);
        ymlRule.setHtmlDescription("<p>All right</p>");
        ymlRule.setType(RuleType.CODE_SMELL);
        ymlRule.setInternalKey(CheckBlitzUsage.RULE_KEY);
        ymlRepository.done();
    }
}
