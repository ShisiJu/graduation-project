<template>
	<div>
		<el-form ref="form" :model="form" label-width="80px">
			<el-form-item label="活动名称"><el-input v-model="form.name"></el-input></el-form-item>

			<el-radio-group v-model="form.quizAnswer[0].answer">
				<el-radio :label="A">A.十分满意</el-radio>
				<el-radio :label="B">B.比较满意</el-radio>
				<el-radio :label="C">C.不太满意</el-radio>
				<el-radio :label="D">D.不满意</el-radio>
			</el-radio-group>

			<el-radio-group v-model="form.quizAnswer[1].answer">
				<el-radio :label="A">A.十分满意</el-radio>
				<el-radio :label="B">B.比较满意</el-radio>
				<el-radio :label="C">C.不太满意</el-radio>
				<el-radio :label="D">D.不满意</el-radio>
			</el-radio-group>

			<el-radio-group v-model="form.quizAnswer[2].answer">
				<el-radio :label="A">A.十分满意</el-radio>
				<el-radio :label="B">B.比较满意</el-radio>
				<el-radio :label="C">C.不太满意</el-radio>
				<el-radio :label="D">D.不满意</el-radio>
			</el-radio-group>
			<el-form-item label="活动形式">
				<el-input type="textarea" v-model="form.desc"></el-input>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" @click="onSubmit">立即创建</el-button>
				<el-button>取消</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	// ajax 请求时,需要传入 quiz 和 quiz answer两个才能确定

export default {
	data(){
		form:{name:"课程评价问卷",quiz:{},quizAnswer:[{answer:'',type:'0',question:0},{},{}]}
	}
};
</script>

<style></style>
