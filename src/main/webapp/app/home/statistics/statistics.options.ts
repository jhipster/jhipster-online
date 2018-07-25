import * as moment from 'moment';

export const prettifyDate = (date: string): string => moment(new Date(date)).format('L');

export const lineChart = (data: any) => {
    return {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: data.map(obj => prettifyDate(obj.date))
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
