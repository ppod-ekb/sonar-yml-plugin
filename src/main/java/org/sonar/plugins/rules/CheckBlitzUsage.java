package org.sonar.plugins.rules;

import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(name = "Blitz usage", key = CheckBlitzUsage.RULE_KEY, priority = Priority.INFO, tags = {"cbr"})
public final class CheckBlitzUsage {

    public static final String RULE_KEY = "blitz";
}
