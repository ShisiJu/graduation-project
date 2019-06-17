<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="academicYear" label="学年"></el-table-column>
			<el-table-column prop="term" label="学期"></el-table-column>
			<el-table-column prop="name" label="名称"></el-table-column>
			<el-table-column prop="code" label="编码"></el-table-column>
			<el-table-column prop="tutor.name" label="导师"></el-table-column>
			<el-table-column prop="amount" label="总数"></el-table-column>
			<el-table-column prop="currentNum" label="已选"></el-table-column>
			<el-table-column label="操作" width="260px">
				<template slot-scope="scope">
					<el-button size="small" @click="handleChoose(scope.row.id, scope.$index)">选择</el-button>
				</template>
			</el-table-column>
		</el-table>
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
import { saveStudentCourse, findOptionalCourse } from '@/api/axiosAPI';

export default {
	name: 'j-admin-group-list',
	data() {
		return {
			tableData: [],
			pageInfo: {},
			studentId: 1,
			index: 1,
			pageSize: 10,
			total: 10
		};
	},
	methods: {
		handleSizeChange(pageSize) {
			this.pageSize = pageSize;
			this.handleCurrentChange(this.index);
		},
		handleCurrentChange(index) {
			this.index = index;
			this.refreshTableData();
		},
		handleChoose(courseId) {
			let confiremd = confirm('确认选择');
			if (!confiremd) return;
			let studentId = this.studentId;
			let data = { courseId, studentId };

			saveStudentCourse(data).then(res => {
				if (res.statusText != 'OK') {
					alert('选择成功');
				} else {
					alert('选择成功');
				}
				this.refreshTableData();
			});
		},
		refreshTableData() {
			let data = this.pageInfo;
			data['index'] = this.index;
			data['studentId'] = this.studentId;
			data['pageSize'] = this.pageSize;
			findOptionalCourse(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		}
	},
	created: function() {
		this.studentId = this.$store.state.userInfo.obj.id;
		this.refreshTableData();
	}
};
</script>
<style></style>
