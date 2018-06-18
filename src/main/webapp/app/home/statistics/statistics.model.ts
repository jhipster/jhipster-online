export interface Chart {
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
    itemsStyle?: any;

    stack?: string;
    label?: any;
    areaStyle?: any;
    data: any[];
}
