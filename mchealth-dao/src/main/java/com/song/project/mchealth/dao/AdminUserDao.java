package com.song.project.mchealth.dao ;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.song.project.mchealth.dao.model.AdminUser;
@Component
public class AdminUserDao extends GenericDAOImpl<AdminUser> {
	@Override
	public String getNameSpace() {
		return "hepet_admin_user";
	}

	public AdminUser findByUserNameAndPwd(Map<String, String> param) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".findByUserNameAndPwd" , param);
	}

	public int modifyLoginPwd(Map<String, Object> params) {
		return  this.getSqlSession().update(this.getNameSpace()+".modifyLoginPwd", params);
	}

}
