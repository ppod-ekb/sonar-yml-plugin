package org.sonar.plugins.sensor;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRule;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.plugins.YmlRulesDefinition;
import org.sonar.plugins.language.YmlLanguage;
import org.sonar.plugins.rules.CheckBlitzUsage;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Node;

import java.io.IOException;
import java.io.StringReader;

public final class YmlSensor implements Sensor {

    private final FileSystem fs;
    private final ActiveRules activeRules;
    private final Logger log = Loggers.get(YmlSensor.class);

    public YmlSensor(FileSystem fs, ActiveRules activeRules) {
        this.fs = fs;
        this.activeRules = activeRules;
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("Yaml sensor");
        descriptor.onlyOnLanguage(YmlLanguage.KEY);
    }

    @Override
    public void execute(final SensorContext context) {
        fs.inputFiles(fs.predicates().hasLanguage(YmlLanguage.KEY))
                .forEach(f -> fileAnalyze(f, context));
    }

    private void fileAnalyze(InputFile file, SensorContext context) {
        log.info("analyze file: " + file);
        try {
            Node node = new Yaml().compose(new StringReader(file.contents()));
            YmlAnalyzer ymlAnalyzer = new YmlAnalyzer(node);
            ymlAnalyzer.analyze();
            log.info("Blitz config finded?: " + ymlAnalyzer.isBlitzConfigFinded());
            if(ymlAnalyzer.isBlitzConfigFinded()){
                ActiveRule blitz = this.activeRules
                        .findByInternalKey(YmlRulesDefinition.REPOSITORY_KEY, CheckBlitzUsage.RULE_KEY);
                if(blitz != null) {
                    NewIssue issue = context.newIssue().forRule(blitz.ruleKey());
                    NewIssueLocation newIssueLocation = issue.newLocation()
                            .on(file)
                            .message("usage blitz detected");
                    issue.at(newIssueLocation).save();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
