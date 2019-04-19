<template>
	<div class="center-in-center">
		<h2>研究生导师评价系统</h2>
		<br />
		<el-form ref="form" :model="form" label-width="80px" :rules="formRules">
			<el-form-item label="账号" prop="username">
				<el-input v-model.number="form.username" clearable></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="pwd">
				<el-input type="password" v-model="form.pwd" clearable></el-input>
			</el-form-item>

			<el-form-item label="用户类型" prop="type">
				<el-radio-group v-model.number="form.type">
					<el-radio-button label="0">学生</el-radio-button>
					<el-radio-button label="1">导师</el-radio-button>
					<el-radio-button label="2">管理员</el-radio-button>
				</el-radio-group>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" @click="submitForm('form')" class="submit_btn">
					登录
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
export default {
	data() {
		return {
			form: {
				username: '',
				pwd: '',
				type: 0
			},
			formRules: {
				username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
				pwd: [{ required: true, message: '请输入密码', trigger: 'change' }],
				type: [{ required: true, message: '请选择用户类型', trigger: 'change' }]
			}
		};
	},
	methods: {
		submitForm(formName) {
			this.$refs[formName].validate(valid => {
				if (valid) {
					
				} else {
					console.log('error submit!!');
					return false;
				}
			});
			this.axios
				.post('login/login', this.$qs.stringify(this.form), {
					headers: { 'content-type': 'application/x-www-form-urlencoded' }
				})
				.then(res => {
					console.log(res);
					//设置store里的type和username
					this.$router.push('/common');
				});
			
		}
	}
};
</script>
<style>
* {
	margin: 0;
	padding: 0;
}

.center-in-center {
	position: absolute;
	top: 50%;
	left: 50%;
	-webkit-transform: translate(-50%, -50%);
	-moz-transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	-o-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
}

.submit_btn {
	width: 100%;
	font-size: 16px;
}
</style>
