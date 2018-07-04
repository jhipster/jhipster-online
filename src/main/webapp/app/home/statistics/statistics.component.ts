/**
 * Copyright 2017-2018 the original author or authors from the JHipster Online project.
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
import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import * as moment from 'moment';
import { Observable, timer } from 'rxjs';
import { NgxEchartsService } from 'ngx-echarts';

import { StatisticsService } from './statistics.service';
import { BasicChart, LineChart } from 'app/home/statistics/statistics.model';
import { comparingLineChart, lineChart, pieChart } from 'app/home/statistics/statistics.options';
// import { lineChartOptions, pieChartOptions, stackedAreaChartOptions } from 'app/home/statistics/statistics.options';

@Component({
    selector: 'jhi-statistics',
    templateUrl: './statistics.component.html'
})
export class StatisticsComponent implements AfterViewInit {
    dataFromDb = [];
    charts = [];

    @ViewChild('chart1') chart1: ElementRef;
    @ViewChild('chart2') chart2: ElementRef;
    @ViewChild('chart3') chart3: ElementRef;
    @ViewChild('chart4') chart4: ElementRef;
    @ViewChild('chart5') chart5: ElementRef;

    countJdl: Observable<string>;
    countYoRc: Observable<string>;
    countUser: Observable<string>; // user/owner ?
    countYoRcByDate: Observable<string>;

    timeScale: string;

    yorcByDateList: any;

    constructor(private statisticsService: StatisticsService, private echartsService: NgxEchartsService) {}

    ngAfterViewInit() {
        this.countJdl = this.statisticsService.countJdl();
        this.countYoRc = this.statisticsService.countYoRC();

        this.statisticsService.countYoRCByYears().subscribe(data => {
            const lineChart3 = new LineChart(this.echartsService, lineChart(data), this.chart1, null).build();
        });
        this.statisticsService.countYoRCByMonths().subscribe(data => {
            const lineChart2 = new LineChart(this.echartsService, lineChart(data), this.chart2, null).build();
        });
        this.statisticsService.countYoRCByDays().subscribe(data => {
            const lineChart2 = new LineChart(this.echartsService, lineChart(data), this.chart3, null).build();
        });
        this.statisticsService.countAllByClientFramework().subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechart2 = new LineChart(this.echartsService, comparingLineChart(data, 'Date', 'Amount'), this.chart4, null).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChart3 = new BasicChart(this.echartsService, pieChart(pieChartData), this.chart5, null).build();

            console.log(data);
            this.updateRelatedBasicChartOf(data, linechart2.chartInstance, lineChart3);
        });
        this.statisticsService.countYoRCByDays().subscribe(data => data);
    }

    public onSelectTimeScale() {
        switch (this.timeScale) {
            case 'years':
                // this.charts[0].generatedProjectsLineChart.chartInstance.setOption(lineChartOptions(this.computeByYear(this.yorcByDateList)));
                break;
            default:
                break;
        }
    }

    private updateRelatedBasicChartOf(data: any, chartInstance: any, basicChartInstance: BasicChart) {
        chartInstance.on('dataZoom', () => {
            const minDate = data[chartInstance.getOption().dataZoom[0].startValue].date;
            const maxDate = data[chartInstance.getOption().dataZoom[0].endValue].date;
            let arr = data.filter(obj => new Date(obj.date) >= new Date(minDate) && new Date(obj.date) <= new Date(maxDate));
            arr = arr.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});

            timer(250).subscribe(() => {
                basicChartInstance.chartInstance.setOption(pieChart(arr));
            });
        });
    }

    private computeByYear(data: any) {
        return Object.keys(data).reduce((acc, key) => {
            const year = moment(new Date(key)).year();
            const filteredDataByYear = this.filterDataByKey(data, key => moment(new Date(key)).year() === year);
            acc[year] = Object.keys(filteredDataByYear).reduce((acc, current) => {
                return filteredDataByYear[current].length + acc;
            }, 0);
            return acc;
        }, {});
    }

    private groupDataBy(data: any, property: string) {
        return data.reduce((acc, current) => {
            const key = current[property];
            if (!acc[key]) {
                acc[key] = [];
            }
            acc[key].push(current);
            return acc;
        }, {});
    }

    private filterDataByKey(data: any, predicate: Function) {
        return Object.keys(data)
            .filter(key => predicate(key))
            .reduce((res, key) => {
                res[key] = data[key];
                return res;
            }, {});
    }

    private filterDataByValue(data: any, predicate: Function) {
        return Object.keys(data)
            .filter(key => predicate(data[key]))
            .reduce((res, key) => {
                res[key] = data[key];
                return res;
            }, {});
    }

    private filterDataByArrayValue(data: any, predicate: Function) {
        return Object.keys(data).reduce((res, key) => {
            res[key] = data[key].filter(predicate);
            return res;
        }, {});
    }
}
