<template>
	<div>
		<div style="float: left;">
			<div style="float: left;">
				<el-input v-model="searchObj.exactStudno" placeholder="请输入导师学号" style="width: 10rem;"></el-input>
				<el-select v-model="searchObj.instituteIds" multiple placeholder="请选择学院">
					<el-option v-for="item in instituteOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
				</el-select>
				<el-button icon="el-icon-search" circle @click="handleSearch"></el-button>
				<el-button @click="clearsearchTutors" circle><i class="el-icon-delete"></i></el-button>
				<el-button type="success" size="small" @click="handleAdd()">添加导师</el-button>
			</div>
			<div style="width: 8rem;float: left;margin-top: 0.25rem;height: 5rem;">
				<el-upload
					action="api/poi/import-tutor"
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
			<div style="width: 16rem;float: left;margin-top: 0.25rem;">
				<el-button size="small" @click="handleImportModel()">导入模版下载</el-button>
				<el-button size="small" @click="handleExport()">导出数据</el-button>
			</div>
		</div>

		<el-table :data="tableData">
			<el-table-column prop="studno" label="学号"></el-table-column>
			<el-table-column prop="name" label="姓名"></el-table-column>
			<el-table-column prop="sex" label="性别"></el-table-column>
			<el-table-column prop="title" label="职称"></el-table-column>
			<el-table-column prop="institute.name" label="学院"></el-table-column>
			<el-table-column label="操作" width="260px">
				<template slot-scope="scope">
					<el-button size="small" type="success" plain @click="handleCheck(scope.row)">查看评价</el-button>
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
				<el-form-item label="学号" label-width="6.25rem"><el-input v-model="selectedTableColumn.studno"></el-input></el-form-item>
				<el-form-item label="导师姓名" label-width="6.25rem"><el-input v-model="selectedTableColumn.name" auto-complete="off"></el-input></el-form-item>
				<el-form-item label="性别" label-width="6.25rem">
					<el-radio-group v-model="selectedTableColumn.sex">
						<el-radio label="男">男</el-radio>
						<el-radio label="女">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="职称" label-width="6.25rem"><el-input v-model="selectedTableColumn.title"></el-input></el-form-item>
				<el-form-item label="学院" label-width="6.25rem">
					<el-select v-model="selectedInstituteOption" placeholder="请选择">
						<el-option v-for="item in instituteOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="handleCancle">取 消</el-button>
				<el-button type="primary" @click="saveTutor">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { exportTutor, searchTutors, getAllInstitute, saveTutor, deleteTutor, turnToEleArr, cloneObject } from '@/api/axiosAPI';

export default {
	name: 'j-admin-tutor-list',
	data() {
		return {
			tableData: [],
			index: 1,
			pageSize: 10,
			total: 10,
			searchObj: { instituteIds: [] },
			dialogFormVisible: false,
			dialogTitle: '',
			selectedInstituteOption: '',
			selectedTableColumn: {},
			selectedTableColumnTemp: {},
			tableDataIndex: 0,
			instituteProps: { label: 'name', value: 'id' },
			instituteOptions: [],
			fileList: []
		};
	},
	methods: {
		handleImportModel() {
			window.open(window.location.origin + '/api/poi/export-tutor-model');
		},
		handleExport() {
			console.log(this.searchObj);
			exportTutor(this.searchObj);
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
		handleEdit(tutor, tableDataIndex) {
			this.tableDataIndex = tableDataIndex;
			this.selectedTableColumn = tutor;
			this.selectedInstituteOption = tutor.institute.id;
			this.dialogTitle = '修改导师信息';
			this.dialogFormVisible = true;
			this.selectedTableColumnTemp = cloneObject(this.selectedTableColumn);
		},
		handleCancle() {
			this.tableData[this.tableDataIndex] = this.selectedTableColumnTemp;
			this.selectedTableColumnTemp = {};
			this.dialogFormVisible = false;
		},
		handleDelete(tutorId) {
			let confirmed = confirm('确认删除');
			if (confirmed === false) {
				return;
			}
			deleteTutor({ tutorId })
				.then(res => {
					this.refreshTableData();
				})
				.catch(err => {
					alert('删除失败，请先将关联数据清空后删除');
					console.log(err);
				});
		},
		handleAdd() {
			this.dialogFormVisible = true;
			this.selectedTableColumn = {};
			this.dialogTitle = '添加导师信息';
		},
		saveTutor() {
			let confirmed = confirm('确定保存');
			if (confirmed === false) {
				return;
			}

			let savedTutor = { tutor: this.selectedTableColumn, instituteId: this.selectedInstituteOption };
			saveTutor(savedTutor)
				.then(res => {
					this.refreshTableData();
					this.dialogFormVisible = false;
				})
				.catch(err => {
					alert('修改失败');
					console.log(err);
				});
		},
		handleCheck(tutor) {
			this.$store.commit('setTutor', tutor);
			let t = this.$store.state.tutor;
			this.$router.push('/tutor/course-outline');
		},
		refreshTableData() {
			let data = this.searchObj;
			data['index'] = this.index;
			data['pageSize'] = this.pageSize;
			searchTutors(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		},
		handleSearch() {
			this.refreshTableData();
		},
		clearsearchTutors() {
			this.searchObj.exactStudno = '';
			this.searchObj.instituteIds = [];
		}
	},
	created: function() {
		this.refreshTableData();
		getAllInstitute({}).then(res => {
			let eleArr = turnToEleArr(res.data);
			this.instituteOptions = eleArr;
		});
	}
};
</script>
<style></style>
