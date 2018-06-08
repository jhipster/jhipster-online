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
import { Chart } from 'chart.js';

import { StatisticsService } from './statistics.service';

@Component({
    selector: 'jhi-statistics',
    templateUrl: './statistics.component.html'
})
export class StatisticsComponent implements AfterViewInit {
    chartData = [];

    constructor(private statisticsService: StatisticsService) {}

    ngAfterViewInit() {
        let clientFrameworkData,
            buildToolData,
            clientPackageManagerData,
            devDatabaseTypeData = {};
        // this.statisticsService.countYoRC().subscribe(count => console.log(count));
        this.statisticsService.getYoRCs().subscribe(yoRcs => {
            yoRcs.forEach(yoRc => this.chartData.push(yoRc));
            clientFrameworkData = this.groupDataBy('clientFramework');
            console.log(clientFrameworkData);
            buildToolData = this.simplifyData(this.groupDataBy('buildTool'));
            clientPackageManagerData = this.simplifyData(this.groupDataBy('clientPackageManager'));
            devDatabaseTypeData = this.simplifyData(this.groupDataBy('devDatabaseType'));
            // this.generateChart('clientFramework', 'doughnut', clientFrameworkData, 'izi monnay');
            // this.generateChart('buildTool', 'doughnut', buildToolData, 'izi monnay 2');
            // this.generateChart('clientPackageManager', 'doughnut', clientPackageManagerData, 'izi monnay 3');
            // this.generateChart('devDatabaseType', 'pie', devDatabaseTypeData, 'izi monnay 4');
        });
    }

    generateChart(canvas: string, type: string, info: any, label: string, options?: any) {
        return new Chart(document.getElementById(canvas), {
            type,
            data: {
                labels: Object.keys(info),
                datasets: [
                    {
                        label,
                        data: Object.keys(info).map(item => info[item]),
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255,99,132,1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }
                ]
            },
            options
        });
    }

    private groupDataBy(property: string) {
        return this.chartData.reduce((acc, current) => {
            const key = current[property];
            if (!acc[key]) {
                acc[key] = [];
            }
            acc[key].push(current);
            return acc;
        }, {});
    }

    private simplifyData(data: any) {
        const simplifiedData = {};
        for (const elem in data) {
            if (data[elem]) {
                simplifiedData[elem] = data[elem].length;
            }
        }
        return simplifiedData;
    }
}
