package userdatatable;

public class UserBean {
	String name, mobile, gender, bgrp;
	
	public UserBean() {}

	public UserBean(String name, String mobile, String gender, String bgrp) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.gender = gender;
		this.bgrp = bgrp;
	}

	public UserBean(String name, String mobile, String gender) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBgrp() {
		return bgrp;
	}

	public void setBgrp(String bgrp) {
		this.bgrp = bgrp;
	}
	
}
