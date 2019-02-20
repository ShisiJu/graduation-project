package org.jasig.cas.client.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forestar.core.Config;
import com.forestar.core.exception.ServiceException;
import com.forestar.core.spring.SpringBeanFactory;
import com.forestar.core.util.StringUtils;
import com.forestar.data.general.QueryFilter;
import com.forestar.data.general.RowBase;
import com.forestar.data.service.BaseService;
import com.forestar.joint.resouces.client.ResoucesClient;

@Controller
@Path("/")
public class UserAccountService{
	
	Logger logger = Logger.getLogger(getClass());
	@Autowired(required = false)
	BaseService dataService;
	@Autowired
	ResoucesClient resoucesClient;


	static final String SSOToken = "SSOToken";
	static final String TOKEN = "token";

	static final String USERNAMEORIGINAL = "UserNameOriginal";
	static final String USERNAME = "UserName";
	static final String PASSWORD = "Password";
	static final String USEREALNAME = "NickName";
	static final String USERTEL = "Mobile";
	static final String USEREMAIL = "Email";
	static final String SUCCESS = "Success";
	public static final String FAILTEXT= "FailText";
	/**
	 * 请求用户信息url
	 */
	static final String SSOIndoorUrl = Config.getValue("SSOIndoorUrl");
	static final String CheckUserTokenUrl = Config.getValue("CheckUserTokenUrl");
	static final String CheckErrorUrl = Config.getValue("CheckErrorUrl"); 
	static final String CheckUserTokenFailText = "token验证失败";
	//系统User表
	static final String FS_PT_YW_BASE_USER = "FS_YW_BASE_USER";
	
	static final String CODINGTYPE = "UTF-8";
	/**
	 * 获得传送过来的值信息
	 * @return
	 */
	private Map<String,Object> getParams(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		BufferedReader reader;
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			StringBuffer sb = new StringBuffer();
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(),CODINGTYPE));
			String line;
			while ((line = reader.readLine()) != null){
				sb.append(line);
//				System.out.println(line);
			}
//			map = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(sb.toString()),map.getClass());
			map = JSON.parseObject(sb.toString(), map.getClass());
			Map<String,Object> result = CheckUserToken(map.get(TOKEN));
			if(null == result || !(Boolean) result.get(SUCCESS)){
				if(null == map) map = new HashMap<String, Object>();
				map.put(SUCCESS, false);
				map.put(FAILTEXT, CheckUserTokenFailText);
			}
		} catch (IOException e) {
			map.put(SUCCESS, false);
			map.put(FAILTEXT, e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
		

	@Path("/RegisterUser")
	@POST
	//@ResponseBody
	public String registerUser() {
		return addOrUpdate(false);
	}


	@Path("/UpdateUser")
	@POST
	//@ResponseBody
	public String updateUser() {
		return addOrUpdate(true);
	}
	

	@Path("/DeleteUser")
	@POST
	//@ResponseBody
	public String deleteUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params = getParams();
		if(null== params.get(FAILTEXT) || !params.get(FAILTEXT).equals(CheckUserTokenFailText)){//检查不通过
			String failText =null;
			map.put(SUCCESS, false);
			Object userName = params.get(USERNAME);
			if(StringUtils.isEmpty(userName)){
				failText = "用户名不能为空";
			}else{
				QueryFilter qf = new QueryFilter();
				qf.setWhereString("C_USERNAME = '"+userName+"'");
				try {
					Boolean b = dataService.del(FS_PT_YW_BASE_USER, qf);
					if(b){
						map.put(SUCCESS, true);
					}else{
						failText = "删除用户失败";
					}
				} catch (Exception e) {
					failText = "删除用户失败";
				}
			}
			if(!(Boolean) map.get(SUCCESS)) map.put(FAILTEXT, failText);
		}else{//token验证失败
			map = params;
		}
//		JSONObject jsonObject =JSONObject.fromObject(map);
		String jsonObject = JSON.toJSONString(map);
		return jsonObject.toString();
	}
	/**
	 * 新增更新用户方法
	 * @param request
	 * @param isAdd
	 * @return
	 */
	private String addOrUpdate(Boolean isUpdate){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params =getParams(); 
		if(null== params.get(FAILTEXT) || !params.get(FAILTEXT).equals(CheckUserTokenFailText)){
			String failText =null;
			map.put(SUCCESS, false);
			Object userNameOriginal = params.get(USERNAMEORIGINAL);//原用户名
			Object userName = params.get(USERNAME);//现用户名
			if(StringUtils.isEmpty(userNameOriginal)){//判断是否是更新
				userNameOriginal = userName;//原用户名和现用户名一致
			}
			if(StringUtils.isEmpty(userName)){
				failText = "用户名不能为空";
			}
			if(null == failText){//判断是否有失败信息
				try {
					RowBase row = new RowBase();
					if(!isUpdate){
						if(null != getUserInfo(userName)){
							isUpdate = true;
						};
					}
					if(isUpdate){
						HashMap<String,Object> hashMap = getUserInfo(userNameOriginal);
						if(null == hashMap) failText = "获取用户信息失败";
						row.setOriginalObjects(hashMap);
					}else if(StringUtils.isEmpty(params.get(PASSWORD))){
						failText = "密码不能为空";
					}
					if(null == failText){//更新或者保存数据
						if(!StringUtils.isEmpty(userName)) row.setByFieldName("C_USERNAME", userName);
						//2018-10-29 添加加密
						if(!StringUtils.isEmpty(params.get(PASSWORD))) row.setByFieldName("C_PWD", params.get(PASSWORD).toString());
						if(!StringUtils.isEmpty(params.get(USEREALNAME))) row.setByFieldName("C_USEREALNAME", params.get(USEREALNAME));
						if(!StringUtils.isEmpty(params.get(USERTEL))) row.setByFieldName("C_USERTEL", params.get(USERTEL));
						if(!StringUtils.isEmpty(params.get(USEREMAIL))) row.setByFieldName("C_USEREMIL", params.get(USEREMAIL));
						dataService.save(FS_PT_YW_BASE_USER, row);
						map.put(SUCCESS, true);
					}
				} catch (Exception e) {
					if(isUpdate) failText = "更新用户失败";
					else failText = "新增用户失败";
					e.printStackTrace();
				}
			}
			if(!(Boolean) map.get(SUCCESS)) map.put(FAILTEXT, failText);
		}else{
			map = params;
		}
		String jsonObject =JSON.toJSONString(map);
		return jsonObject.toString();
	}
	
	/**
	 * 获得本地用户信息
	 * @param userName
	 * @return
	 */
	public HashMap<String, Object> getUserInfo(Object userName){
		HashMap<String, Object> map = null;
		QueryFilter qf = new QueryFilter();
		qf.setWhereString("C_USERNAME = '"+userName+"'");
		try {
			List<RowBase> rows = dataService.getEntityList(FS_PT_YW_BASE_USER, qf);
			if(rows.size() > 0) map = rows.get(0).getOriginalObjects();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * 
	 * 静态方便调用
	 * @param token
	 * @return 	"NickName": "动物园管理员",
						"UserName": "ZoomAdmin",
						"HeadUrl": "\/UpFiles\/UserHeads\/8cda422c-7730-4c33-8a71-57999a2b73ae.jpg",
						"Token": "0b270b10-10cc-49a6-a54a-dd643e3deddb",
						"Mobile": "18074930826",
						"Email": "",
						"CompanyID": 1058
	 * @throws ServiceException 
	 */
	static Map<String, Object> SSOIndoor(String token) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TOKEN, token);
		map = httpPost(SSOIndoorUrl+"?"+TOKEN+"="+token, map);
		//兼容多一层data的方式
		Object status = map.get("status");
		Object data = map.get("data");
		if(StringUtils.isNotEmpty(status) && StringUtils.isNotEmpty(data)  && status.toString().equals("1")){
			map = JSONObject.parseObject(data.toString(),map.getClass());
		}
		map.put(SUCCESS, false);//默认失败
		Object userName =  map.get(USERNAME);
		if(!StringUtils.isEmpty(userName)){
			//静态方法，从SpringBeanFatory中取得dataService对象
			BaseService baseDataService =  (BaseService) SpringBeanFactory.getBean("baseService");
			QueryFilter qf = new QueryFilter();
			qf.setWhereString("C_USERNAME = '"+userName+"'");
			List<RowBase> rows = baseDataService.getEntityList(FS_PT_YW_BASE_USER, qf);
			if(rows.size() > 0){
				map.put(SUCCESS, true);//修改为成功
				map.put(PASSWORD,  String.valueOf(rows.get(0).getByFieldName("C_PWD")));
			}else{
				map.clear();
				map.put(FAILTEXT, "当前登录用户未同步");
			}
		}
		return map;
	}
	
	
	static Map<String, Object> CheckUserToken(Object token){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put(TOKEN, token);
		//如果不配置CheckUserTokenUrl则不进行check检查
		if(StringUtils.isEmpty(CheckUserTokenUrl)){
			param.put(SUCCESS, true);
			return param;
		}
		return httpPost(CheckUserTokenUrl, param);
	}
	
	/**
	 * JAVA发送POST请求
	 * @param url
	 * @param param
	 * @return
	 */
	static Map<String, Object> httpPost(String url, Map<String,Object> param){
		HttpURLConnection connection = null;
		StringBuffer sb = new StringBuffer();
		Map map =null;
        try {
			URL postUrl = new URL(url);
	        connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true); 
			connection.setRequestMethod("POST");     
			connection.setRequestProperty("Accept-Charset", CODINGTYPE);
			connection.setRequestProperty("Content-Type","application/json");
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), CODINGTYPE);
//			String json = JSONObject.fromObject(param).toString();
			String json = JSON.toJSONString(param);
			out.write(json);
			//流用完记得关
			out.flush();
			out.close();
			//获取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), CODINGTYPE));
			String line;
			while ((line = reader.readLine()) != null){
				sb.append(line);
			}
			reader.close();
			JSONObject jsonObject = null;
			if(url.equals(CheckUserTokenUrl)){//CheckUserTokenUrl返回的是 true或者false。变更true或者false为对象。
				jsonObject = new JSONObject();
				jsonObject.put(SUCCESS, Boolean.parseBoolean(sb.toString()));
			}else{
				jsonObject = JSON.parseObject(sb.toString());
			}
			map = JSON.parseObject(jsonObject.toJSONString(),param.getClass());
		} catch (Exception e) {
			map.put(SUCCESS, false);
			map.put(FAILTEXT, e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}