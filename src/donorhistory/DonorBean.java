package donorhistory;

public class DonorBean {

	String mobile;
	String bgrp;
	String dod;
	
	public DonorBean() {}
	
	public DonorBean(String mobile, String bgrp, String dod) {
		super();
		this.mobile = mobile;
		this.bgrp = bgrp;
		this.dod = dod;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBgrp() {
		return bgrp;
	}

	public void setBgrp(String bgrp) {
		this.bgrp = bgrp;
	}

	public String getDod() {
		return dod;
	}

	public void setDod(String dod) {
		this.dod = dod;
	}
	
	

}
