<template>
	<div>
		<div>
			<el-input v-model="searchObj.name" placeholder="请输入组名称" style="width: 9rem;"></el-input>
			<el-select v-model="searchObj.instituteIds" multiple placeholder="请选择学院">
				<el-option v-for="item in instituteOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
			</el-select>
			<el-button icon="el-icon-search" circle @click="handleSearch"></el-button>
			<el-button @click="clearSearch" circle><i class="el-icon-delete"></i></el-button>
			<el-button type="success" size="small" @click="handleAdd()">添加组</el-button>
			<div style="width: 14rem;float: right;margin-top: 0.25rem;height: 4rem;">
				<el-button size="small" @click="handleImportModel()">导入模版下载</el-button>
				<el-button size="small" @click="handleExport()">导出数据</el-button>
			</div>
			<div style="width: 8rem;float: right;margin-top: 0.25rem;height: 4rem;">
				<el-upload
					action="api/poi/import-group"
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
		</div>

		<el-table :data="tableData">
			<el-table-column prop="academicYear" label="学年"></el-table-column>
			<el-table-column prop="name" label="名称"></el-table-column>
			<el-table-column prop="institute.name" label="学院"></el-table-column>
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
			<el-form :model="selectedTableColumn">
				<el-form-item label="编码" label-width="6.25rem"><el-input v-model="selectedTableColumn.code"></el-input></el-form-item>
				<el-form-item label="名称" label-width="6.25rem"><el-input v-model="selectedTableColumn.name" auto-complete="off"></el-input></el-form-item>
				<el-form-item label="学年" label-width="6.25rem"><el-input v-model="selectedTableColumn.academicYear"></el-input></el-form-item>
				<el-form-item label="学院" label-width="6.25rem">
					<el-select v-model="selectedInstituteOption" placeholder="请选择">
						<el-option v-for="item in instituteOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
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
import { exportGroup, searchGroups, getAllInstitute, saveGroup, deleteGroup, turnToEleArr, cloneObject } from '@/api/axiosAPI';

export default {
	name: 'j-admin-group-list',
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
			window.open(window.location.origin + '/api/poi/export-group');
		},
		handleExport() {
			exportGroup(this.searchObj);
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
		handleEdit(group, tableDataIndex) {
			this.tableDataIndex = tableDataIndex;
			this.selectedTableColumn = group;
			this.selectedInstituteOption = group.institute.id;
			this.dialogTitle = '修改组信息';
			this.dialogFormVisible = true;
			this.selectedTableColumnTemp = cloneObject(this.selectedTableColumn);
		},
		handleCancle() {
			this.tableData[this.tableDataIndex] = this.selectedTableColumnTemp;
			this.selectedTableColumnTemp = {};
			this.dialogFormVisible = false;
		},
		handleDelete(groupId) {
			let confirmed = confirm('确认删除');
			if (confirmed === false) {
				return;
			}
			deleteGroup({ groupId })
				.then(res => {
					this.refreshTableData();
				})
				.catch(err => {
					alert('删除失败');
					console.log(err);
				});
		},
		handleAdd() {
			this.dialogFormVisible = true;
			this.selectedTableColumn = {};
			this.dialogTitle = '添加组信息';
		},
		saveColumnData() {
			let confirmed = confirm('确定保存');
			if (confirmed === false) {
				return;
			}

			let savedData = { group: this.selectedTableColumn, instituteId: this.selectedInstituteOption };
			saveGroup(savedData)
				.then(res => {
					this.refreshTableData();
					this.dialogFormVisible = false;
				})
				.catch(err => {
					alert('修改失败');
					console.log(err);
				});
		},
		refreshTableData() {
			let data = this.searchObj;
			data['index'] = this.index;
			data['pageSize'] = this.pageSize;
			searchGroups(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		},
		handleSearch() {
			this.refreshTableData();
		},
		clearSearch() {
			this.searchObj.name = '';
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
