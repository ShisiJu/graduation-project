<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="studno" label="管理员学号"></el-table-column>
			<el-table-column prop="name" label="姓名" ></el-table-column>
			<el-table-column prop="sex" label="性别" ></el-table-column>
		</el-table>
		<hr />
		<update-pwd></update-pwd>
	</div>
</template>

<script>
import UpdatePwd from '@/components/common/UpdatePwd';

export default {
	name: 'j-admin-info',
	data() {
		return {
			tableData: []
		};
	},
	created: function() {
		let userObj = this.$store.state.userInfo.obj;
		this.tableData.push(userObj);
	},
	components: {
		UpdatePwd
	}
};
</script>

<style></style>
