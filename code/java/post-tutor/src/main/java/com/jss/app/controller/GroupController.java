package com.jss.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jss.app.model.entity.Group;
import com.jss.app.service.GroupService;

@RequestMapping("/group")
@RestController
public class GroupController {

	@Autowired
	private GroupService groupService;

	@RequestMapping("/all")
	public List<Group> findAllGroups() {

		return groupService.findAllGroups();
	}

	@RequestMapping("/institute")
	public List<Group> findByInstitute_id(@RequestParam Long institute_id) {

		return groupService.findByInstitute_id(institute_id);
	}

	@RequestMapping("/institute_ids")
	public List<Group> findByInstitute_idIn(@RequestBody JSONArray listId) {
		List<Long> ids = listId.toJavaList(Long.class);
		return groupService.findByInstitute_idIn(ids);
	}

	@RequestMapping("/deleteGroup")
	public void deleteGroup(Long groupId) {

		groupService.deleteGroupById(groupId);
	}

	@RequestMapping("/saveGroup")
	public Group saveGroup(@RequestBody JSONObject jsonObject) {

		Group group = jsonObject.getObject("group", Group.class);
		Long instituteId = jsonObject.getLong("instituteId");
		return groupService.saveGroup(group, instituteId);

	}
	

	
	

	@RequestMapping("/searchGroups")
	public Page<Group> searchGroups(@RequestBody JSONObject jsonObject) {

		String name = jsonObject.getString("name");
		List<Long> instituteIds = jsonObject.getJSONArray("instituteIds").toJavaList(Long.class);
		Integer index = jsonObject.getInteger("index");
		Integer pageSize = jsonObject.getInteger("pageSize");
		return groupService.searchGroups(name, instituteIds, index, pageSize);
	}

}
