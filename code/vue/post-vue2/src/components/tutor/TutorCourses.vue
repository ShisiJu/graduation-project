<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="academicYear" label="学年"></el-table-column>
			<el-table-column prop="term" label="学期"></el-table-column>
			<el-table-column prop="name" label="课程名"></el-table-column>
			<el-table-column prop="code" label="课程编码"></el-table-column>
			<el-table-column prop="tutor.institute.name" label="所属学院"></el-table-column>
			<el-table-column prop="tutor.name" label="导师"></el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button
						type="primary"
						@click.native.prevent="checkCourse(scope.row)"
						size="small"
					>
						查看评价
					</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
import { findCoursesByTutor } from '@/api/axiosAPI';

export default {
	data() {
		return {
			tableData: []
		};
	},
	methods: {
		checkCourse(course) {
			this.$store.commit('setCourse', course);
			this.$router.push('/tutor/course-detail');
		}
	},
	created: function() {
		let tutorId = this.$store.state.userInfo.obj.id;
		findCoursesByTutor({ id: tutorId }).then(res => {
			this.tableData = res.data;
			console.log(res.data);
		});
	}
};
</script>

<style></style>
