<template>
	<div>
		<div style="float: left;">
			<div style="float: left;">
				<el-input v-model="searchObj.studno" placeholder="请输入学生学号" style="width: 10.25rem;"></el-input>
				<el-cascader
					placeholder="请选择班级"
					:options="groupOptions"
					:show-all-levels="false"
					v-model="searchObj.group"
					@change="handleSearchGroupChange"
					:props="groupProps"
				></el-cascader>
				<el-button icon="el-icon-search" circle @click="handleSearch"></el-button>
				<el-button @click="clearSearch" circle><i class="el-icon-delete"></i></el-button>
				<el-button type="success" size="small" @click="handleAdd()">添加学生</el-button>
			</div>
			<div style="width: 8rem;float: left;margin-top: 0.25rem;height: 5rem;">
				<el-upload
					action="api/poi/import-student"
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
			<div style="width:16rem;float: left;margin-top: 0.25rem;">
				<el-button size="small" @click="handleImportModel()">导入模版下载</el-button>
				<el-button size="small" @click="handleExport()">导出数据</el-button>
			</div>
		</div>

		<el-table :data="tableData">
			<el-table-column prop="studno" label="学号"></el-table-column>
			<el-table-column prop="name" label="姓名"></el-table-column>
			<el-table-column prop="sex" label="性别"></el-table-column>
			<el-table-column prop="group.academicYear" label="学年"></el-table-column>
			<el-table-column prop="group.institute.name" label="学院"></el-table-column>
			<el-table-column prop="group.name" label="组"></el-table-column>
			<el-table-column label="操作" width="160">
				<template slot-scope="scope">
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
			<el-form :model="selectTable">
				<el-form-item label="学号" label-width="6.25rem"><el-input v-model="selectTable.studno"></el-input></el-form-item>
				<el-form-item label="学生姓名" label-width="6.25rem"><el-input v-model="selectTable.name"></el-input></el-form-item>
				<el-form-item label="性别" label-width="6.25rem">
					<el-radio-group v-model="selectTable.sex">
						<el-radio label="男">男</el-radio>
						<el-radio label="女">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="组" label-width="6.25rem">
					<el-cascader :options="groupOptions" :show-all-levels="false" v-model="selectedOptions" @change="handleGroupChange" :props="groupProps"></el-cascader>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="handleCancle">取 消</el-button>
				<el-button type="primary" @click="updateOneStudent">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import {
	exportStudent,
	searchStudents,
	insertStudent,
	updateStudent,
	deleteStudent,
	getAllInstitute,
	getGroupByInstituteId,
	getAllGroup,
	getGroupByInstituteIdIn
} from '@/api/axiosAPI';

export default {
	name: 'j-admin-studen-list',
	data() {
		return {
			tableData: [],
			index: 1,
			pageSize: 10,
			total: 10,
			dialogFormVisible: false,
			dialogTitle: '',
			searchObj: { group: [] },
			selectedOptions: [],
			selectTable: {},
			selectTableTemp: {},
			tableDataIndex: 0,
			selectedGroupId: null,
			groupProps: { label: 'name', value: 'id', children: 'groups' },
			groupOptions: [],
			fileList: []
		};
	},
	methods: {
		handleImportModel() {
			window.open(window.location.origin + '/api/poi/export-student-model');
		},
		handleExport() {
			console.log(this.searchObj);
			exportStudent(this.searchObj);
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
			this.refreshStudents();
		},
		handleEdit(student, tableDataIndex) {
			this.dialogTitle = '修改学生信息';
			this.tableDataIndex = tableDataIndex;
			this.dialogFormVisible = true;
			this.selectTable = student;
			this.selectTableTemp = this.cloneObject(this.selectTable);
			let groupId = student.group.id;
			this.selectedGroupId = groupId;
			let instituteId = student.group.institute.id;
			this.selectedOptions = [instituteId, groupId];
		},
		handleCancle() {
			this.tableData[this.tableDataIndex] = this.selectTableTemp;
			this.selectTableTemp = {};
			this.dialogFormVisible = false;
		},
		handleDelete(studenId) {
			let confirmed = confirm('确认删除');
			if (confirmed === false) {
				return;
			}
			deleteStudent({ id: studenId })
				.then(res => {
					this.refreshStudents();
				})
				.catch(err => {
					alert('删除失败，请先将关联数据清空后删除');
					console.log(err);
				});
		},
		handleAdd() {
			this.selectedGroupId = null;
			this.dialogFormVisible = true;
			this.selectTable = {};
			this.dialogTitle = '添加学生信息';
		},
		updateOneStudent() {
			let confirmed = confirm('确定保存');
			if (confirmed === false) {
				return;
			}
			let studentWithGroup = { student: this.selectTable, groupId: this.selectedGroupId };
			updateStudent(studentWithGroup)
				.then(res => {
					this.refreshStudents();
					this.dialogFormVisible = false;
				})
				.catch(err => {
					alert('修改失败');
					console.log(err);
				});
		},
		handleGroupChange(val) {
			this.selectedGroupId = val[1];
		},
		cloneObject(obj) {
			let objStr = JSON.stringify(obj);
			return JSON.parse(objStr);
		},
		refreshStudents() {
			let data = this.searchObj;
			data['index'] = this.index;
			data['pageSize'] = this.pageSize;

			searchStudents(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		},
		handleSearch() {
			this.refreshStudents();
		},
		clearSearch() {
			this.searchObj = { group: [] };
		},
		handleSearchGroupChange(val) {
			this.searchObj.group = val;
			this.searchObj.groupId = val[1];
		}
	},
	created: function() {
		this.refreshStudents();
		getAllInstitute({})
			.then(res => {
				this.groupOptions = res.data;
				let listId = [];
				this.groupOptions.forEach(opt => {
					opt['groups'] = [];
					listId.push(opt.id);
				});
				return getGroupByInstituteIdIn(listId);
			})
			.then(res => {
				let groups = res.data;
				groups.forEach(g => {
					this.groupOptions.forEach(opt => {
						if (g.institute.id === opt.id) {
							opt.groups.push(g);
						}
					});
				});
			});
	}
};
</script>
<style></style>
