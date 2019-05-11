			console.log('----changePieOption----');
			console.log(this.selectedTutorId);

			console.log(this.$refs.recent5YearChart);

			let dom = this.$refs.recent5YearChart;
			let myChart = echarts.init(dom);
			let option = coursePieAndLineChartData();

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