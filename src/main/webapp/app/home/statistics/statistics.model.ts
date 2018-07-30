import { ElementRef } from '@angular/core';
import { NgxEchartsService } from 'ngx-echarts';

export enum Frequency {
    YEARLY = 'yearly',
    MONTHLY = 'monthly',
    WEEKLY = 'weekly',
    DAILY = 'daily',
    HOURLY = 'hourly'
}

export class BasicChart {
    public chartInstance;

    constructor(
        protected echartsService: NgxEchartsService,
        protected chartOptions: ChartOptions,
        protected elementRef: ElementRef,
        protected data?: any
    ) {}

    build() {
        this.chartInstance = this.echartsService.init(this.elementRef.nativeElement);
        this.chartInstance.setOption(this.chartOptions);
        return this;
    }
}

export class LineChart extends BasicChart {
    constructor(echartsService: NgxEchartsService, chartOptions: ChartOptions, elementRef: ElementRef, data: any) {
        super(echartsService, chartOptions, elementRef, data);
    }

    build() {
        this.chartInstance = this.echartsService.init(this.elementRef.nativeElement);
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

    // pie
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
