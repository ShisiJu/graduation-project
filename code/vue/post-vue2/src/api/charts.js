import echarts from 'echarts';
import 'echarts/lib/chart/pie';
import 'echarts/lib/chart/line';
import 'echarts/lib/component/tooltip';
import 'echarts/lib/component/legend';
import 'echarts/lib/component/title';



export const coursePieChartData = data => {
	return {
		title: {
			text: '课程评价等级分布',
			subtext: '可根据查询动态查看',
			x: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: '{a} <br/>{b} : {c} ({d}%)'
		},
		legend: {
			orient: 'vertical',
			left: 'left',
			data: ['A', 'B', 'C', 'D']
		},
		series: [{
			name: '评价',
			type: 'pie',
			radius: '55%',
			center: ['50%', '60%'],
			data: data,
			itemStyle: {
				emphasis: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}]
	};
}




export const coursePieAndLineChartData = (dom, oriData) => {
	let sourceData = [];
	let labelOption = [];
	let aOption = [];
	let bOption = [];
	let cOption = [];
	let dOption = [];
	labelOption.push('objectEvaluation');
	aOption.push('非常满意');
	bOption.push('比较满意');
	cOption.push('不太满意');
	dOption.push('不满意');
	oriData.forEach(ele => {
		labelOption.push(ele.name);
		aOption.push(ele.A);
		bOption.push(ele.B);
		cOption.push(ele.C);
		dOption.push(ele.D);
	});
	sourceData.push(labelOption);
	sourceData.push(aOption);
	sourceData.push(bOption);
	sourceData.push(cOption);
	sourceData.push(dOption);

	let myChart = echarts.init(dom);
	let option = {
		legend: {},
		tooltip: {
			trigger: 'axis',
			showContent: false
		},
		dataset: {
			source: sourceData
		},
		xAxis: {
			type: 'category'
		},
		yAxis: {
			gridIndex: 0
		},
		grid: {
			top: '55%'
		},
		series: [{
				type: 'line',
				smooth: true,
				seriesLayoutBy: 'row'
			},
			{
				type: 'line',
				smooth: true,
				seriesLayoutBy: 'row'
			},
			{
				type: 'line',
				smooth: true,
				seriesLayoutBy: 'row'
			},
			{
				type: 'line',
				smooth: true,
				seriesLayoutBy: 'row'
			},
			{
				type: 'pie',
				id: 'pie',
				radius: '30%',
				center: ['50%', '25%'],
				label: {
					formatter: '{b}: {@2012} ({d}%)'
				},
				encode: {
					itemName: 'objectEvaluation',
					value: '2012',
					tooltip: '2012'
				}
			}
		]
	};

	myChart.on('updateAxisPointer', function(event) {
		var xAxisInfo = event.axesInfo[0];
		if (xAxisInfo) {
			var dimension = xAxisInfo.value + 1;
			myChart.setOption({
				series: {
					id: 'pie',
					label: {
						formatter: '{b}: {@[' + dimension + ']} ({d}%)'
					},
					encode: {
						value: dimension,
						tooltip: dimension
					}
				}
			});
		}
	});

	myChart.setOption(option);
	return {};
}
