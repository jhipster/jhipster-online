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
import { Component, AfterViewInit, OnInit } from '@angular/core';

import { StatisticsService } from './statistics.service';
import { DataSet } from 'app/home/statistics/statistics.model';

@Component({
    selector: 'jhi-statistics',
    templateUrl: './statistics.component.html',
    styleUrls: ['statistics.scss']
})
export class StatisticsComponent implements AfterViewInit, OnInit {
    ngAfterViewInit(): void {}

    chartData = [];

    options: any;

    constructor(private statisticsService: StatisticsService) {}

    ngOnInit() {
        const xAxisData = [];
        const data1 = [];
        const data2 = [];

        for (let i = 0; i < 100; i++) {
            xAxisData.push('category' + i);
            data1.push((Math.sin(i / 5) * (i / 5 - 10) + i / 6) * 5);
            data2.push((Math.cos(i / 5) * (i / 5 - 10) + i / 6) * 5);
        }

        this.options = {
            legend: {
                data: ['bar', 'bar2'],
                align: 'left'
            },
            tooltip: {},
            xAxis: {
                data: xAxisData,
                silent: false,
                splitLine: {
                    show: false
                }
            },
            yAxis: {},
            series: [
                {
                    name: 'bar',
                    type: 'bar',
                    data: data1,
                    animationDelay: function(idx) {
                        return idx * 10;
                    }
                },
                {
                    name: 'bar2',
                    type: 'bar',
                    data: data2,
                    animationDelay: function(idx) {
                        return idx * 10 + 100;
                    }
                }
            ],
            animationEasing: 'elasticOut',
            animationDelayUpdate: function(idx) {
                return idx * 5;
            }
        };

        var ctx = document.getElementById('myChart');

        let clientFrameworkData,
            buildToolData,
            clientPackageManagerData,
            devDatabaseTypeData = {};
        // this.statisticsService.countYoRC().subscribe(count => console.log(count));
        this.statisticsService.getYoRCs().subscribe(yoRcs => {
            yoRcs.forEach(yoRc => this.chartData.push(yoRc));
            clientFrameworkData = this.groupDataBy(this.chartData, 'clientFramework');
            let test = this.groupDataBy(this.chartData, 'createdDate');
            let react = this.filterDataBy(test, 'clientFramework', 'react');
            let angular = this.filterDataBy(test, 'clientFramework', 'angularX');
            let arr = [];
            let arr2 = [];
            for (const elem in react) {
                arr.push({ t: new Date(elem), y: react[elem].length });
            }
            for (const elem in angular) {
                arr2.push({ t: new Date(elem), y: angular[elem].length });
            }
            arr = arr.sort((a: any, b: any) => a.t.toISOString().localeCompare(b.t.toISOString()));
            arr2 = arr2.sort((a: any, b: any) => a.t.toISOString().localeCompare(b.t.toISOString()));

            // this.generateTimeLineChart('clientFramework',
            //     [new DataSet(
            //         'react',
            //         arr,
            //         'rgba(47, 214, 255, 0.9)',
            //         'rgba(47, 214, 255, 0.9)',
            //         'rgba(0, 0, 0, 0)',
            //         'rgba(0, 0, 0, 0)'),
            //     new DataSet(
            //         'angular',
            //         arr2,
            //         'rgba(221, 0, 49, 0.9)',
            //         'rgba(221, 0, 49, 0.9)',
            //         'rgba(0, 0, 0, 0)',
            //         'rgba(0, 0, 0, 0)',
            //         '-1')]
            // );

            // buildToolData = this.simplifyData(this.groupDataBy(this.chartData, 'buildTool'));
            // clientPackageManagerData = this.simplifyData(this.groupDataBy(this.chartData, 'clientPackageManager'));
            devDatabaseTypeData = this.groupDataBy(this.chartData, 'devDatabaseType');
            // this.generateBasicCounterChart(
            //     'clientPackageManager', 'bar', clientFrameworkData,
            //     ['rgba(47, 214, 255, 0.7)', 'rgba(221, 0, 49, 0.7)']);
            // this.generateCountChart('buildTool', 'line', buildToolData, 'izi monnay 2');
            // this.generateCountChart('clientPackageManager', 'doughnut', clientPackageManagerData, 'izi monnay 3');
            //     this.generateBasicCounterChart('devDatabaseType', 'line', devDatabaseTypeData,
            //          ['red', 'purple', 'blue', 'green', 'yellow']);
        });
    }

    // generateTimeLineChart(canvas: string, datasets: DataSet[], options?: any) {
    //     return new Chart(document.getElementById(canvas), {
    //         type: 'line',
    //         data: {
    //             labels: datasets[0].data.map((e: any) => e.t),
    //             datasets
    //         },
    //         options: {
    //             pan: {
    //                 enabled: true,
    //                 mode: 'x',
    //             },
    //             zoom: {
    //                 enabled: true,
    //                 mode: 'x',
    //             },
    //             responsive: true,
    //             title:{
    //                 display:true,
    //                 text:"Chart.js Time Point Data"
    //             },
    //             scales: {
    //                 xAxes: [{
    //                     type: "time",
    //                     unit: 'day',
    //                     unitStepSize: 1,
    //                     time: {
    //                         displayFormats: {
    //                         }
    //                     }
    //                 }],
    //                 yAxes: [{
    //                     ticks: {
    //                         beginAtZero: true
    //                     },
    //                     stacked: true
    //                 }],
    //             },
    //             elements: {
    //                 line: {
    //                     tension: 0.0000000001
    //                 }
    //             }
    //         }
    //     });
    // }

    // generateBasicCounterChart(canvas: string, type: string, data: any, backgroundColor: string[], options?: any) {
    //     return new Chart(document.getElementById(canvas), {
    //         type: 'bar',
    //         data: {
    //             labels: Object.keys(data),
    //             datasets: [
    //                 {
    //                     data: Object.keys(data).map(item => data[item].length),
    //                     backgroundColor
    //                 }
    //             ]
    //         },
    //         options: {
    //             pan: {
    //                 enabled: true,
    //                 mode: 'x',
    //             },
    //             zoom: {
    //                 enabled: true,
    //                 mode: 'x',
    //             }
    //         }
    //     });
    // }

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

    private filterDataBy(data: any, filter: string, value: string) {
        let filteredData = {};
        for (const elem in data) {
            if (data[elem]) {
                filteredData[elem] = data[elem].filter(cF => cF[filter] === value);
            }
        }
        return filteredData;
    }
}
