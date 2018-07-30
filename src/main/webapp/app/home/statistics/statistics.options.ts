import * as moment from 'moment';

import { Frequency } from 'app/home/statistics/statistics.model';

export const prettifyDate = (date: string, frequency?: Frequency): string => {
    let format = '';
    switch (frequency) {
        case Frequency.YEARLY:
            format = 'YYYY';
            break;
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

export const lineChart = (data: any, frenquency: Frequency) => {
    return {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: data.map(obj => prettifyDate(obj.date, frenquency))
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

export const barChart = (data: any) => {
    return {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            data: data.map(obj => prettifyDate(obj.date))
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

export const comparingLineChart = (data: any, xAxis: string, yAxis: string) => {
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
            name: k,
            type: 'line',
            data: data.map(e => e.values[k])
        }));

    return {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            name: xAxis,
            type: 'category',
            boundaryGap: false,
            data: data.map(obj => prettifyDate(obj.date))
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

export const pieChart = (data: any) => {
    return {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: Object.keys(data).map(k => k)
        },
        series: [
            {
                name: 'Values',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: Object.keys(data).map(k => ({
                    name: k,
                    value: data[k]
                }))
            }
        ]
    };
};
