import { ElementRef } from '@angular/core';
import { NgxEchartsService } from 'ngx-echarts';

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
    private minZoom: string;
    private maxZoom: string;

    constructor(echartsService: NgxEchartsService, chartOptions: ChartOptions, elementRef: ElementRef, data: any) {
        super(echartsService, chartOptions, elementRef, data);
    }

    build() {
        this.chartInstance = this.echartsService.init(this.elementRef.nativeElement);
        this.chartInstance.setOption(this.chartOptions);
        // this.setupEventsHandlers();
        return this;
    }

    setupEventsHandlers() {
        this.chartInstance.on('dataZoom', () => {
            this.minZoom = Object.keys(this.data)[this.chartInstance.getOption().dataZoom[0].startValue];
            this.maxZoom = Object.keys(this.data)[this.chartInstance.getOption().dataZoom[0].endValue];
        });
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
    showSymbol?: boolean;
    label?: any;
    areaStyle?: any;
    data: any[];
}
