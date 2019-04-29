<template>
	<div>
		<div>
			<el-input v-model="exactStudno" placeholder="请输入导师学号" style="width: 10.25rem;margin-right: 3.125rem;"></el-input>
			<span style="margin: 1.25rem;">学院</span>

			<el-select v-model="instituteIds" multiple placeholder="请选择学院">
				<el-option v-for="item in instituteOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
			</el-select>
			<el-button style="margin-left: 1.5rem;" icon="el-icon-search" circle @click="handleSearch"></el-button>
			<el-button @click="clearsearchTutors" circle><i class="el-icon-delete"></i></el-button>
			<el-button style="margin-left: 12.5rem;" type="success" size="small" @click="handleAdd()">添加导师</el-button>
		</div>

		<el-table :data="tableData">
			<el-table-column prop="studno" label="学号"></el-table-column>
			<el-table-column prop="name" label="姓名"></el-table-column>
			<el-table-column prop="sex" label="性别"></el-table-column>
			<el-table-column prop="title" label="职称"></el-table-column>
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
				<el-form-item label="学号" label-width="100px"><el-input v-model="selectedTableColumn.studno"></el-input></el-form-item>
				<el-form-item label="导师姓名" label-width="100px"><el-input v-model="selectedTableColumn.name" auto-complete="off"></el-input></el-form-item>
				<el-form-item label="性别" label-width="100px">
					<el-radio-group v-model="selectedTableColumn.sex">
						<el-radio label="男">男</el-radio>
						<el-radio label="女">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="职称" label-width="100px"><el-input v-model="selectedTableColumn.title"></el-input></el-form-item>
				<el-form-item label="学院" label-width="100px">
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
import { searchTutors, getAllInstitute, saveTutor, deleteTutor, turnToEleArr, cloneObject } from '@/api/axiosAPI';

export default {
	name: 'j-admin-tutor-list',
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
					alert('删除失败');
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

		refreshTableData() {
			let instituteIds = this.instituteIds;
			let studno = this.exactStudno;
			let pageSize = this.pageSize;
			let index = this.index;
			let data = { instituteIds, studno, pageSize, index };
			searchTutors(data).then(res => {
				console.log(res.data);
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		},
		handleSearch() {
			this.refreshTableData();
		},
		clearsearchTutors() {
			this.exactStudno = '';
			this.instituteIds = [];
		}
	},
	created: function() {
		this.refreshTableData();
		getAllInstitute({}).then(res => {
			let eleArr = turnToEleArr(res.data);
			this.instituteOptions = eleArr;
			console.log('-------create');
			console.log(this.instituteOptions);
		});
	}
};
</script>
<style></style>
