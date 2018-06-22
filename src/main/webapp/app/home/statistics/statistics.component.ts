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
import { Observable } from 'rxjs';
import { NgxEchartsService } from 'ngx-echarts';

import { StatisticsService } from './statistics.service';
import { BasicChart, LineChart } from 'app/home/statistics/statistics.model';
import { lineChartOptions, pieChartOptions, stackedAreaChartOptions } from 'app/home/statistics/statistics.options';

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

    countJdl: Observable<string>;
    countYoRc: Observable<string>;
    countUser: Observable<string>; // user/owner ?
    countYoRcByDate: Observable<string>;

    constructor(private statisticsService: StatisticsService, private echartsService: NgxEchartsService) {}

    ngAfterViewInit() {
        this.countJdl = this.statisticsService.countJdl();
        this.countYoRc = this.statisticsService.countYoRC();

        this.statisticsService.getYoRCs().subscribe(yoRcs => {
            yoRcs.forEach(yoRc => this.dataFromDb.push(yoRc));
            console.log(this.dataFromDb);

            const yorcByDateList = this.groupDataBy(this.dataFromDb, 'creationDate');
            console.log(yorcByDateList);

            const clientFrameworkData = this.groupDataBy(this.dataFromDb, 'clientFramework');

            const reactData = this.filterDataByArrayValue(clientFrameworkData, obj => obj.clientFramework === 'react');
            // const angularData = this.filterDataByArrayValue(clientFrameworkData, obj => obj.clientFramework === "angularX");

            console.log(reactData);

            const generatedProjectsLineChart = new LineChart(
                this.echartsService,
                lineChartOptions(yorcByDateList),
                this.chart1,
                yorcByDateList
            ).build();
            // const clientFrameworkStackedAreaChart = new LineChart(this.echartsService, stackedAreaChartOptions(reactData, angularData), this.chart3, reactData).build();
            const clientFrameworkPieChart = new BasicChart(this.echartsService, pieChartOptions(clientFrameworkData), this.chart2).build();

            this.charts.push({ generatedProjectsLineChart });
            // this.charts.push({ clientFrameworkStackedAreaChart });
            this.charts.push({ clientFrameworkPieChart });

            this.updateRelatedBasicChartOf(
                yorcByDateList,
                clientFrameworkData,
                generatedProjectsLineChart.chartInstance,
                clientFrameworkPieChart
            );
        });
    }

    private updateRelatedBasicChartOf(data: any, basicChartData: any, chartInstance: any, basicChartInstance: any) {
        chartInstance.on('dataZoom', () => {
            const minDate = Object.keys(data)[chartInstance.getOption().dataZoom[0].startValue];
            const maxDate = Object.keys(data)[chartInstance.getOption().dataZoom[0].endValue];

            const arr = this.filterDataByArrayValue(
                basicChartData,
                obj => new Date(obj.creationDate) >= new Date(minDate) && new Date(obj.creationDate) <= new Date(maxDate)
            );

            var xd = this.charts[1].clientFrameworkPieChart.chartInstance.setOption(arr);
            console.log(xd);
        });
    }

    private updateData(alldata: any, data1: any, data2: any, fstDate: Date, sndDate: Date) {
        const test = Object.keys(data1).filter(date => new Date(date) >= fstDate && new Date(date) <= sndDate);
        const test2 = Object.keys(data2).filter(date => new Date(date) >= fstDate && new Date(date) <= sndDate).length;
        console.log(test);
        console.log(test2);
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
            .reduce((res, key) => (res[key] = data[key]), {});
    }

    private filterDataByValue(data: any, predicate: Function) {
        return Object.keys(data)
            .filter(key => predicate(data[key]))
            .reduce((res, key) => (res[key] = data[key]), {});
    }

    private filterDataByArrayValue(data: any, predicate: Function) {
        let caca = Object.keys(data).map(key =>
            data[key].filter(item => predicate(item)).reduce((acc, current) => {
                acc[key] = data[key];
                return acc;
            }, {})
        );
        console.log(caca);
        return caca;
    }
}
