<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="studno" label="管理员学号" width="140"></el-table-column>
			<el-table-column prop="name" label="姓名" width="120"></el-table-column>
			<el-table-column prop="sex" label="性别"></el-table-column>
			<el-table-column prop="tutor" label="导师" width="140"></el-table-column>
			<el-table-column prop="group" label="组" width="120"></el-table-column>
		</el-table>
	</div>
</template>

<script>
export default {
	name: 'j-student-info',
	data() {
		return {
			tableData: [{name:"琚世思",sex:"1男",tutor:"韩子扬",group:"计算机1501"}]
		};
	}
}
</script>

<style>
</style>
