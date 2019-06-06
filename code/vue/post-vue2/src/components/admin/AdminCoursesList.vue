<template>
	<div>
		<div>
			<div style="float: left;">
				<el-input v-model="searchObj.name" placeholder="课程名称" style="width: 9rem;"></el-input>
				<el-input v-model="searchObj.academicYear" placeholder="学年" style="width: 9rem"></el-input>
				<el-select v-model="searchObj.term" placeholder="学期" style="width: 9rem" clearable>
					<el-option v-for="item in termOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
				</el-select>
				<el-select v-model="searchObj.tutorId" placeholder="请选择导师" filterable>
					<el-option v-for="item in tutorOptions" :key="item.value" :label="item.label" :value="item.value">
						<span style="float: left">{{ item.label }}</span>
						<span style="float: right; color: #8492a6; font-size: 13px">{{ item.studno }}</span>
					</el-option>
				</el-select>
			</div>
			<el-button icon="el-icon-search" circle @click="handleSearch"></el-button>
			<el-button @click="clearSearch" circle><i class="el-icon-delete"></i></el-button>
			<el-button type="success" size="small" @click="handleAdd()">添加课程</el-button>

			<div>
				<div style="width: 8rem;float: left;margin-top: 0.25rem;height: 4rem;">
					<el-upload
						action="api/poi/import-course"
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

				<div style="width:16rem;float: left;margin-top: 0.25rem;height: 4rem;">
					<el-button size="small" @click="handleImportModel()">导入模版下载</el-button>
					<el-button size="small" @click="handleExport()">导出数据</el-button>
				</div>
			</div>
		</div>

		<el-table :data="tableData">
			<el-table-column prop="academicYear" label="学年"></el-table-column>
			<el-table-column prop="term" label="学期"></el-table-column>
			<el-table-column prop="name" label="名称"></el-table-column>
			<el-table-column prop="code" label="编码"></el-table-column>
			<el-table-column prop="courseType" label="课程类型"></el-table-column>
			<el-table-column prop="tutor.name" label="导师"></el-table-column>
			<el-table-column label="操作" width="260px">
				<template slot-scope="scope">
					<el-button size="small" type="success" @click="handleCheck(scope.row)" plain>查看评价</el-button>
					<el-button size="small" @click="handleEdit(scope.row, scope.$index)">编辑</el-button>
					<el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
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

		<el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
			<el-form :model="selectedTableColumn">
				<el-form-item label="编码" label-width="6.25rem"><el-input v-model="selectedTableColumn.code"></el-input></el-form-item>
				<el-form-item label="名称" label-width="6.25rem"><el-input v-model="selectedTableColumn.name" auto-complete="off"></el-input></el-form-item>
				<el-form-item label="学年" label-width="6.25rem"><el-input v-model="selectedTableColumn.academicYear"></el-input></el-form-item>

				<el-form-item label="学期" label-width="6.25rem">
					<el-select v-model="selectedTableColumn.term" placeholder="学期" style="width: 9rem" clearable>
						<el-option v-for="item in termOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="课程类型" label-width="6.25rem">
					<el-select v-model="selectedTableColumn.courseType" placeholder="课程类型" style="width: 9rem" clearable>
						<el-option v-for="item in courseOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="课程人数" label-width="6.25rem"><el-input v-model="selectedTableColumn.amount"></el-input></el-form-item>

				<el-form-item label="导师" label-width="6.25rem">
					<el-select v-model="selectedTutorId" placeholder="请选择" filterable>
						<el-option v-for="item in tutorOptions" :key="item.value" :label="item.label" :value="item.value">
							<span style="float: left">{{ item.label }}</span>
							<span style="float: right; color: #8492a6; font-size: 0.8125rem">{{ item.studno }}</span>
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="组" label-width="6.25rem">
					<el-select v-model="selectedGroupIds" multiple filterable placeholder="请选择">
						<el-option v-for="item in groupOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="handleCancle">取 消</el-button>
				<el-button type="primary" @click="saveColumnData">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { exportCourse, searchCourses, getAllInstitute, saveCourse, getAllTutor, deleteCourse, getGroupsByCourseId, getAllGroup, turnToEleArr, cloneObject } from '@/api/axiosAPI';

export default {
	name: 'j-admin-group-list',
	data() {
		return {
			tableData: [],
			index: 1,
			pageSize: 10,
			total: 10,
			searchObj: {},
			termOptions: [{ value: '秋季' }, { value: '春季' }],
			courseOptions: [{ value: '必修' }, { value: '选修' }],
			dialogFormVisible: false,
			dialogTitle: '',
			edited: false,
			selectedTableColumn: {},
			selectedTutorId: '',
			selectedGroupIds: [],
			selectedTableColumnTemp: {},
			tableDataIndex: 0,
			instituteProps: { label: 'name', value: 'id' },
			groupOptions: [],
			instituteOptions: [],
			tutorOptions: [{ label: 'Stan', value: '5', studno: 'Mar' }],
			fileList: []
		};
	},
	methods: {
		handleImportModel() {
			window.open(window.location.origin + '/api/poi/export-course-model');
		},
		handleExport() {
			console.log(this.searchObj);
			exportCourse(this.searchObj);
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
		handleSizeChange(pageSize) {
			this.pageSize = pageSize;
			this.handleCurrentChange(this.index);
		},
		handleCurrentChange(index) {
			this.index = index;
			this.refreshTableData();
		},
		handleEdit(columnData, tableDataIndex) {
			this.tableDataIndex = tableDataIndex;
			this.selectedTableColumn = columnData;
			// 需要先查出原来的组信息 放入
			this.searchTutorOptions(columnData.tutor.id);
			this.searchGroupOptions(columnData.id);
			this.dialogTitle = '修改课程信息';
			this.dialogFormVisible = true;
			this.selectedTableColumnTemp = cloneObject(this.selectedTableColumn);
		},
		handleCancle() {
			this.tableData[this.tableDataIndex] = this.selectedTableColumnTemp;
			this.selectedTableColumnTemp = { groupIds: [] };
			this.dialogFormVisible = false;
		},
		handleDelete(courseId) {
			let confirmed = confirm('确认删除');
			if (confirmed === false) {
				return;
			}
			deleteCourse({ courseId })
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
			this.selectedTableColumn = {};
			this.dialogTitle = '添加课程信息';
			this.selectedGroupIds = [];
			this.selectedTutorId = '';
		},
		saveColumnData() {
			let savedData = { tutorId: this.selectedTutorId, groupIds: this.selectedGroupIds, course: this.selectedTableColumn };
			console.log(savedData);
			let confirmed = confirm('确定保存');
			if (confirmed === false) {
				return;
			}

			saveCourse(savedData)
				.then(res => {
					this.refreshTableData();
					this.dialogFormVisible = false;
				})
				.catch(err => {
					alert('修改失败');
					console.log(err);
				});
		},
		handleCheck(rowData) {
			this.$store.commit('setCourse', rowData);
			this.$router.push('/tutor/course-detail');
		},
		refreshTableData() {
			let data = this.searchObj;
			data['index'] = this.index;
			data['pageSize'] = this.pageSize;
			searchCourses(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		},
		handleSearch() {
			this.refreshTableData();
		},
		clearSearch() {
			this.searchObj = { instituteIds: [] };
		},
		searchTutorOptions(id) {
			this.selectedTutorId = id;
			if (this.edited == true) return;
			this.getAllTutorInfo();
			this.selectedTutorId = id;
		},
		getAllTutorInfo() {
			getAllTutor({}).then(res => {
				let data = res.data;
				let eleData = turnToEleArr(data, 'studno');
				this.tutorOptions = eleData;
			});
		},
		searchGroupOptions(courseId) {
			getGroupsByCourseId({ courseId }).then(res => {
				this.selectedGroupIds = res.data;
			});
			if (this.edited == true) return;
		}
	},
	created: function() {
		this.refreshTableData();
		getAllInstitute({}).then(res => {
			let eleArr = turnToEleArr(res.data);
			this.instituteOptions = eleArr;
		});

		this.getAllTutorInfo();

		getAllGroup({}).then(res => {
			let data = res.data;
			let eleData = turnToEleArr(data);
			this.groupOptions = eleData;
		});
	}
};
</script>
<style></style>
