package com.song.project.mchealth.admin.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.song.project.mchealth.admin.service.UserService;
import com.song.project.mchealth.admin.utils.WebUtil;
import com.song.project.mchealth.common.utils.JsonUtils;
import com.song.project.mchealth.common.utils.VerifyCodeUtils;

@Controller
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/admin/login/img_code")
	@ResponseBody
	public void getImgCode(HttpServletResponse response , HttpSession session) throws IOException{
		String verifyCode = VerifyCodeUtils.outputVerifyImage(200, 80, response.getOutputStream(), 4);
		session.setAttribute("imgVerifyCode", verifyCode);
	}
	
	private JSONObject verifyImgCode(HttpSession session , String custVerifyCode){
		JSONObject result = JsonUtils.commonJsonReturn();
		String sessionCode = (String)session.getAttribute("imgVerifyCode");
		if(!StringUtils.equalsIgnoreCase(custVerifyCode, sessionCode)){
			return JsonUtils.commonJsonReturn("0001", "验证码错误");
		}
		session.removeAttribute("imgVerifyCode");
		return result;
	}
	
	@RequestMapping(value="/admin/user/login")
	@ResponseBody
	public String userLogin(HttpServletRequest request , HttpServletResponse response , HttpSession session ,
			@RequestParam(value="imgVerify") String imgVerify , 
			@RequestParam(value="userName") String userName,
			@RequestParam(value="loginPwd") String loginPwd){
		JSONObject result = JsonUtils.commonJsonReturn();
		//验证图片验证码
		result = verifyImgCode(session, imgVerify);
		if(!JsonUtils.equalDefSuccCode(result)){
			return result.toJSONString();
		}
		//用户账户名和密码验证
		JSONObject loginResult = userService.login(userName, loginPwd);
		if(JsonUtils.equalDefSuccCode(loginResult)){
			@SuppressWarnings("unchecked")
			Map<String , Object> loginMap = loginResult.getJSONObject("body").getObject("loginMap", Map.class);
			session.setAttribute("userType", (String)loginMap.get("userType"));
			session.setAttribute("userId", loginMap.get("userId"));
			session.setAttribute("userName", userName);
			session.setAttribute("needChangePwd",  loginMap.get("needChangePwd"));
		}
		return loginResult.toJSONString();
	}
	
	@RequestMapping(value="/admin/user/logout")
	@ResponseBody
	public String userLogout(HttpSession session){
		try{
	    	WebUtil.logOut(session);
	    	return JsonUtils.commonJsonReturn().toJSONString();
    	}catch(Exception e){
			logger.error("logout error " ,e);
			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
		}
	}
	
	
	@RequestMapping(value="/admin/user/modify_pwd")
	@ResponseBody
	public String modify_pwd(HttpServletRequest request , HttpServletResponse response ,
												@RequestParam(value="oldPwd") String oldPwd,
												@RequestParam(value="newPwd") String newPwd
												){
		try{
			JSONObject result =  userService.modifyLoginPwd(WebUtil.getUserId(request) , oldPwd , newPwd);
	    	 return result.toJSONString();
    	}catch(Exception e){
			logger.error("modify_pwd error " ,e);
			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
		}
	}
	
}
