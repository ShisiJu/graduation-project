<template>
	<div>
		<div style="float: left;"></div>
		<el-tabs v-model="activeName" @tab-click="handleClick">
			<el-tab-pane label="导师排名" name="first">
				<el-table :data="tableData">
					<el-table-column type="index" width="50" label="名次"></el-table-column>
					<el-table-column prop="studno" label="学号"></el-table-column>
					<el-table-column prop="name" label="姓名"></el-table-column>
					<el-table-column prop="sex" label="性别"></el-table-column>
					<el-table-column prop="title" label="职称"></el-table-column>
					<el-table-column prop="APercent" label="非常满意率"></el-table-column>
					<el-table-column prop="BPercent" label="满意率"></el-table-column>
				</el-table>
			</el-tab-pane>
			<el-tab-pane label="学院排名" name="second">
				<el-table :data="tableData">
					<el-table-column type="index" width="50" label="名次"></el-table-column>
					<el-table-column prop="name" label="姓名"></el-table-column>
					<el-table-column prop="code" label="编码"></el-table-column>
					<el-table-column prop="A" label="非常满意"></el-table-column>
				</el-table>
			</el-tab-pane>
			<el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
			<el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>
		</el-tabs>
	</div>
</template>

<script>
import { satisticByTutor, satisticByInstitute } from '@/api/axiosAPI';

export default {
	name: 'j-admin-tutor-list',
	data() {
		return {
			tableData: [],
			activeName: 'first'
		};
	},
	methods: {
		handleClick(tab, event) {
			switch (tab.name) {
				case 'first':
					satisticByTutor({}).then(res => {
						this.tableData = res.data;
						res.data.forEach(ele => {
							ele.APercent = APercent.toFixed(2);
							ele.BPercent = BPercent.toFixed(2);
							ele.CPercent = CPercent.toFixed(2);
							ele.DPercent = CPercent.toFixed(2);
						});
						console.log(res.data);
					});
					break;
				case 'second':
					satisticByInstitute({}).then(res => {
						this.tableData = res.data;
					});
					break;
			}
		},
		refreshTableData() {
			//let data = { instituteIds, studno, pageSize, index };
			satisticByTutor({}).then(res => {
				this.tableData = res.data;
			});
		}
	},
	created: function() {
		this.refreshTableData();
	}
};
</script>
<style></style>
