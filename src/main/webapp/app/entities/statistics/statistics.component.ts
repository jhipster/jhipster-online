/**
 * Copyright 2017-2020 the original author or authors from the JHipster Online project.
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
import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { Observable, timer } from 'rxjs';
import { NgxEchartsService } from 'ngx-echarts';

import { StatisticsService } from './statistics.service';
import { Chart, Frequency } from './statistics.model';
import { barChartOptions, comparingLineChartOptions, pieChartOptions } from './statistics.options';
import { computeAngularKey, displayNames } from 'app/entities/statistics/statistics.utils';

@Component({
    selector: 'jhi-statistics',
    templateUrl: './statistics.component.html',
    styleUrls: ['statistics.scss']
})
export class StatisticsComponent implements AfterViewInit {
    @ViewChild('chartTrend1') chartTrend1: ElementRef;
    @ViewChild('chartTrend2') chartTrend2: ElementRef;
    @ViewChild('chartTrend3') chartTrend3: ElementRef;
    @ViewChild('chartTrend4') chartTrend4: ElementRef;
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
    @ViewChild('chartJDLLine') chartJDLLine: ElementRef;
    @ViewChild('chartJDLPie') chartJDLPie: ElementRef;

    countYoRcByDate: Observable<string>;
    countYos: number;
    countJdls: number;
    countUsers: number;

    timeScale: string = Frequency.DEFAULT;
    overview = true;

    constructor(private statisticsService: StatisticsService, private echartsService: NgxEchartsService) {}

    ngAfterViewInit() {
        this.onSelectTimeScale(Frequency.DEFAULT);
    }

    public setTimeScale() {
        this.onSelectTimeScale(this.timeScale);
    }

    public onSelectTimeScale(event) {
        if (event === undefined) {
            return;
        }
        if (event) {
            this.timeScale = event;
        }

        this.refreshData();
    }

    public refreshData() {
        this.statisticsService.countYos().subscribe(countYos => (this.countYos = countYos));
        this.statisticsService.countJdls().subscribe(countJdls => (this.countJdls = countJdls));
        this.statisticsService.countUsers().subscribe(countUsers => (this.countUsers = countUsers));
        switch (this.timeScale) {
            case 'yearly':
                this.displayCharts(Frequency.MONTHLY);
                // this.displayGenerationCount(Frequency.YEARLY, this.chartJDL);
                break;
            case 'monthly':
                this.displayCharts(Frequency.WEEKLY);
                break;
            case 'weekly':
                this.displayCharts(Frequency.DAILY);
                break;
            case 'daily':
                this.displayCharts(Frequency.HOURLY);
                break;
            case 'clientFramework':
                this.overview = true;
                this.displayOverview('clientFramework');
                break;
            case 'buildTool':
                this.overview = true;
                this.displayOverview('buildTool');
                break;
            case 'default':
            default:
                this.overview = true;
                this.displayGenerationCount(Frequency.MONTHLY, this.chartTrendFull2);
                this.displayGenerationCount(Frequency.DAILY, this.chartTrendFull3);
                this.displayGenerationCount(Frequency.HOURLY, this.chartTrendFull4);
                break;
        }
    }

    private displayOverview(field: string) {
        this.displayOverviewChart(field, Frequency.MONTHLY, this.chartTrendFull2);
        this.displayOverviewChart(field, Frequency.DAILY, this.chartTrendFull3);
        this.displayOverviewChart(field, Frequency.HOURLY, this.chartTrendFull4);
    }

    private displayOverviewChart(field: string, frequency: Frequency, lineChart: any) {
        this.statisticsService.getFieldCount(field, frequency).subscribe(data => {
            this.displayOverviewData(data, lineChart, frequency);
        });
    }

    private displayOverviewData(data: any, lineChart, frequency: Frequency) {
        data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));
        new Chart(this.echartsService, comparingLineChartOptions(data, 'Date', 'Total', frequency), lineChart).build();
    }

    private displayGenerationCount(frequency: Frequency, chart: any) {
        this.statisticsService
            .getCount(frequency)
            .subscribe(data => new Chart(this.echartsService, barChartOptions(data, frequency), chart).build());
    }

    private displayEntityGenerationStats(frequency: Frequency, field: string, lineChar: any, pieChart: any) {
        this.statisticsService
            .getEntityFieldCount(field, frequency)
            .subscribe((data: any) => this.displayData(data, lineChar, pieChart, frequency));
    }

    private displayData(data: any, lineChart, pieChart, frequency: Frequency) {
        data.sort((a: any, b: any) => new Date(a.date).toISOString().localeCompare(new Date(b.date).toISOString()));

        const refinedData = this.refineData(data);

        const linechartCompared1 = new Chart(
            this.echartsService,
            comparingLineChartOptions(refinedData, 'Date', 'Total', frequency),
            lineChart
        ).build();

        const pieChartData = refinedData.reduce((acc, current) => {
            Object.keys(current.values).forEach(e => {
                const currentSum = acc[e] || 0;
                acc[e] = currentSum + current.values[e];
            });
            return acc;
        }, {});

        const lineChartCompared2 = new Chart(this.echartsService, pieChartOptions(pieChartData), pieChart).build();
        this.updateRelatedBasicChartOf(refinedData, linechartCompared1.chartInstance, lineChartCompared2);
    }

    private displayChart(frequency: Frequency, field: string, chartLine: any, chartPie: any) {
        this.statisticsService.getFieldCount(field, frequency).subscribe(data => {
            this.displayData(data, chartLine, chartPie, frequency);
        });
    }

    private displayDeploymentTools(frequency: Frequency, lineChart, pieChart) {
        this.statisticsService
            .getDeploymentToolsCount(frequency)
            .subscribe((data: any) => this.displayData(data, lineChart, pieChart, frequency));
    }

    private displayCharts(frequency: Frequency) {
        this.overview = false;

        this.displayChart(frequency, 'clientFramework', this.chartFrameworkLine, this.chartFrameworkPie);
        this.displayChart(frequency, 'buildTool', this.chartBuildtoolLine, this.chartBuildtoolPie);
        this.displayDeploymentTools(frequency, this.chartDeploymentLine, this.chartDeploymentPie);
        this.displayChart(frequency, 'prodDatabaseType', this.chartDBProdLine, this.chartDBProdPie);
        this.displayChart(frequency, 'cacheProvider', this.chartCacheLine, this.chartCachePie);
        this.displayChart(frequency, 'jhipsterVersion', this.chartVersionLine, this.chartVersionPie);
        this.displayChart(frequency, 'applicationType', this.chartAppTypeLine, this.chartAppTypePie);
        this.displayEntityGenerationStats(frequency, 'fields', this.chartJDLLine, this.chartJDLPie);
    }

    private updateRelatedBasicChartOf(data: any, chartInstance: any, basicChartInstance: Chart) {
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
                basicChartInstance.chartInstance.setOption(pieChartOptions(arr));
            });
        });
    }

    private refineData(data) {
        return data.reduce((acc, current) => {
            const refinedValues = Object.keys(current.values).reduce((prev, currentProperty: string) => {
                const lowerCaseKey = currentProperty.toLowerCase();

                this.prettifyClientFrameworkData(lowerCaseKey, prev, currentProperty, current);
                this.prettifyBuildToolData(lowerCaseKey, prev, currentProperty, current);
                this.prettifyProdDatabase(lowerCaseKey, prev, currentProperty, current);
                this.prettifyCacheProvider(lowerCaseKey, prev, currentProperty, current);
                this.prettifyCloudDeployment(lowerCaseKey, prev, currentProperty, current);
                this.prettifyApplicationTypeData(lowerCaseKey, prev, currentProperty, current);

                // jhipster versions
                if (/\d\.\d\.\d/.test(lowerCaseKey)) {
                    prev[lowerCaseKey] = (prev[lowerCaseKey] || 0) + current.values[currentProperty];
                }

                return prev;
            }, {});

            acc.push({ date: current.date, values: refinedValues });

            return acc;
        }, []);
    }

    private prettifyClientFrameworkData(lowerCaseKey: string, prev, currentProperty: string, current) {
        if (!['react', 'vue', 'angular', 'none'].some(k => lowerCaseKey.includes(k))) {
            return;
        }

        let key;
        if (lowerCaseKey.includes('react')) {
            key = displayNames.react;
        } else if (lowerCaseKey.includes('vue')) {
            key = displayNames.vuejs;
        } else if (lowerCaseKey.includes('angular')) {
            key = computeAngularKey(lowerCaseKey);
        } else {
            key = displayNames.default;
        }

        prev[key] = (prev[key] || 0) + current.values[currentProperty];
    }

    private prettifyBuildToolData(lowerCaseKey: string, prev, currentProperty: string, current) {
        if (!['maven', 'gradle'].includes(lowerCaseKey)) {
            return;
        }

        let key;
        if (lowerCaseKey.includes('maven')) {
            key = displayNames.maven;
        } else if (lowerCaseKey.includes('gradle')) {
            key = displayNames.gradle;
        }

        prev[key] = (prev[key] || 0) + current.values[currentProperty];
    }

    private prettifyProdDatabase(lowerCaseKey: string, prev, currentProperty: string, current) {
        if (!['postgre', 'maria', 'oracle', 'mysql', 'mssql', 'mongo', 'none'].some(k => lowerCaseKey.includes(k))) {
            return;
        }

        let key;
        if (lowerCaseKey.includes('postgre')) {
            key = displayNames.postgresql;
        } else if (lowerCaseKey.includes('maria')) {
            key = displayNames.mariadb;
        } else if (lowerCaseKey.includes('oracle')) {
            key = displayNames.oracle;
        } else if (lowerCaseKey.includes('mysql')) {
            key = displayNames.mysql;
        } else if (lowerCaseKey.includes('mssql')) {
            key = displayNames.mssql;
        } else if (lowerCaseKey.includes('mongo')) {
            key = displayNames.mongodb;
        } else {
            key = displayNames.default;
        }

        prev[key] = (prev[key] || 0) + current.values[currentProperty];
    }

    private prettifyCacheProvider(lowerCaseKey: string, prev, currentProperty: string, current) {
        if (!['ehcache', 'hazelcast', 'infinispan', 'none'].some(k => lowerCaseKey.includes(k))) {
            return;
        }

        let key;
        if (lowerCaseKey.includes('ehcache')) {
            key = displayNames.ehcache;
        } else if (lowerCaseKey.includes('hazelcast')) {
            key = displayNames.hazelcast;
        } else if (lowerCaseKey.includes('infinispan')) {
            key = displayNames.infinispan;
        } else {
            key = displayNames.default;
        }

        prev[key] = (prev[key] || 0) + current.values[currentProperty];
    }

    private prettifyCloudDeployment(lowerCaseKey: string, prev, currentProperty: string, current) {
        if (!['kubernetes', 'openshift', 'heroku', 'cloud', 'aws', 'none'].some(k => lowerCaseKey.includes(k))) {
            return;
        }

        let key;
        if (lowerCaseKey.includes('kubernetes')) {
            key = displayNames.kubernetes;
        } else if (lowerCaseKey.includes('openshift')) {
            key = displayNames.openshift;
        } else if (lowerCaseKey.includes('heroku')) {
            key = displayNames.heroku;
        } else if (lowerCaseKey.includes('cloud')) {
            key = displayNames.cloudfoundry;
        } else if (lowerCaseKey.includes('aws')) {
            key = displayNames.aws;
        } else {
            key = displayNames.default;
        }

        prev[key] = (prev[key] || 0) + current.values[currentProperty];
    }

    private prettifyApplicationTypeData(lowerCaseKey: string, prev, currentProperty: string, current) {
        if (!['gateway', 'microservice', 'monolith', 'uaa', 'none'].some(k => lowerCaseKey.includes(k))) {
            return;
        }

        let key;
        if (lowerCaseKey.includes('gateway')) {
            key = displayNames.gateway;
        } else if (lowerCaseKey.includes('microservice')) {
            key = displayNames.microservice;
        } else if (lowerCaseKey.includes('monolith')) {
            key = displayNames.monolithic;
        } else if (lowerCaseKey.includes('uaa')) {
            key = displayNames.uaa;
        } else {
            key = displayNames.default;
        }

        prev[key] = (prev[key] || 0) + current.values[currentProperty];
    }
}
