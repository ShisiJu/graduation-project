<template>
	<div>
		<el-form ref="form" :model="form" label-width="15.625rem">
			<el-form-item label="课程">{{ course.name }}</el-form-item>
			<el-form-item label="上课状态">
				<el-radio-group v-model="form.optAnswers[0]">
					<el-radio label="A">A.十分满意</el-radio>
					<el-radio label="B">B.比较满意</el-radio>
					<el-radio label="C">C.不太满意</el-radio>
					<el-radio label="D">D.不满意</el-radio>
				</el-radio-group>
			</el-form-item>

			<el-form-item label="授课方式">
				<el-radio-group v-model="form.optAnswers[1]">
					<el-radio label="A">A.十分满意</el-radio>
					<el-radio label="B">B.比较满意</el-radio>
					<el-radio label="C">C.不太满意</el-radio>
					<el-radio label="D">D.不满意</el-radio>
				</el-radio-group>
			</el-form-item>

			<el-form-item label="认真负责">
				<el-radio-group v-model="form.optAnswers[2]">
					<el-radio label="A">A.十分满意</el-radio>
					<el-radio label="B">B.比较满意</el-radio>
					<el-radio label="C">C.不太满意</el-radio>
					<el-radio label="D">D.不满意</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="对导师的意见">
				<el-input
					type="textarea"
					style="width: 24rem;"
					v-model="form.descAnswers[0]"
				></el-input>
			</el-form-item>

			<el-form-item label="提交方式">
				<el-switch
					v-model="form.anonymous"
					active-text="实名"
					inactive-text="匿名"
				></el-switch>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="save && postQuizWithAnswers()">
					确认提交
				</el-button>
				<el-button>取消</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
import { insertQuizWithAnswers } from '@/api/axiosAPI';

export default {
	data() {
		return {
			form: { optAnswers: ['A', 'A', 'A'], anonymous: 0, descAnswers: [''] },
			course: this.$store.state.quiz.course,
			//设置是否可以保存,防止多次提交数据
			save: true
		};
	},
	methods: {
		saveQuizAnswers: function() {
			let quizAnswers = [];
			let answer = { question: 0, type: 0, answer: 'A' };
			this.form.optAnswers.forEach((e, i) => {
				let answer = { question: i, type: 0, answer: e };
				quizAnswers.push(answer);
			});
			let len = quizAnswers.length;
			this.form.descAnswers.forEach((e, i) => {
				let answer = { question: len + i, type: 1, answer: e };
				quizAnswers.push(answer);
			});
			return quizAnswers;
		},saveQuizAndAnswers:function(){
			let quiz = {anonymous:this.anonymous,template:0,studentCourse:null};
			let quizAnswers = this.saveQuizAnswers();
			let studentCourseId = this.$store.state.quiz.id;
			let squiz = {quiz,quizAnswers,studentCourseId}
			return squiz;
		},
		postQuizWithAnswers: function() {
			let confired = confirm('是否确认提交');
			if (confired == false) return;
			this.save = false;
			
			insertQuizWithAnswers(this.saveQuizAndAnswers()).then(res => {
				if (res.status != 200) {
					this.save = true;
					alert('提交失败');
					return;
				}
				this.$router.push('/student/course');
			});
		}
	},
	computed: {
		anonymous: function() {
			if (this.form.anonymous) {
				return 1;
			} else {
				return 0;
			}
		}
	}
};
</script>

<style scoped="scoped">
div .el-form-item__content {
	margin-left: -6.25rem;
}
</style>
