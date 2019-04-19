<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="tutor" label="日期" width="140"></el-table-column>
			<el-table-column prop="name" label="课程名" width="140"></el-table-column>
			<el-table-column prop="academic_year" label="学年"></el-table-column>
			<el-table-column prop="term" label="学期"></el-table-column>
			<el-table-column label="操作">
				<el-button type="primary">评价课程</el-button>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
export default {
	name: 'j-student-course',
	data() {
		return {
			tableData: [
				{ name: '高数', sex: '男', tutor: '韩子扬', academic_year: '2018', term: '春季' }
			]
		};
	}
};
</script>

<style></style>
