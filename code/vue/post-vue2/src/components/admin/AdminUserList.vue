<template>
	<div>
		<div>
			<el-input v-model="exactStudno" placeholder="请输入学号" style="width: 10.25rem;margin-right: 3.125rem;"></el-input>
			<span style="margin: 1.25rem;">用户类型</span>

			<el-select v-model="userType" placeholder="请选择用户类型">
				<el-option v-for="item in userOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
			</el-select>
			<el-button style="margin-left: 1.5rem;" icon="el-icon-search" circle @click="handleSearch"></el-button>
			<el-button @click="clearsearchTutors" circle><i class="el-icon-delete"></i></el-button>
		</div>

		<el-table :data="tableData">
			<el-table-column prop="username" label="用户名"></el-table-column>
			<el-table-column prop="studno" label="学号"></el-table-column>
			<el-table-column prop="type" label="类型">
				<template slot-scope="scope">
					{{ getTypeStr(scope.row.type) }}
				</template>
			</el-table-column>
			<el-table-column label="操作" width="160">
				<template slot-scope="scope">
					<el-button size="small" @click="resetPassword(scope.row.id, scope.$index)">重置密码</el-button>
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
	</div>
</template>

<script>
import { searchUser, deleteUser, updatePwd, resetPwd, turnToEleArr, cloneObject } from '@/api/axiosAPI';

export default {
	name: 'j-admin-admin-list',
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
			userOptions: [{ value: 0, label: '学生' }, { value: 1, label: '导师' }, { value: 2, label: '管理员' }],
			userType: ''
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
		resetPassword(userId) {
			resetPwd({});
		},
		refreshTableData() {
			let userType = this.userType;
			let studno = this.exactStudno;
			let pageSize = this.pageSize;
			let index = this.index;
			let data = { type:userType, studno, pageSize, index };
			searchUser(data).then(res => {
				this.tableData = res.data.content;
				this.total = res.data.totalElements;
			});
		},
		handleSearch() {
			this.refreshTableData();
		},
		clearsearchTutors() {
			this.exactStudno = '';
			this.userType = '';
		},
		getTypeStr(type) {
			if (type === 0) {
				return '学生';
			} else if (type === 1) {
				return '导师';
			} else if (type === 2) {
				return '管理员';
			}
		}
	},
	created: function() {
		this.refreshTableData();
	},
	computed: {}
};
</script>
<style></style>
