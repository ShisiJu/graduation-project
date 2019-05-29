<template>
	<div>
		<div><el-button style="margin-left: 12.5rem;" type="success" size="small" @click="handleAdd()">添加学院</el-button></div>

		<el-table :data="tableData">
			<el-table-column prop="code" label="编码"></el-table-column>
			<el-table-column prop="name" label="名称"></el-table-column>
			<el-table-column label="操作">
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
				<el-form-item label="学院名称" label-width="6.25rem"><el-input v-model="selectedTableColumn.name" auto-complete="off"></el-input></el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="handleCancle">取 消</el-button>
				<el-button type="primary" @click="saveColumnData">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { pageInstitues, saveInstitue, deleteInstitue, turnToEleArr, cloneObject } from '@/api/axiosAPI';

export default {
	name: 'j-admin-institute-list',
	data() {
		return {
			tableData: [],
			index: 1,
			pageSize: 10,
			total: 10,
			exactStudno: '',
			dialogFormVisible: false,
			dialogTitle: '',
			selectedInstituteOption: '',
			selectedTableColumn: {},
			selectedTableColumnTemp: {},
			tableDataIndex: 0
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
		handleEdit(column, tableDataIndex) {
			this.tableDataIndex = tableDataIndex;
			this.selectedTableColumn = column;
			this.dialogTitle = '修改学院信息';
			this.dialogFormVisible = true;
			this.selectedTableColumnTemp = cloneObject(this.selectedTableColumn);
		},
		handleCancle() {
			this.tableData[this.tableDataIndex] = this.selectedTableColumnTemp;
			this.selectedTableColumnTemp = {};
			this.dialogFormVisible = false;
		},
		handleDelete(columnId) {
			let confirmed = confirm('确认删除');
			if (confirmed === false) {
				return;
			}
			deleteInstitue({ instituteId: columnId })
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
			this.dialogTitle = '添加学院信息';
		},
		saveColumnData() {
			let confirmed = confirm('确定保存');
			if (confirmed === false) {
				return;
			}
			let savedData = this.selectedTableColumn;
			saveInstitue(savedData)
				.then(res => {
					this.refreshTableData();
					this.dialogFormVisible = false;
				})
				.catch(err => {
					alert('修改失败');
					console.log(err);
				});
		},

		refreshTableData(pageInfo) {
			let pageSize = this.pageSize;
			let index = this.index;
			let data = null;
			data = { pageSize, index };

			pageInstitues(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		}
	},
	created: function() {
		this.refreshTableData();
	}
};
</script>
<style></style>
