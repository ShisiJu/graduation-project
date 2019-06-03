<template>
	<div>
		<el-container>
			<el-aside width="15%">
				<el-menu>
					<el-submenu index="1" name="student" v-if="userInfo.type === 0">
						<template slot="title">
							<div class="jsstitle">
								<i class="el-icon-message"></i>
								学生信息
							</div>
						</template>
						<el-menu-item-group>
							<router-link :to="'/student/course'"><el-menu-item index="1-1">我的课程</el-menu-item></router-link>
							<router-link :to="'/student/info'"><el-menu-item index="1-2">个人信息</el-menu-item></router-link>
							<router-link :to="'/student/choose-course'"><el-menu-item index="1-3">选择课程</el-menu-item></router-link>
						</el-menu-item-group>
					</el-submenu>

					<el-submenu index="2" name="tutor" v-if="userInfo.type === 1">
						<template slot="title">
							<div class="jsstitle">
								<i class="el-icon-menu"></i>
								导师信息
							</div>
						</template>
						<el-menu-item-group>
							<router-link :to="'/tutor/course'"><el-menu-item index="2-1">我的课程</el-menu-item></router-link>
							<router-link :to="'/tutor/info'"><el-menu-item index="2-2">个人信息</el-menu-item></router-link>
							<router-link :to="'/tutor/student-course'"><el-menu-item index="2-3">成绩信息</el-menu-item></router-link>
						</el-menu-item-group>
					</el-submenu>

					<el-submenu index="3" name="admin" v-if="userInfo.type === 2">
						<template slot="title">
							<i class="el-icon-setting"></i>
							管理员
						</template>

						<el-submenu index="3-1">
							<template slot="title">
								<div class="jsstitle">基本信息</div>
							</template>
							<router-link :to="'/admin/students'"><el-menu-item index="3-1-1">学生信息</el-menu-item></router-link>
							<router-link :to="'/admin/tutors'"><el-menu-item index="3-1-2">导师信息</el-menu-item></router-link>
							<router-link :to="'/admin/users'"><el-menu-item index="3-1-3">用户信息</el-menu-item></router-link>
						</el-submenu>

						<el-submenu index="3-2">
							<template slot="title">
								<div class="jsstitle">结构信息</div>
							</template>
							<router-link :to="'/admin/institutes'"><el-menu-item index="3-2-1">学院信息</el-menu-item></router-link>
							<router-link :to="'/admin/groups'"><el-menu-item index="3-2-2">组信息</el-menu-item></router-link>
						</el-submenu>

						<el-submenu index="3-3">
							<template slot="title">
								<div class="jsstitle">课程信息</div>
							</template>
							<router-link :to="'/admin/courses'"><el-menu-item index="3-3-1">课程信息</el-menu-item></router-link>
							<router-link :to="'/admin/statistics'"><el-menu-item index="3-3-2">统计信息</el-menu-item></router-link>
							<router-link :to="'/admin/student-course'"><el-menu-item index="3-3-3">学生课程</el-menu-item></router-link>
						</el-submenu>
					</el-submenu>
				</el-menu>
			</el-aside>

			<el-container>
				<el-header style="text-align: right; font-size: 1.125rem">
					<el-button @click="goBack" plain style="float:left" icon="el-icon-back">后退</el-button>
					<el-dropdown>
						<i class="el-icon-setting" style="margin-right: 0.9375rem;font-size: large;"></i>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item @click.native.prevent="goInfo()">个人信息</el-dropdown-item>
							<el-dropdown-item @click.native.prevent="logout()">登出</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
					<span>{{ objInfo.name }}</span>
				</el-header>

				<el-main><router-view></router-view></el-main>
			</el-container>
		</el-container>
	</div>
</template>

<script>
export default {
	name: 'j-aside',
	data() {
		// 可以把 type 传到子组件  session里?
		return {
			userInfo: this.$store.state.userInfo.user,
			objInfo: this.$store.state.userInfo.obj
		};
	},
	methods: {
		logout: function() {
			this.$store.commit('setUserInfo', null);
			this.$router.push('/post');
		},
		goInfo: function() {
			this.$router.push('/' + this.userType + '/info');
		},
		goBack() {
			this.$router.go(-1);
		}
	},
	computed: {
		userType() {
			let type = this.$store.state.userInfo.user.type;
			if (type === 0) return 'student';
			else if (type === 1) return 'tutor';
			else if (type === 2) return 'admin';
		}
	},
	beforeCreate: function() {
		let user = this.$store.state.userInfo;
		if (user === null) {
			this.$router.push('/post');
			alert('请先登录');
		}
	}
};
</script>

<style scoped="scoped">
a {
	text-decoration: none;
	color: #000000;
}
.router-link-exact-active {
	color: #409eff;
}

.jsstitle {
	font-size: large;
}
</style>
