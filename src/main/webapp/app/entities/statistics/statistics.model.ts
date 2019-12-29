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
 * Unless required by applicable law or agreed to in writing; software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { ElementRef } from '@angular/core';
import { NgxEchartsService } from 'ngx-echarts';

export enum Frequency {
    DEFAULT = 'default',
    MONTHLY = 'monthly',
    WEEKLY = 'weekly',
    DAILY = 'daily',
    HOURLY = 'hourly'
}

export class Chart {
    public chartInstance;

    constructor(
        protected echartsService: NgxEchartsService,
        protected chartOptions: ChartOptions,
        protected elementRef: ElementRef,
        protected data?: any
    ) {}

    build() {
        this.chartInstance = this.echartsService.init(this.elementRef.nativeElement);
        this.chartInstance.clear();
        this.chartInstance.setOption(this.chartOptions);
        return this;
    }
}

export interface ChartOptions {
    title?: ChartTitle;
    tooltip?: any;
    toolbox?: any;
    grid?: any;
    xAxis?: any;
    yAxis?: any;
    dataZoom?: any;
    legend?: any;
    series: ChartSeries[];
}

export interface ChartTitle {
    text?: string;
    subtext?: string;
    left?: string;
}

export interface ChartSeries {
    name?: string;
    type?: string;

    radius?: string;
    center?: string[];
    selectedMode?: string;
    itemStyle?: any;

    stack?: string;
    smooth?: boolean;
    symbolSize?: number;
    showSymbol?: boolean;
    label?: any;
    areaStyle?: any;
    data: any[];
}
