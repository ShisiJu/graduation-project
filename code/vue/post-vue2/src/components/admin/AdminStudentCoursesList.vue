<template>
	<div>
		<div>
			<el-input v-model="searchObj.name" placeholder="课程名称" style="width: 9rem;"></el-input>
			<el-input v-model="searchObj.academicYear" placeholder="学年" style="width: 9rem"></el-input>
			<el-select v-model="searchObj.term" placeholder="学期" style="width: 9rem" clearable>
				<el-option v-for="item in termOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
			</el-select>
			<el-input v-model="searchObj.studentStudno" placeholder="学生学号" style="width: 9rem"></el-input>

			<el-input v-if="type === 2" v-model="searchObj.tutorStudno" placeholder="导师学号" style="width: 9rem"></el-input>
			<el-button style="margin-left: 1.5rem;" icon="el-icon-search" circle @click="handleSearch"></el-button>
			<el-button @click="clearSearch" circle><i class="el-icon-delete"></i></el-button>
			<div style="width: 8rem;float: right;margin-top: 0.25rem;height: 4rem;">
				<el-upload
					action="api/poi/import-student-course"
					:on-preview="handlePreview"
					:on-remove="handleRemove"
					:before-remove="beforeRemove"
					multiple
					:limit="1"
					:on-exceed="handleExceed"
					:file-list="fileList"
				>
					<el-button size="small">导入EXCEL</el-button>
				</el-upload>
			</div>
			<div style="width: 14rem;float: right;margin-top: 0.25rem;height: 4rem;">
				<el-button size="small" @click="handleImportModel()">导入模版下载</el-button>
				<el-button size="small" @click="handleExport()">导出数据</el-button>
			</div>
		</div>

		<div>
			<el-table :data="tableData">
				<el-table-column prop="course.academicYear" label="学年"></el-table-column>
				<el-table-column prop="course.term" label="学期"></el-table-column>
				<el-table-column prop="course.name" label="课程名"></el-table-column>
				<el-table-column prop="course.tutor.institute.name" label="所属学院"></el-table-column>
				<el-table-column prop="course.tutor.name" label="导师"></el-table-column>
				<el-table-column prop="student.name" label="学生"></el-table-column>
				<el-table-column prop="score" label="分数">
					<template slot-scope="scope">
						<div v-if="scope.row.score >= 60" style="color: #006600;">{{ scope.row.score }}</div>
						<div v-if="scope.row.score < 60" style="color:red;">{{ scope.row.score }}</div>
					</template>
				</el-table-column>

				<el-table-column label="操作" width="260px">
					<template slot-scope="scope">
						<el-button size="small" @click="handleEdit(scope.row, scope.$index)">编辑</el-button>
						<el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
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

		<el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
			<el-form :model="selectedTableColumn">
				<el-form-item label="课程名称" label-width="6.25rem"><el-input disabled v-model="selectedTableColumn.course.name" auto-complete="off"></el-input></el-form-item>
				<el-form-item label="学年" label-width="6.25rem"><el-input disabled v-model="selectedTableColumn.course.academicYear"></el-input></el-form-item>
				<el-form-item label="课程编号" label-width="6.25rem"><el-input disabled v-model="selectedTableColumn.course.code"></el-input></el-form-item>
				<el-form-item label="学院名称" label-width="6.25rem"><el-input disabled v-model="selectedTableColumn.course.tutor.institute.name"></el-input></el-form-item>
				<el-form-item label="学生名称" label-width="6.25rem"><el-input disabled v-model="selectedTableColumn.student.name"></el-input></el-form-item>
				<el-form-item label="学生学号" label-width="6.25rem"><el-input disabled v-model="selectedTableColumn.student.studno"></el-input></el-form-item>
				<el-form-item label="分数" label-width="6.25rem"><el-input v-model="selectedTableColumn.score"></el-input></el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="handleCancle">取 消</el-button>
				<el-button type="primary" @click="handleConfirm">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { findStudentCourseWithPage, updateScore, deleteStudentCourse, exportStudentCourse } from '@/api/axiosAPI';

export default {
	name: 'student-course-list',
	created: function() {
		this.refreshTableData();
	},
	data() {
		return {
			dialogFormVisible: false,
			dialogTitle: '',
			edited: false,
			selectedTableColumn: {},
			tableData: [],
			searchObj: {},
			index: 1,
			pageSize: 10,
			total: 10,
			termOptions: [{ value: '秋季' }, { value: '春季' }],
			fileList: [],
			type: 2
		};
	},
	methods: {
		handleImportModel() {
			window.open(window.location.origin + '/api/poi/export-student-course-model');
		},
		handleExport() {
			if (this.type === 1) {
				this.searchObj['tutorStudno'] = this.$store.state.userInfo.obj.studno;
			}
			console.log('--handleExport-')

			console.log(this.searchObj)
			exportStudentCourse(this.searchObj);
		},
		handleRemove(file, fileList) {
			console.log(file, fileList);
		},
		handlePreview(file) {
			console.log(file);
		},
		handleExceed(files, fileList) {
			this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
		},
		beforeRemove(file, fileList) {
			return this.$confirm(`确定移除 ${file.name}？`);
		},
		handleEdit(columnData, tableDataIndex) {
			this.selectedTableColumn = columnData;
			this.dialogFormVisible = true;
		},
		handleCancle() {
			this.dialogFormVisible = false;
		},
		handleDelete(id) {
			let confirmed = confirm('确认删除');
			if (confirmed === false) {
				return;
			}
			deleteStudentCourse({ id })
				.then(res => {
					this.refreshTableData();
				})
				.catch(err => {
					alert('删除失败,为防止误操作,请先将组清空后删除');
					console.log(err);
				});
		},
		handleAdd() {
			this.dialogFormVisible = true;
		},
		handleConfirm() {
			let confirmed = confirm('确定保存');
			if (confirmed === false) {
				return;
			}
			let data = {};
			let columnData = this.selectedTableColumn;
			data['score'] = columnData.score;
			data['id'] = columnData.id;
			updateScore(data).then(res => {
				if (res.status === 200) {
					alert('修改成功');
					this.dialogFormVisible = false;
				}
			});
		},
		handleSizeChange(pageSize) {
			this.pageSize = pageSize;
			this.handleCurrentChange(this.index);
		},
		handleCurrentChange(index) {
			this.index = index;
			this.refreshTableData();
		},
		clearSearch() {
			this.searchObj = {};
		},
		handleSearch() {
			this.refreshTableData();
		},
		refreshTableData() {
			let data = this.searchObj;
			data['index'] = this.index;
			data['pageSize'] = this.pageSize;
			if (this.type === 1) {
				data['tutorStudno'] = this.$store.state.userInfo.obj.studno;
			}

			findStudentCourseWithPage(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
				if (this.total > 0) {
					this.selectedTableColumn = res.data.content[0];
				}
			});
		}
	},
	created: function() {
		this.type = this.$store.state.userInfo.user.type;
		this.refreshTableData();
	}
};
</script>

<style></style>
