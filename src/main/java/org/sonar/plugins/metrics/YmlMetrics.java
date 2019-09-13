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

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

public final class YmlMetrics implements Metrics {

  public static final Metric BLITZ_USAGE_COUNT = new Metric.Builder("blitz_usage_count", "Blitz usage", Metric.ValueType.INT)
          .setDescription("")
          .setDirection(Metric.DIRECTION_BETTER)
          .setQualitative(false)
          .setDomain(CoreMetrics.DOMAIN_MAINTAINABILITY)
          .create();

  public List<Metric> getMetrics() {
    return Arrays.asList(BLITZ_USAGE_COUNT);
  }
}
