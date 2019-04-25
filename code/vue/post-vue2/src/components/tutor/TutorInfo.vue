<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="studno" label="学号"></el-table-column>
			<el-table-column prop="name" label="姓名"></el-table-column>
			<el-table-column prop="sex" label="性别"></el-table-column>
			<el-table-column prop="institute.name" label="学院"></el-table-column>
			<el-table-column prop="title" label="职称"></el-table-column>
		</el-table>
	</div>
</template>

<script>
export default {
	name: 'j-tutor-info',
	data() {
		return {
			tableData: []
		};
	},
	created: function() {
		let userObj = this.$store.state.userInfo.obj;
		this.tableData.push(userObj);
	}
};
</script>

<style></style>
