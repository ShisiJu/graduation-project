<template>
	<div>
		<div style="float: left;"></div>
		<el-tabs v-model="activeName" @tab-click="handleClick">
			<el-tab-pane label="导师排名" name="tutor_rank"><statistics-table :data="tableData"></statistics-table></el-tab-pane>
			<el-tab-pane label="学院排名" name="institute_rank">
				<el-table :data="tableData">
					<el-table-column type="index" width="50" label="名次"></el-table-column>
					<el-table-column prop="name" label="名称"></el-table-column>
					<el-table-column prop="code" label="编码"></el-table-column>
					<el-table-column prop="A" label="非常满意数"></el-table-column>
					<el-table-column prop="B" label="满意数"></el-table-column>
					<el-table-column prop="C" label="一般满意数"></el-table-column>
					<el-table-column prop="D" label="不满意数"></el-table-column>
					<el-table-column prop="amount" label="评价数"></el-table-column>
					<el-table-column prop="APercent" label="非常满意率"></el-table-column>
					<el-table-column prop="BPercent" label="满意率"></el-table-column>
					<el-table-column prop="CPercent" label="非常满意率"></el-table-column>
					<el-table-column prop="DPercent" label="满意率"></el-table-column>
				</el-table>
			</el-tab-pane>
			<el-tab-pane label="上课状态" name="state_rank"><statistics-table :data="tableData"></statistics-table></el-tab-pane>
			<el-tab-pane label="授课方式" name="method_rank"><statistics-table :data="tableData"></statistics-table></el-tab-pane>
			<el-tab-pane label="认真负责" name="serious_rank"><statistics-table :data="tableData"></statistics-table></el-tab-pane>
			<el-tab-pane label="导师排名倒序" name="tutor_rank_desc"><statistics-table :data="tableData"></statistics-table></el-tab-pane>
			<el-tab-pane label="上课状态倒序" name="state_rank_desc"><statistics-table :data="tableData"></statistics-table></el-tab-pane>
			<el-tab-pane label="授课方式倒序" name="method_rank_desc"><statistics-table :data="tableData"></statistics-table></el-tab-pane>
			<el-tab-pane label="认真负责倒序" name="serious_rank_desc"><statistics-table :data="tableData"></statistics-table></el-tab-pane>
		</el-tabs>
	</div>
</template>

<script>
import { satisticByTutor, satisticByInstitute, satisticByTutorAndQuestion } from '@/api/axiosAPI';
import StatisticsTable from '@/components/common/StatisticsTable';

export default {
	name: 'j-admin-tutor-list',
	data() {
		return {
			tableData: [],
			asc: true,
			activeName: 'tutor_rank'
		};
	},
	components: {
		'statistics-table': StatisticsTable
	},
	methods: {
		handleClick(tab, event) {
			let asc = this.asc;
			let question = 0;
			switch (tab.name) {
				case 'tutor_rank':
					this.satisticByTutor({ asc });
					break;
				case 'institute_rank':
					satisticByInstitute({ asc }).then(res => {
						console.log(res.data);
						this.formatData(res.data);
						this.tableData = res.data;
					});
					break;
				case 'state_rank':
					question = 0;
					this.satisticQuestion({ asc, question });
					break;
				case 'method_rank':
					question = 1;
					this.satisticQuestion({ asc, question });
					break;
				case 'serious_rank':
					question = 2;
					this.satisticQuestion({ asc, question });
					break;
				case 'tutor_rank_desc':
					asc = false;
					this.satisticByTutor({ asc });
					break;
				case 'state_rank_desc':
					asc = false;
					question = 0;
					this.satisticQuestion({ asc, question });
					break;
				case 'method_rank_desc':
					asc = false;
					question = 1;
					this.satisticQuestion({ asc, question });
					break;
				case 'serious_rank_desc':
					asc = false;
					question = 2;
					this.satisticQuestion({ asc, question });
					break;
			}
		},
		satisticQuestion(data) {
			console.log('satisticQuestion');
			console.log(data);
			satisticByTutorAndQuestion(data).then(res => {
				console.log(res);
				this.formatData(res.data);
				this.tableData = res.data;
			});
		},
		satisticByTutor(data) {
			satisticByTutor(data).then(res => {
				this.tableData = res.data;
				this.formatData(res.data);
			});
		},
		formatData(data) {
			data.forEach(ele => {
				ele.APercent = ele.APercent.toFixed(2) + '%';
				ele.BPercent = ele.BPercent.toFixed(2) + '%';
				ele.CPercent = ele.CPercent.toFixed(2) + '%';
				ele.DPercent = ele.DPercent.toFixed(2) + '%';
			});
		}
	},
	created: function() {
		let asc = this.asc;
		this.satisticByTutor({ asc });
	}
};
</script>
<style></style>
