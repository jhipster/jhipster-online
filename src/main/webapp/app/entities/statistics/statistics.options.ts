import * as moment from 'moment';

import { Frequency } from './statistics.model';
import { StatisticsUtils } from './statistics.utils';

const prettifyDate = (date: string, frequency?: Frequency): string => {
    let format = '';
    switch (frequency) {
        case Frequency.MONTHLY:
            format = 'MMMM YYYY';
            break;
        case Frequency.HOURLY:
            format = 'HH:00';
            break;
        case Frequency.WEEKLY:
        case Frequency.DAILY:
        default:
            format = 'L';
            break;
    }

    return moment(new Date(date)).format(format);
};

export const lineChartOptions = (data: any, frequency: Frequency) => {
    return {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: data.map(obj => prettifyDate(obj.date, frequency))
        },
        yAxis: {
            type: 'value'
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        series: [
            {
                data: data.map(obj => obj.count),
                type: 'line',
                smooth: true
            }
        ]
    };
};

export const barChartOptions = (data: any, frequency: Frequency) => {
    return {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            data: data.map(obj => prettifyDate(obj.date, frequency))
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: data.map(obj => obj.count),
                type: 'bar'
            }
        ]
    };
};

export const comparingLineChartOptions = (data: any, xAxis: string, yAxis: string, frequency: Frequency) => {
    const series = data
        .map(e => Object.keys(e.values))
        .reduce((acc, current) => {
            current.forEach(e => {
                if (!acc.includes(e)) {
                    acc.push(e);
                }
            });

            return acc;
        }, [])
        .map(k => ({
            name: StatisticsUtils.getDisplayName(k),
            type: 'line',
            connectNullData: true,
            data: data.map(e => (e.values[k] ? e.values[k] : 0))
        }));

    return {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            name: xAxis,
            type: 'category',
            connectNullData: true,
            boundaryGap: false,
            data: data.map(obj => prettifyDate(obj.date, frequency))
        },
        yAxis: {
            name: yAxis,
            type: 'value'
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        series
    };
};

export const pieChartOptions = (data: any) => {
    return {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            type: 'scroll',
            data: Object.keys(data).map(k => StatisticsUtils.getDisplayName(k))
        },
        series: [
            {
                name: 'Values',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: Object.keys(data).map(k => ({
                    name: StatisticsUtils.getDisplayName(k),
                    value: data[k]
                }))
            }
        ]
    };
};
