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
import { Component, AfterViewInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { Observable, timer } from 'rxjs';
import { NgxEchartsService } from 'ngx-echarts';

import { StatisticsService } from './statistics.service';
import { HomeService } from '../home.service';
import { BasicChart, LineChart } from 'app/home/statistics/statistics.model';
import { barChart, comparingLineChart, pieChart } from 'app/home/statistics/statistics.options';

@Component({
    selector: 'jhi-statistics',
    templateUrl: './statistics.component.html',
    styleUrls: ['statistics.scss']
})
export class StatisticsComponent implements AfterViewInit, OnDestroy {
    @ViewChild('chartTrend1') chartTrend1: ElementRef;
    @ViewChild('chartTrend2') chartTrend2: ElementRef;
    @ViewChild('chartTrend3') chartTrend3: ElementRef;
    @ViewChild('chartTrend4') chartTrend4: ElementRef;
    @ViewChild('chartTrendFull1') chartTrendFull1: ElementRef;
    @ViewChild('chartTrendFull2') chartTrendFull2: ElementRef;
    @ViewChild('chartTrendFull3') chartTrendFull3: ElementRef;
    @ViewChild('chartTrendFull4') chartTrendFull4: ElementRef;
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
    @ViewChild('chartJDL') chartJDL: ElementRef;

    countJdl: Observable<string>;
    countYoRc: Observable<string>;
    countUser: Observable<string>;
    countYoRcByDate: Observable<string>;

    timeScale = 'all';
    generatedApps = true;

    yorcByDateList: any;

    constructor(
        private statisticsService: StatisticsService,
        private echartsService: NgxEchartsService,
        private homeService: HomeService
    ) {}

    ngAfterViewInit() {
        this.countJdl = this.statisticsService.countJdl();
        this.countYoRc = this.statisticsService.countYos();
        this.onSelectTimeScale();
    }

    public onSelectTimeScale() {
        switch (this.timeScale) {
            case 'all':
                this.generatedApps = true;
                if (this.isFullScreen()) {
                    this.displayTrend('yearly', this.chartTrendFull1);
                    this.displayTrend('monthly', this.chartTrendFull2);
                    this.displayTrend('daily', this.chartTrendFull3);
                    this.displayTrend('hourly', this.chartTrendFull4);
                } else {
                    this.displayTrend('yearly', this.chartTrend1);
                    this.displayTrend('monthly', this.chartTrend2);
                    this.displayTrend('daily', this.chartTrend3);
                    this.displayTrend('hourly', this.chartTrend4);
                }
                break;
            case 'years':
                this.displayCharts('monthly');
                this.displayTrend('yearly', this.chartJDL);
                break;
            case 'months':
                this.displayCharts('weekly');
                break;
            case 'weeks':
                this.displayCharts('daily');
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

    private displayChart(frequency: string, field: string, chartLine: any, chartPie: any) {
        this.statisticsService.getFieldCount(field, frequency).subscribe(data => {
            data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
            const linechartCompared1 = new LineChart(
                this.echartsService,
                comparingLineChart(data, 'Date', 'Amount'),
                chartLine,
                null
            ).build();
            const pieChartData = data.reduce((acc, current) => {
                Object.keys(current.values).forEach(e => {
                    const currentSum = acc[e] || 0;
                    acc[e] = currentSum + current.values[e];
                });
                return acc;
            }, {});
            const lineChartCompared2 = new BasicChart(this.echartsService, pieChart(pieChartData), chartPie, null).build();

            this.updateRelatedBasicChartOf(data, linechartCompared1.chartInstance, lineChartCompared2);
        });
    }

    private displayCharts(frequency: string) {
        this.generatedApps = false;

        this.displayChart(frequency, 'clientFramework', this.chartFrameworkLine, this.chartFrameworkPie);
        this.displayChart(frequency, 'buildTool', this.chartBuildtoolLine, this.chartBuildtoolPie);
        // Deployement this.displayChart(frequency, '', this.chartDeploymentLine, this.chartDeploymentPie);
        this.displayChart(frequency, 'prodDatabaseType', this.chartDBProdLine, this.chartDBProdPie);
        this.displayChart(frequency, 'cacheProvider', this.chartCacheLine, this.chartCachePie);
        this.displayChart(frequency, 'jhipsterVersion', this.chartVersionLine, this.chartVersionPie);
        this.displayChart(frequency, 'applicationType', this.chartAppTypeLine, this.chartAppTypePie);
        // JDL this.displayChart(frequency, '', this.chartJDLLine, this.chartJDLPie);
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

    public toggleFullscreen() {
        this.homeService.toggleFullScreen();
    }

    public isFullScreen() {
        return this.homeService.isFullScreen();
    }

    public exitFullScreen() {
        this.homeService.exitFullScreen();
    }

    ngOnDestroy() {
        this.exitFullScreen();
    }
}
