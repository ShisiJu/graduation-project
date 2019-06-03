<template>
	<div>
	<el-table :data="data">
		<el-table-column type="index" width="50" label="名次"></el-table-column>
		<el-table-column prop="studno" label="学号"></el-table-column>
		<el-table-column prop="name" label="姓名"></el-table-column>
		<el-table-column prop="sex" label="性别"></el-table-column>
		<el-table-column prop="title" label="职称"></el-table-column>
		<el-table-column prop="A" label="非常满意数"></el-table-column>
		<el-table-column prop="B" label="满意数"></el-table-column>
		<el-table-column prop="C" label="一般满意数"></el-table-column>
		<el-table-column prop="D" label="不满意数"></el-table-column>
		<el-table-column prop="amount" label="评价数"></el-table-column>
		<el-table-column prop="APercent" label="非常满意率"></el-table-column>
		<el-table-column prop="BPercent" label="满意率"></el-table-column>
		<el-table-column prop="CPercent" label="一般满意率"></el-table-column>
		<el-table-column prop="DPercent" label="不满意率"></el-table-column>
	</el-table>
	</div>
</template>

<script>
export default {
	name: 'statistics-table',
	data() {
		return {};
	},
	props: {
		data: {
			type: Array,
			required: true
		}
	},
	created() {
		console.log(this.data);
	}
};
</script>

<style></style>
