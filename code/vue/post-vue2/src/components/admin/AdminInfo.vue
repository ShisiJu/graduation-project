<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="studno" label="管理员学号" width="140"></el-table-column>
			<el-table-column prop="name" label="姓名" width="120"></el-table-column>
			<el-table-column prop="sex" label="性别" width="120"></el-table-column>
		</el-table>
	</div>
</template>

<script>
export default {
	name: 'j-admin-info',
	data() {
		return {
			tableData: [],
		};
	},
	created: function() {
		let userObj = this.$store.state.userInfo.obj;
		this.tableData.push(userObj)
	}
};
</script>

<style>
</style>
