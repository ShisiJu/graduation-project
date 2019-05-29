<template>
	<div>
		<div>
			<el-input v-model="exactName" placeholder="请输入组名称" style="width: 10.25rem;margin-right: 3.125rem;"></el-input>
			<span style="margin: 1.25rem;">学院</span>

			<el-select v-model="instituteIds" multiple placeholder="请选择学院">
				<el-option v-for="item in instituteOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
			</el-select>
			<el-button style="margin-left: 1.5rem;" icon="el-icon-search" circle @click="handleSearch"></el-button>
			<el-button @click="clearSearch" circle><i class="el-icon-delete"></i></el-button>
			<el-button style="margin-left: 12.5rem;" type="success" size="small" @click="handleAdd()">添加组</el-button>
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
import { searchGroups, getAllInstitute, saveGroup, deleteGroup, turnToEleArr, cloneObject } from '@/api/axiosAPI';

export default {
	name: 'j-admin-group-list',
	data() {
		return {
			tableData: [],
			index: 1,
			pageSize: 10,
			total: 10,
			exactName: '',
			dialogFormVisible: false,
			dialogTitle: '',
			selectedInstituteOption: '',
			selectedTableColumn: {},
			selectedTableColumnTemp: {},
			tableDataIndex: 0,
			instituteProps: { label: 'name', value: 'id' },
			instituteOptions: [],
			instituteIds: []
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
			let instituteIds = this.instituteIds;
			let name = this.exactName;
			let pageSize = this.pageSize;
			let index = this.index;
			let data = { instituteIds, name, pageSize, index };
			searchGroups(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		},
		handleSearch() {
			this.refreshTableData();
		},
		clearSearch() {
			this.exactName = '';
			this.instituteIds = [];
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
