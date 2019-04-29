<template>
	<div>
		<el-container>
			<el-aside width="15%">
				<el-menu>
					<el-submenu index="1" name="student" v-if="userInfo.type === 0">
						<template slot="title">
							<i class="el-icon-message"></i>
							学生信息
						</template>
						<el-menu-item-group>
							<router-link :to="'/student/course'">
								<el-menu-item index="1-1">我的课程</el-menu-item>
							</router-link>
							<router-link :to="'/student/info'">
								<el-menu-item index="1-2">个人信息</el-menu-item>
							</router-link>
						</el-menu-item-group>
					</el-submenu>

					<el-submenu index="2" name="tutor" v-if="userInfo.type === 1">
						<template slot="title">
							<i class="el-icon-menu"></i>
							导师信息
						</template>
						<el-menu-item-group>
							<router-link :to="'/tutor/course'">
								<el-menu-item index="2-1">我的课程</el-menu-item>
							</router-link>
							<router-link :to="'/tutor/info'">
								<el-menu-item index="2-2">个人信息</el-menu-item>
							</router-link>
						</el-menu-item-group>
					</el-submenu>

					<el-submenu index="3" name="admin" v-if="userInfo.type === 2">
						<template slot="title">
							<i class="el-icon-setting"></i>
							管理员
						</template>

						<el-submenu index="3-1">
							<template slot="title">
								学生
							</template>
							<router-link :to="'/admin/students'">
								<el-menu-item index="3-1-1">学生个人信息</el-menu-item>
							</router-link>
							<router-link :to="'/admin/groups'">
								<el-menu-item index="3-1-2">组信息</el-menu-item>
							</router-link>
						</el-submenu>

						<el-submenu index="3-2">
							<template slot="title">
								导师
							</template>
							<router-link :to="'/admin/tutors'">
								<el-menu-item index="3-2-1">导师个人信息</el-menu-item>
							</router-link>
							<router-link :to="'/admin/groups'">
								<el-menu-item index="3-2-2">导师课程信息</el-menu-item>
							</router-link>
						</el-submenu>

						<el-submenu index="3-3">
							<template slot="title">
								课程
							</template>
							<el-menu-item index="3-3-1">课程统计</el-menu-item>
						</el-submenu>
					</el-submenu>
				</el-menu>
			</el-aside>

			<el-container>
				<el-header style="text-align: right; font-size: 18px">
					<el-dropdown>
						<i class="el-icon-setting" style="margin-right: 15px;font-size: large;"></i>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item @click.native.prevent='goInfo()'>个人信息</el-dropdown-item>
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
		logout:function() {
			console.log('------logout')
			this.$store.commit('setUserInfo', null);
			this.$router.push('/');
		},goInfo:function(){
			console.log('------goInfo')
			this.$router.push('/'+this.userType+'/info');
		}
	},computed: {
		userType() {
			let type = this.$store.state.userInfo.user.type;
			if(type===0)
				return 'student'
			else if (type === 1)
				return 'tutor'
			else if(type === 2)
				return 'admin'
		}
	},
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
</style>
