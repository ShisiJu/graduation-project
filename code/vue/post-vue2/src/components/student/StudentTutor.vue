<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="studno" label="学号" ></el-table-column>
			<el-table-column prop="name" label="姓名" ></el-table-column>
			<el-table-column prop="sex" label="性别"></el-table-column>
			<el-table-column prop="title" label="职称" ></el-table-column>
			<el-table-column prop="institute.name" label="学院" ></el-table-column>
		</el-table>
	</div>
</template>

<script>
	
	export default{
			name: 'j-student-tutor',
		data() {
			return {
				tableData: [],
			};
		},
		created: function() {
			let tutor = this.$store.state.userInfo.obj.group.tutor;
			if(tutor)
				this.tableData.push(tutor)
		}
	}
	
</script>

<style>
</style>

