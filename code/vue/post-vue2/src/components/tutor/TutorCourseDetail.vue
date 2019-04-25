<template>
	<div>
		<div class="switch">
			<el-switch v-model="chart" active-text="表格" inactive-text="图表"></el-switch>
			----
			<span>评价人数: {{ count }}</span>
		</div>
		<div v-show="chart">
			<el-table :data="tableData" style="width: 100%;margin-top: 1.875rem;">
				<el-table-column prop="question" label="日期" width="180"></el-table-column>
				<el-table-column prop="A" label="A" width="180"></el-table-column>
				<el-table-column prop="B" label="B"></el-table-column>
				<el-table-column prop="C" label="C"></el-table-column>
				<el-table-column prop="D" label="D"></el-table-column>
				<el-table-column label="平均分">
					<template slot-scope="scope">
						{{
							(scope.row.A * 100 +
								scope.row.B * 85 +
								scope.row.C * 60 +
								scope.row.D * 40) /
								count
						}}
					</template>
				</el-table-column>
			</el-table>
		</div>

		<v-chart v-if="!chart" class="jchart" :options="socreChart"></v-chart>

		<div v-show="chart">
			<el-table :data="descData" style="width: 100%;margin-top: 1.875rem;">
				<el-table-column prop="desc" label="学生评价">
					<template slot-scope="scope" v-if="scope.$index < descShow">
						{{ scope.row.desc }}
					</template>
				</el-table-column>
			</el-table>
			<el-button type="text" @click="showMoreDesc()" v-if="existMoreDesc()">
				显示更多
			</el-button>
			<el-button type="text" v-if="!existMoreDesc()" disabled>
				没有了
			</el-button>
		</div>
	</div>
</template>

<script>
import ECharts from 'vue-echarts';
import 'echarts/lib/chart/bar';
import 'echarts/lib/component/tooltip';
import { findCourseDetail } from '@/api/axiosAPI';
export default {
	data() {
		return {
			tableData: [],
			originalData: [],
			chart: true,
			socreChart: {},
			descData: [],
			descShow: 3
		};
	},
	methods: {
		getShowData(oriData) {
			let showData = [];
			let questions = ['上课状态', '授课方式', '认真负责'];
			for (let i = 0; i < 3; i++) {
				let d = { question: questions[i], A: 0, B: 0, C: 0, D: 0 };
				showData.push(d);
			}

			oriData.forEach((ori, i) => {
				if (ori.type != 0) {
					if (ori.answer === '') return;
					let desc = { desc: ori.answer };
					this.descData.push(desc);
					return;
				}
				let index = ori.question;
				showData[index][ori.answer] += 1;
			});
			return showData;
		},
		changeCharts() {
			this.socreChart = {
				legend: {},
				tooltip: {},
				dataset: {
					dimensions: ['question', 'A', 'B', 'C', 'D'],
					source: this.tableData
				},
				xAxis: { type: 'category' },
				yAxis: {},
				series: [{ type: 'bar' }, { type: 'bar' }, { type: 'bar' }, { type: 'bar' }]
			};
		},
		showMoreDesc() {
			this.descShow += 5;
		},
		existMoreDesc() {
			return this.descShow < this.descData.length;
		}
	},
	created: function() {
		let course = this.$store.state.course;
		findCourseDetail(course).then(res => {
			this.originalData = res.data;
			this.tableData = this.getShowData(this.originalData);
			this.changeCharts();
		});
	},
	computed: {
		count() {
			return this.originalData.length / 4;
		}
	},
	components: {
		'v-chart': ECharts
	},
	mounted: function() {}
};
</script>

<style scoped="scoped">
.switch {
	position: relative;
	left: 0rem;
	width: 18.75rem;
}

.jchart {
	width: 90%;
	height: 25rem;
}

.pagination {
	width: 90%;
}
</style>
