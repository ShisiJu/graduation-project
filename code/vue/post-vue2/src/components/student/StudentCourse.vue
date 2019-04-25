<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="course.academic_year" label="学年"></el-table-column>
			<el-table-column prop="course.term" label="学期"></el-table-column>
			<el-table-column prop="course.name" label="课程名"></el-table-column>
			<el-table-column prop="course.code" label="课程编码"></el-table-column>
			<el-table-column prop="course.tutor.institute.name" label="所属学院"></el-table-column>
			<el-table-column prop="course.tutor.name" label="导师"></el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button v-if="scope.row.status == 0"
						type="primary"
						@click.native.prevent="evaluateCourse(scope.row)"
						size="small"
					>
						评价课程
					</el-button>
					<el-button v-if="scope.row.status == 1"
						type="success"
						size="small"
						disabled
					>
						已评价
					</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
import { findStudentCourse } from '@/api/axiosAPI';

export default {
	name: 'j-student-info',
	created: function() {
		let studentId = this.$store.state.userInfo.obj.id;
		
		findStudentCourse({id:studentId}).then(res => {
			this.tableData = res.data;
			console.log(this.tableData);
		});
	},
	data() {
		return {
			tableData: []
		};
	},
	methods: {
		evaluateCourse: function(data) {
			console.log(data)
			this.$store.commit('setQuiz', data)
			this.$router.push('/student/quiz')
		}
	}
};
</script>

<style></style>
