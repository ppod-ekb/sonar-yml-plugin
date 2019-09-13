/*
 * JavaScript Plugin
 * Copyright (C) 2019 cbr
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.metrics;

import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.plugins.YmlRulesDefinition;
import org.sonar.plugins.language.YmlLanguage;
import org.sonar.plugins.rules.CheckBlitzUsage;

import java.util.stream.StreamSupport;

public final class BlitzUsageMeasureComputer implements MeasureComputer {
    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext defContext) {
        System.out.println("BlitzUsageMeasureComputer.define");
        return defContext
                .newDefinitionBuilder()
                .setOutputMetrics(YmlMetrics.BLITZ_USAGE_COUNT.key())
                .build();
    }

    @Override
    public void compute(MeasureComputerContext context) {
        System.out.println("BlitzUsageMeasureComputer.compute: " + context.getComponent().getKey());
        String ruleKey = YmlRulesDefinition.REPOSITORY_KEY + ":" + CheckBlitzUsage.RULE_KEY;
        boolean isBlitzUsage = context.getMeasure(YmlMetrics.BLITZ_USAGE_COUNT.key()) != null ?
                context.getMeasure(YmlMetrics.BLITZ_USAGE_COUNT.key()).getBooleanValue() : false;
        if(!isBlitzUsage){
            long l = context.getIssues().stream()
                    .filter(i -> i.ruleKey().toString().trim().equals(ruleKey))
                    .count();
            if(context.getComponent().getType() == Component.Type.FILE){
                if(context.getComponent().getFileAttributes().getLanguageKey().equals(YmlLanguage.KEY)){
                    System.out.println("BlitzUsageMeasureComputer.compute[ruleKey]: " + ruleKey);
                    System.out.println("BlitzUsageMeasureComputer.compute: " + context.getComponent().getFileAttributes().getLanguageKey());
                    context.addMeasure(YmlMetrics.BLITZ_USAGE_COUNT.getKey(), (int)l);
                }
            } else {
                int value = StreamSupport.stream(context.getChildrenMeasures(YmlMetrics.BLITZ_USAGE_COUNT.key()).spliterator(), false)
                        .map(Measure::getIntValue)
                        .reduce(0, Integer::sum);
                context.addMeasure(YmlMetrics.BLITZ_USAGE_COUNT.getKey(), value);
            }
        }
    }
}
