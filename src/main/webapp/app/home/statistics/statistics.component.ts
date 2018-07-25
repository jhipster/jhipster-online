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
import { barChart, comparingLineChart, lineChart, pieChart, prettifyDate } from 'app/home/statistics/statistics.options';
// import { lineChartOptions, pieChartOptions, stackedAreaChartOptions } from 'app/home/statistics/statistics.options';

@Component({
    selector: 'jhi-statistics',
    templateUrl: './statistics.component.html',
    styleUrls: ['statistics.scss']
})
export class StatisticsComponent implements AfterViewInit {
    dataFromDb = [];
    charts = [];

    @ViewChild('chartTrend1') chartTrend1: ElementRef;
    @ViewChild('chartTrend2') chartTrend2: ElementRef;
    @ViewChild('chartTrend3') chartTrend3: ElementRef;
    @ViewChild('chartTrend4') chartTrend4: ElementRef;
    @ViewChild('chartFrameworkLine') chartFrameworkLine: ElementRef;
    @ViewChild('chartFrameworkPie') chartFrameworkPie: ElementRef;
    @ViewChild('chartBuildtoolLine') chartBuildtoolLine: ElementRef;
    @ViewChild('chartBuildtoolPie') chartBuildtoolPie: ElementRef;
    @ViewChild('chartDeploymentLine') chartDeploymentLine: ElementRef;
    @ViewChild('chartDeploymentPie') chartDeploymentPie: ElementRef;
    @ViewChild('chartDBProdLine') chartDBProdLine: ElementRef;
    @ViewChild('chartDBProdPie') chartDBProdPie: ElementRef;
    @ViewChild('chartCacheLine') chartCacheLine: ElementRef;
    @ViewChild('chartCachePie') chartCachePie: ElementRef;
    @ViewChild('chartVersionLine') chartVersionLine: ElementRef;
    @ViewChild('chartVersionPie') chartVersionPie: ElementRef;
    @ViewChild('chartAppTypeLine') chartAppTypeLine: ElementRef;
    @ViewChild('chartAppTypePie') chartAppTypePie: ElementRef;
    @ViewChild('chartJDLLine') chartJDLLine: ElementRef;
    @ViewChild('chartJDLPie') chartJDLPie: ElementRef;

    countJdl: Observable<string>;
    countYoRc: Observable<string>;
    countUser: Observable<string>; // user/owner ?
    countYoRcByDate: Observable<string>;

    timeScale = 'all';
    generalOverview = true;

    yorcByDateList: any;

    constructor(private statisticsService: StatisticsService, private echartsService: NgxEchartsService) {}

    ngAfterViewInit() {
        this.countJdl = this.statisticsService.countJdl();
        this.countYoRc = this.statisticsService.countYos();
        this.onSelectTimeScale();
    }

    public onSelectTimeScale() {
        switch (this.timeScale) {
            case 'all':
                this.generalOverview = true;
                this.displayTrend('yearly', this.chartTrend1);
                this.displayTrend('monthly', this.chartTrend2);
                this.displayTrend('daily', this.chartTrend3);
                this.displayTrend('hourly', this.chartTrend4);
                break;
            case 'years':
                this.displayCharts('monthly');
                break;
            case 'months':
                this.displayCharts('daily');
                break;
            case 'weeks':
                break;
            case 'days':
                this.displayCharts('hourly');
                break;
            default:
                break;
        }
    }

    private displayTrend(frequency: string, chart: any) {
        this.statisticsService.getCount(frequency).subscribe(data => {
            new LineChart(this.echartsService, barChart(data), chart, null).build();
        });
    }

    private displayCharts(frequency: string) {
        this.generalOverview = false;

        // Client framework
        this.statisticsService.getFieldCount('clientFramework', frequency).subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechartCompared1 = new LineChart(
                this.echartsService,
                comparingLineChart(data, 'Date', 'Amount'),
                this.chartFrameworkLine,
                null
            ).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChartCompared2 = new BasicChart(this.echartsService, pieChart(pieChartData), this.chartFrameworkPie, null).build();

            this.updateRelatedBasicChartOf(data, linechartCompared1.chartInstance, lineChartCompared2);
        });

        // Buildtool
        this.statisticsService.getFieldCount('buildTool', frequency).subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechartCompared1 = new LineChart(
                this.echartsService,
                comparingLineChart(data, 'Date', 'Amount'),
                this.chartBuildtoolLine,
                null
            ).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChartCompared2 = new BasicChart(this.echartsService, pieChart(pieChartData), this.chartBuildtoolPie, null).build();

            this.updateRelatedBasicChartOf(data, linechartCompared1.chartInstance, lineChartCompared2);
        });

        // Deployment
        /*
        this.statisticsService.getFieldCount('buildTool', frequency).subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechartCompared1 = new LineChart(this.echartsService, comparingLineChart(data, 'Date', 'Amount'), this.chartDeploymentLine, null).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChartCompared2 = new BasicChart(this.echartsService, pieChart(pieChartData), this.chartDeploymentPie, null).build();

            this.updateRelatedBasicChartOf(data, linechartCompared1.chartInstance, lineChartCompared2);
        });*/

        // Production database
        this.statisticsService.getFieldCount('prodDatabaseType', frequency).subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechartCompared1 = new LineChart(
                this.echartsService,
                comparingLineChart(data, 'Date', 'Amount'),
                this.chartDBProdLine,
                null
            ).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChartCompared2 = new BasicChart(this.echartsService, pieChart(pieChartData), this.chartDBProdPie, null).build();

            this.updateRelatedBasicChartOf(data, linechartCompared1.chartInstance, lineChartCompared2);
        });

        // Cache provider
        this.statisticsService.getFieldCount('cacheProvider', frequency).subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechartCompared1 = new LineChart(
                this.echartsService,
                comparingLineChart(data, 'Date', 'Amount'),
                this.chartCacheLine,
                null
            ).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChartCompared2 = new BasicChart(this.echartsService, pieChart(pieChartData), this.chartCachePie, null).build();

            this.updateRelatedBasicChartOf(data, linechartCompared1.chartInstance, lineChartCompared2);
        });

        // JHipster Version
        this.statisticsService.getFieldCount('jhipsterVersion', frequency).subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechartCompared1 = new LineChart(
                this.echartsService,
                comparingLineChart(data, 'Date', 'Amount'),
                this.chartVersionLine,
                null
            ).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChartCompared2 = new BasicChart(this.echartsService, pieChart(pieChartData), this.chartVersionPie, null).build();

            this.updateRelatedBasicChartOf(data, linechartCompared1.chartInstance, lineChartCompared2);
        });

        // Type of app
        this.statisticsService.getFieldCount('applicationType', frequency).subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechartCompared1 = new LineChart(
                this.echartsService,
                comparingLineChart(data, 'Date', 'Amount'),
                this.chartAppTypeLine,
                null
            ).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChartCompared2 = new BasicChart(this.echartsService, pieChart(pieChartData), this.chartAppTypePie, null).build();

            this.updateRelatedBasicChartOf(data, linechartCompared1.chartInstance, lineChartCompared2);
        });

        // JDL entities
        /*
        this.statisticsService.getFieldCount('', frequency).subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechartCompared1 = new LineChart(this.echartsService, comparingLineChart(data, 'Date', 'Amount'), this.chartJDLLine, null).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChartCompared2 = new BasicChart(this.echartsService, pieChart(pieChartData), this.chartJDLPie, null).build();

            this.updateRelatedBasicChartOf(data, linechartCompared1.chartInstance, lineChartCompared2);
        });*/
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
