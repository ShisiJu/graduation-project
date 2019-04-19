<template>
	<div>
		<el-container>
			<el-aside width="15%">
				<el-menu>
					<el-submenu index="1" name="student" v-if="type === 'student'">
						<template slot="title">
							<i class="el-icon-message"></i>
							学生信息
						</template>
						<el-menu-item-group>
							<el-menu-item index="1-1">我的导师</el-menu-item>
							<el-menu-item index="1-2">
								<router-link :to="'course'">我的课程</router-link>
							</el-menu-item>
							<el-menu-item index="1-3">
								<router-link :to="'info'">个人信息</router-link>
							</el-menu-item>
						</el-menu-item-group>
					</el-submenu>

					<el-submenu index="2" name="tutor">
						<template slot="title">
							<i class="el-icon-menu"></i>
							导师信息
						</template>
						<el-menu-item-group>
							<el-menu-item index="2-1">我的学生</el-menu-item>
							<el-menu-item index="2-2">我的课程</el-menu-item>
							<el-menu-item index="2-3">个人信息</el-menu-item>
						</el-menu-item-group>
					</el-submenu>

					<el-submenu index="3" name="admin">
						<template slot="title">
							<i class="el-icon-setting"></i>
							管理员
						</template>

						<el-submenu index="3-1">
							<template slot="title">
								学生
							</template>
							<el-menu-item index="3-1-1">学生个人信息</el-menu-item>
							<el-menu-item index="3-1-2">学生选课信息</el-menu-item>
						</el-submenu>

						<el-submenu index="3-2">
							<template slot="title">
								导师
							</template>
							<el-menu-item index="3-2-1">导师个人信息</el-menu-item>
							<el-menu-item index="3-2-2">导师课程信息</el-menu-item>
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
							<el-dropdown-item>个人信息</el-dropdown-item>
							<el-dropdown-item>登出</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
					<span>{{ name }}</span>
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
		return { name: '琚世思', type: this.$route.query.type };
	}
};
</script>

<style scoped="scoped">
a {
	text-decoration: none;
	color: #000000;
	
}
.router-link-exact-active
{
color: #409EFF;	
}
</style>
