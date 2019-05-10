<template>
	<div>
		<div>
			<el-input v-model="exactStudno" placeholder="请输入学生学号" style="width: 10.25rem;margin-right: 3.125rem;"></el-input>
			<span style="margin: 1.25rem;">专业</span>
			<el-cascader :options="groupOptions" :show-all-levels="false" v-model="selectedSearchOptions" @change="handleSearchGroupChange" :props="groupProps"></el-cascader>
			<el-button style="margin-left: 1.5rem;" icon="el-icon-search" circle @click="handleSearch"></el-button>
			<el-button @click="clearSearchStudents" circle><i class="el-icon-delete"></i></el-button>
			<el-button style="margin-left: 12.5rem;" type="success" size="small" @click="handleAdd()">添加学生</el-button>
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
				<el-form-item label="学号" label-width="100px"><el-input v-model="selectTable.studno"></el-input></el-form-item>
				<el-form-item label="学生姓名" label-width="100px"><el-input v-model="selectTable.name"></el-input></el-form-item>
				<el-form-item label="性别" label-width="100px">
					<el-radio-group v-model="selectTable.sex">
						<el-radio label="男">男</el-radio>
						<el-radio label="女">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="组" label-width="100px">
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
import { searchStudents, insertStudent, updateStudent, deleteStudent, getAllInstitute, getGroupByInstituteId, getAllGroup, getGroupByInstituteIdIn } from '@/api/axiosAPI';

export default {
	name: 'j-admin-studen-list',
	data() {
		return {
			tableData: [],
			index: 1,
			pageSize: 10,
			total: 10,
			exactStudno: '',
			dialogFormVisible: false,
			dialogTitle: '',
			selectedOptions: [],
			selectedSearchOptions: [],
			selectTable: {},
			selectTableTemp: {},
			tableDataIndex: 0,
			groupId: null,
			selectedGroupId: null,
			groupProps: { label: 'name', value: 'id', children: 'groups' },
			groupOptions: []
		};
	},
	methods: {
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
					alert('删除失败');
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
			let groupId = this.groupId;
			let studno = this.exactStudno;
			let pageSize = this.pageSize;
			let index = this.index;
			let data = { groupId, studno, pageSize, index };
			
			searchStudents(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		},
		handleSearch() {
			this.refreshStudents();
		},
		clearSearchStudents() {
			this.selectedSearchOptions = [];
			this.exactStudno = '';
			this.groupId = null;
		},
		handleSearchGroupChange(val) {
			this.groupId = val[1];
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
