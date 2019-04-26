<template>
	<div>
		<el-table :data="tableData">
			<el-table-column prop="studno" label="学号"></el-table-column>
			<el-table-column prop="name" label="姓名"></el-table-column>
			<el-table-column prop="sex" label="性别"></el-table-column>
			<el-table-column prop="group.academic_year" label="学年"></el-table-column>
			<el-table-column prop="group.institute.name" label="学院"></el-table-column>
			<el-table-column prop="group.name" label="组"></el-table-column>
			<el-table-column label="操作" width="160">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
					<el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
						删除
					</el-button>
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

		<el-dialog title="修改学生信息" :visible.sync="dialogFormVisible">
			<el-form :model="selectTable">
				<el-form-item label="学号" label-width="100px">
					<el-input v-model="selectTable.studno"></el-input>
				</el-form-item>
				<el-form-item label="学生姓名" label-width="100px">
					<el-input v-model="selectTable.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别" label-width="100px">
					<el-radio-group v-model="selectTable.sex">
						<el-radio label="男">男</el-radio>
						<el-radio label="女">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="组" label-width="100px">
					<el-cascader
						:options="groupOptions"
						:show-all-levels="false"
						@change="handleGroupChange"
					></el-cascader>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="updateOneStudent">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { findStudenPage, countStudents, insertStudent, updateStudent } from '@/api/axiosAPI';

export default {
	name: 'j-admin-studen-list',
	data() {
		return {
			tableData: [],
			index: 1,
			pageSize: 10,
			total: 10,
			dialogFormVisible: false,
			selectTable: {},
			groupId: 1,
			groupOptions: [{ label: '信息学院', value: '1' }]
		};
	},
	methods: {
		updateTableData(index, pageSize) {
			findStudenPage({ pageSize, index }).then(res => {
				this.tableData = res.data.content;
			});
		},
		handleSizeChange(pageSize) {
			this.pageSize = pageSize;
			this.handleCurrentChange(this.index);
		},
		handleCurrentChange(index) {
			this.index = index;
			this.updateTableData(index, this.pageSize);
		},
		handleEdit(student) {
			this.dialogFormVisible = true;
			this.selectTable = student;
			console.log(this.dialogFormVisible);
		},
		handleDelete(studenId) {},
		updateOneStudent() {},
		handleGroupChange(val) {
			console.log('handleGroupChange '+val)
		}
	},
	created: function() {
		let pageSize = this.pageSize;
		let index = this.index;

		countStudents({})
			.then(res => {
				this.total = res.data;
				return findStudenPage({ pageSize, index });
			})
			.then(res => {
				this.tableData = res.data.content;
			});
	}
};
</script>
<style></style>
