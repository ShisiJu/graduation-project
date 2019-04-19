<template>
	<div>
		{{tableData.date | dateFormat}}
		<RouterView></RouterView>
	</div>
</template>

<script>
export default {
	name: 'j-student',
	data() {
		return {
			tableData: [{name:"",date:"1994-1-1",address:"bj"}]
		};
	}
};
</script>

<style scoped>
	
</style>
