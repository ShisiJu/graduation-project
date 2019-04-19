//对时间的处理
import moment from 'moment'
//时间过滤器
Vue.filter('dateFormat', function(date, pattern = 'YYYY-MM-DD HH:MM') {
	return moment(date).format(pattern);
})
