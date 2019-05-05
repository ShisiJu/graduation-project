<template>
	<div>
		<el-table :data="userTableData">
			<el-table-column prop="studno" label="学号"></el-table-column>
			<el-table-column prop="username" label="用户名"></el-table-column>
			<el-table-column label="操作" width="260px">
				<template slot-scope="scope">
					<el-button size="small" @click="updatePassword()">修改密码</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
			<el-form :model="pwdForm" :rules="rules" ref="pwdForm">
				<el-form-item label="旧密码" label-width="100px"><el-input type="password" v-model="pwdForm.oldPwd" show-password></el-input></el-form-item>
				<el-form-item label="新密码" label-width="100px" prop="pass"><el-input type="password" v-model="pwdForm.pass" show-password></el-input></el-form-item>
				<el-form-item label="确认新密码" label-width="100px" prop="checkPass"><el-input type="password" v-model="pwdForm.checkPass" show-password></el-input></el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="handleCancle">取 消</el-button>
				<el-button type="primary" @click="handleOk">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { updatePwd } from '@/api/axiosAPI';

export default {
	name: 'j-update-pwd',
	data() {
		let validatePass = (rule, value, callback) => {
			if (value === '') {
				callback(new Error('请输入密码'));
			} else {
				if (this.pwdForm.checkPass !== '') {
					this.$refs.pwdForm.validateField('checkPass');
				}
				callback();
			}
		};
		let validatePass2 = (rule, value, callback) => {
			if (value === '') {
				callback(new Error('请再次输入密码'));
			} else if (value !== this.pwdForm.pass) {
				callback(new Error('两次输入密码不一致!'));
			} else {
				callback();
			}
		};
		return {
			userTableData: [],
			dialogFormVisible: false,
			dialogTitle: '',
			pwdForm: { pass: '', passCheck: '' },
			rules: {
				pass: [{ validator: validatePass, trigger: 'blur' }],
				checkPass: [{ validator: validatePass2, trigger: 'blur' }]
			},
			userId: ''
		};
	},
	methods: {
		updatePassword() {
			this.dialogFormVisible = true;
		},
		handleOk() {
			this.$refs['pwdForm'].validate(valid => {
				if (valid) {
					console.log('提交');
					let data = { userId: this.userId, pwd: this.pwdForm.oldPwd, newPwd: this.pwdForm.checkPass };
					updatePwd(data).then(res => {
						let succeed = res.data;
						if (succeed === false) {
							alert('修改失败,旧密码错误');
						} else {
							this.dialogFormVisible = false;
						}
						this.pwdForm = { pass: '', passCheck: '' };
					});
				} else {
					alert('密码不一致');
					return false;
				}
			});
		},
		handleCancle() {
			this.dialogFormVisible = false;
		}
	},
	created: function() {
		let userTableData = this.$store.state.userInfo.user;
		this.userId = this.$store.state.userInfo.user.id;

		this.userTableData.push(userTableData);
	},
	computed: {
		samePwd() {
			if (pwdForm.pwd === '') return false;
		}
	}
};
</script>

<style></style>
