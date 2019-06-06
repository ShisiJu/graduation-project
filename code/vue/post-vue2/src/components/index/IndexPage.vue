<template>
	<div>
		<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
			<el-menu-item index="1" @click="jumpUrl('/')">Home</el-menu-item>
			<el-submenu index="2">
				<template slot="title">
					英语学习
				</template>
				<el-menu-item index="2-1">ESL Pod</el-menu-item>
				<el-menu-item index="2-2">EngVid</el-menu-item>
				<el-menu-item index="2-3">每日英语听力</el-menu-item>
			</el-submenu>
			<el-submenu index="3">
				<template slot="title">
					编程学习
				</template>
				<el-menu-item index="3-1">JavaSE</el-menu-item>
				<el-menu-item index="3-2">JVM</el-menu-item>
				<el-menu-item index="3-3">算法与数据结构</el-menu-item>
			</el-submenu>
			<el-submenu index="4">
				<template slot="title">
					项目分享
				</template>
				<el-menu-item index="4-1" @click="jumpUrl('/post')">研究生导师评价系统</el-menu-item>
			</el-submenu>
			<el-menu-item index="5">我的经验分享</el-menu-item>
		</el-menu>

		<div class="carouselBox">
			<br />
			<el-carousel :interval="4000" type="card" height="200px">
				<el-carousel-item v-for="item in carouselData" :key="item.src"><img :src="item.src" class="carouselImg" @click="goUrl(item.url)" /></el-carousel-item>
			</el-carousel>
		</div>
	</div>
</template>

<script>
let imgUrlPrefix = './static/img/carousel/';
export default {
	name: 'j-index',
	data() {
		return {
			carouselData: [
				{ src: imgUrlPrefix + '1.jpg', url: 'https://secure3.eslpod.com/', title: 'ESL Pod', id: 1 },
				{ src: imgUrlPrefix + '2.jpg', url: 'https://www.csdn.net/', title: 'CSDN', id: 2 },
				{ src: imgUrlPrefix + '3.jpg', url: 'https://github.com/', title: 'GitHub', id: 3 }
			],
			activeIndex: '1'
		};
	},
	methods: {
		goUrl(url) {
			console.log(url);
			window.location.href = url;
		},
		jumpUrl(url) {
			this.$router.push(url);
		}
	},
	created() {}
};
</script>

<style>
.carouselImg {
	background-size: cover;
}

.carouselBox {
	width: 80%;
	margin-left: 10%;
}
.el-carousel__item img {
	width: 100%;
	height: 100%;
}

.el-carousel__item:nth-child(2n) {
	background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
	background-color: #d3dce6;
}
</style>
