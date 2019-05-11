<template>
	<div>
		<div>
			<el-input v-model="searchObj.name" placeholder="课程名称" style="width: 9rem;"></el-input>
			<el-input v-model="searchObj.academicYear" placeholder="学年" style="width: 9rem"></el-input>
			<el-select v-model="searchObj.term" placeholder="学期" style="width: 9rem" clearable>
				<el-option v-for="item in termOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
			</el-select>
			<el-button style="margin-left: 1.5rem;" icon="el-icon-search" circle @click="handleSearch"></el-button>
		</div>

		<div>
			<el-table :data="tableData">
				<el-table-column prop="course.academicYear" label="学年"></el-table-column>
				<el-table-column prop="course.term" label="学期"></el-table-column>
				<el-table-column prop="course.name" label="课程名"></el-table-column>
				<el-table-column prop="course.code" label="课程编码"></el-table-column>
				<el-table-column prop="course.tutor.institute.name" label="所属学院"></el-table-column>
				<el-table-column prop="course.tutor.name" label="导师"></el-table-column>
				<el-table-column label="操作">
					<template slot-scope="scope">
						<el-button v-if="scope.row.status == 0" type="primary" @click.native.prevent="evaluateCourse(scope.row)" size="small">评价课程</el-button>
						<el-button v-if="scope.row.status == 1" type="success" size="small" disabled>已评价</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<div class="Pagination">
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:current-page="index"
				:page-sizes="[10, 20, 30, 40]"
				:page-size="pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="total"
			></el-pagination>
		</div>
	</div>
</template>

<script>
import { findStudentCourse, findStudentCourseWithPage } from '@/api/axiosAPI';

export default {
	name: 'j-student-info',
	created: function() {
		this.studentId =  this.$store.state.userInfo.obj.id;
		this.refreshTableData();
	},
	data() {
		return {
			tableData: [],
			searchObj: {},
			index: 1,
			pageSize: 10,
			total: 10,
			studentId: '',
			termOptions: [{ value: '秋季' }, { value: '春季' }]
		};
	},
	methods: {
		evaluateCourse: function(data) {
			this.$store.commit('setQuiz', data);
			this.$router.push('/student/quiz');
		},
		handleSizeChange(pageSize) {
			this.pageSize = pageSize;
			this.handleCurrentChange(this.index);
		},
		handleCurrentChange(index) {
			this.index = index;
			this.refreshTableData();
		},
		handleSearch() {
			this.refreshTableData();
		},
		refreshTableData() {
			let data = this.searchObj;
			data['studentId'] = this.studentId;
			data['index'] = this.index;
			data['pageSize'] = this.pageSize;
			findStudentCourseWithPage(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		}
	}
};
</script>

<style></style>
