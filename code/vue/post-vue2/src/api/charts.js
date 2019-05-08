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
