import echarts from 'echarts';


import 'echarts/lib/chart/pie';
import 'echarts/lib/chart/line';
import 'echarts/lib/chart/radar';
import 'echarts/lib/component/tooltip';
import 'echarts/lib/component/legend';
import 'echarts/lib/component/title';

import {
	computeSorce
} from '@/api/axiosAPI';


export const courseRadarChart = (dom, data) => {
	let myChart = echarts.init(dom);
	let autumn = data[8];
	let spring = data[9];

	let option = {
		title: {
			text: '今年课程状态'
		},
		tooltip: {},
		legend: {
			data: ['秋季', '春季']
		},
		radar: {
			name: {
				textStyle: {
					color: '#fff',
					backgroundColor: '#999',
					borderRadius: 3,
					padding: [2, 5]
				}
			},
			indicator: [{
					name: '仪容仪表',
					max: 100
				},
				{
					name: '授课方式',
					max: 100
				},
				{
					name: '认真负责',
					max: 100
				},

			]
		},
		series: [{
			name: '秋季VS春季',
			type: 'radar',
			data: [{
					value: [autumn.APercent, autumn.B, autumn.C],
					name: '秋季'
				},
				{
					value: [5000, 14000, 28000],
					name: '春季'
				}
			]
		}]
	};

	myChart.setOption(option);
	return {}
}


export const courseDetailChart = (dom, data) => {

	let myChart = echarts.init(dom);
	// 4 个
	let organizedData = [
		[],
		[],
		[],
		[]
	];

	for (let ele of data) {
		organizedData[0].push(ele['A']);
		organizedData[1].push(ele['B']);
		organizedData[2].push(ele['C']);
		organizedData[3].push(ele['D']);
	}


	let option = {
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		legend: {
			data: ['非常满意', '比较满意', '一般满意', '不满意']
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		xAxis: [{
			type: 'category',
			data: ['仪容仪表', '授课方式', '认真负责']
		}],
		yAxis: [{
			type: 'value'
		}],
		series: [{
				name: '非常满意',
				type: 'bar',
				barWidth: 55,
				stack: '满意程度',
				data: organizedData[0]
			},
			{
				name: '比较满意',
				type: 'bar',
				stack: '满意程度',
				data: organizedData[1]
			},
			{
				name: '一般满意',
				type: 'bar',
				stack: '满意程度',
				data: organizedData[2]
			},
			{
				name: '不满意',
				type: 'bar',
				stack: '满意程度',
				data: organizedData[3]
			}
		]
	}
	myChart.setOption(option);
	return {}
}



export const courseLineChartData = (dom, oriData) => {

	let myChart = echarts.init(dom);

	let xData = [];
	let seriesData = [];

	oriData.forEach(ele => {
		seriesData.push(computeSorce(ele));
		xData.push(ele.name);
	})

	let option = {
		title: {
			text: '近五年综合评价分数折线图（百分制）',
		},
		xAxis: {
			type: 'category',
			data: xData
		},
		yAxis: {
			type: 'value'
		},
		series: [{
			data: seriesData,
			type: 'line',
			smooth: true,
			label: {
				show: true,
				position: 'top',
				textStyle: {
					color: 'black',
					fontSize: 16
				}
			}
		}]
	}
	myChart.setOption(option);
	return {};
}


export const coursePieData = (dom, oriData) => {

	let myChart = echarts.init(dom);
	console.log(myChart)
	let seriesData = [{
			value: 0,
			name: '非常满意'
		},
		{
			value: 0,
			name: '比较满意'
		},
		{
			value: 0,
			name: '一般满意'
		},
		{
			value: 0,
			name: '不满意'
		}
	];

	oriData.forEach(ele => {
		seriesData[0].value = seriesData[0].value + ele.A;
		seriesData[1].value = seriesData[1].value + ele.B;
		seriesData[2].value = seriesData[2].value + ele.C;
		seriesData[3].value = seriesData[3].value + ele.D;
	})

	let option = {
		title: {
			text: '满意度分布饼状图',
			x: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
			orient: 'vertical',
			left: 'left',
			data: ['非常满意', '比较满意', '一般满意', '不满意']
		},
		series: [{
			name: '学生评价',
			type: 'pie',
			radius: '55%',
			center: ['50%', '60%'],
			data: seriesData,
			itemStyle: {
				emphasis: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}]
	};


	myChart.setOption(option);
	return {};

}
