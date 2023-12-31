/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import dayjs from 'dayjs/esm';

import { Frequency } from './statistics.model';
import { StatisticsUtils } from './statistics.utils';

const prettifyDate = (date: string, frequency?: Frequency): string => {
  let format = '';
  switch (frequency) {
    case Frequency.MONTHLY:
      format = 'MMMM YYYY';
      break;
    case Frequency.HOURLY:
      format = 'HH:00';
      break;
    case Frequency.WEEKLY:
    case Frequency.DAILY:
    default:
      format = 'L';
      break;
  }

  return dayjs(new Date(date)).format(format);
};

export const lineChartOptions = (data: any, frequency: Frequency) => {
  return {
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: data.map((obj: any) => prettifyDate(obj.date, frequency))
    },
    yAxis: {
      type: 'value'
    },
    dataZoom: [
      {
        type: 'inside'
      }
    ],
    series: [
      {
        data: data.map((obj: any) => obj.count),
        type: 'line',
        smooth: true
      }
    ]
  };
};

export const barChartOptions = (data: any, frequency: Frequency) => {
  return {
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: data.map((obj: any) => prettifyDate(obj.date, frequency))
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: data.map((obj: any) => obj.count),
        type: 'bar'
      }
    ]
  };
};

export const comparingLineChartOptions = (data: any, xAxis: string, yAxis: string, frequency: Frequency) => {
  const series = data
    .map((e: any) => Object.keys(e.values))
    .reduce((acc: string[], current: string[]) => {
      current.forEach(e => {
        if (!acc.includes(e)) {
          acc.push(e);
        }
      });

      return acc;
    }, [])
    .map((k: any) => ({
      name: StatisticsUtils.getDisplayName(k),
      type: 'line',
      connectNullData: true,
      data: data.map((e: any) => (e.values[k] ? e.values[k] : 0))
    }));

  return {
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      name: xAxis,
      type: 'category',
      connectNullData: true,
      boundaryGap: false,
      data: data.map((obj: any) => prettifyDate(obj.date, frequency))
    },
    yAxis: {
      name: yAxis,
      type: 'value'
    },
    dataZoom: [
      {
        type: 'inside'
      }
    ],
    series
  };
};

export const pieChartOptions = (data: any) => {
  return {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      type: 'scroll',
      data: Object.keys(data).map(k => StatisticsUtils.getDisplayName(k))
    },
    series: [
      {
        name: 'Values',
        type: 'pie',
        radius: '55%',
        center: ['50%', '60%'],
        data: Object.keys(data).map(k => ({
          name: StatisticsUtils.getDisplayName(k),
          value: data[k]
        }))
      }
    ]
  };
};
