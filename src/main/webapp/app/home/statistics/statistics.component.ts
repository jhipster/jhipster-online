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
import { Component, AfterViewInit } from '@angular/core';
import _ from 'lodash';
import * as moment from 'moment';

import { StatisticsService } from './statistics.service';
import { Chart, ChartTitle, ChartSeries } from 'app/home/statistics/statistics.model';
import { Observable } from 'rxjs';

@Component({
    selector: 'jhi-statistics',
    templateUrl: './statistics.component.html'
})
export class StatisticsComponent implements AfterViewInit {
    dataFromDb = [];

    countJdl: Observable<string>;
    countYoRc: Observable<string>;

    charts = [];

    options: Chart;
    options2: Chart;
    options3: Chart;

    static names: any = {
        react: 'React',
        angularX: 'Angular 2+',

        oracle: 'Oracle',
        mysql: 'MySQL',
        postgresql: 'PostgreSQL',
        mariadb: 'MariaDB'
    };

    constructor(private statisticsService: StatisticsService) {}

    ngAfterViewInit() {
        this.countJdl = this.statisticsService.countJdl();
        this.countYoRc = this.statisticsService.countYoRC();

        let clientFrameworkData, devDatabaseData;

        this.statisticsService.getYoRCs().subscribe(yoRcs => {
            yoRcs.forEach(yoRc => this.dataFromDb.push(yoRc));
            clientFrameworkData = this.groupDataBy(this.dataFromDb, 'clientFramework');
            devDatabaseData = this.groupDataBy(this.dataFromDb, 'devDatabaseType');
            let test = this.groupDataBy(this.dataFromDb, 'creationDate');
            let react = this.filterDataBy(test, 'clientFramework', 'react');
            let angular = this.filterDataBy(test, 'clientFramework', 'angularX');
            let arr = [];
            let arr2 = [];
            for (const elem in react) {
                arr.push({ x: new Date(elem), y: react[elem].length });
            }
            for (const elem in angular) {
                arr2.push({ x: new Date(elem), y: angular[elem].length });
            }

            arr = arr.sort((a, b) => b.x.toISOString().localeCompare(a.x.toISOString())).reverse();
            arr2 = arr2.sort((a, b) => a.x.toISOString().localeCompare(b.x.toISOString())).reverse();
            console.log(arr);

            this.options = {
                title: { text: 'React/Angular distribution' },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                series: [
                    {
                        type: 'pie',
                        name: 'lol',
                        radius: '65%',
                        center: ['50%', '50%'],
                        selectedMode: 'single',
                        data: Object.keys(clientFrameworkData).map(item => {
                            return {
                                name: StatisticsComponent.prettifyName(item),
                                value: clientFrameworkData[item].length
                            };
                        })
                    }
                ]
            };

            this.options3 = {
                title: { text: 'Dev databases' },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                series: [
                    {
                        type: 'pie',
                        name: 'lol',
                        radius: '65%',
                        center: ['50%', '50%'],
                        selectedMode: 'single',
                        data: Object.keys(devDatabaseData).map(item => {
                            return {
                                name: StatisticsComponent.prettifyName(item),
                                value: devDatabaseData[item].length
                            };
                        })
                    }
                ]
            };

            this.options2 = {
                title: { text: '' },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: { backgroundColor: '#6a7985' }
                    }
                },
                toolbox: {
                    feature: { saveAsImage: {} }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data: arr.map((e: any) => moment(e.x).format('L'))
                    }
                ],
                yAxis: [{ type: 'value' }],
                dataZoom: [{ type: 'inside' }],
                series: [
                    {
                        name: 'REACT',
                        type: 'line',
                        stack: '总量',
                        areaStyle: { normal: {} },
                        data: arr.map((e: any) => e.y)
                    },
                    {
                        name: 'ANGULAR',
                        type: 'line',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        areaStyle: { normal: {} },
                        data: arr2.map((e: any) => e.y)
                    }
                ]
            };
        });
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

    private filterDataBy(data: any, filter: string, value: string) {
        let filteredData = {};
        for (const elem in data) {
            if (data[elem]) {
                filteredData[elem] = data[elem].filter(cF => cF[filter] === value);
            }
        }
        return filteredData;
    }

    private static prettifyName(rawName: string) {
        return StatisticsComponent.names[rawName] || rawName;
    }
}
