eslint 语法验证太严格 可以在 config/index.js

```
useEslint: false
```
# post-vue

是`vue 3.x`项目





# post-vue2 

`vue 2.x`项目

css 需要单独引用

安装axios

```
cnpm install axios --save
```

安装element组件
```
cnpm i element-ui --save 
```

[](https://jingyan.baidu.com/article/c275f6ba6afd84e33d75670d.html)
一些alias

在 `build/webpack.base.conf.js` 

```
 '@': resolve('src'),
```

## axios

```
{
  // `data` 由服务器提供的响应
  data: {},
 
  // `status` 来自服务器响应的 HTTP 状态码
  status: 200,
 
  // `statusText` 来自服务器响应的 HTTP 状态信息
  statusText: 'OK',
 
  // `headers` 服务器响应的头
  headers: {},
 
  // `config` 是为请求提供的配置信息
  config: {}
}
```


[最全的vue.js视频【黑马程序员】](https://www.bilibili.com/video/av36650577/?p=144)
基于 Promise 模型
.then(succe(),err())

.catch 如果有一个出现错误都不继续执行
## filter

Vue的filter 可以通过 `|` 来调用

## 跨域

[axios可以解决跨域访问的问题吗？](https://segmentfault.com/q/1010000007665348)
[CORS](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS#)
[Vue-cli proxyTable 解决开发环境的跨域问题](https://www.jianshu.com/p/95b2caf7e0da)

```js
import axios from 'axios';
import qs from 'qs';

axios.post('http://www.xyz.com/request', qs.stringify(params))
.then(response => {
  console.log(response);
})
.catch(err => {
  console.log(err);
});
```
npm install qs

[](https://www.jianshu.com/p/042632dec9fb)

[](https://github.com/axios/axios/blob/master/README.md#using-applicationx-www-form-urlencoded-format)
{{headers: {'Content-Type': 'application/x-www-form-urlencoded'}}


axios的POST默认提交的`content-type`是`application/json`
而spring mvc 的 @RequestParam 是无法接收的



表单提交
```
import qs from 'qs';
const data = { 'bar': 123 };
const options = {
  method: 'POST',
  headers: { 'content-type': 'application/x-www-form-urlencoded' },
  data: qs.stringify(data),
  url,
};
axios(options);
```

```
{
  headers: { 'content-type': 'application/x-www-form-urlencoded' }
}
```

```
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
```

## this

[this 指向详细解析（箭头函数）](https://www.cnblogs.com/dongcanliang/p/7054176.html)
js的严格模式

[](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Functions/Arrow_functions)
[严格模式的限制](http://www.runoob.com/js/js-strict.html)
严格模式的限制

箭头函数不会创建自己的this,它只会从自己的作用域链的上一层继承this