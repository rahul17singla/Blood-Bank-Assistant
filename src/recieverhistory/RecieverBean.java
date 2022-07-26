package recieverhistory;

public class RecieverBean {

	String name,mobile,bgrp,doi,hospital,reason;
	int units;
	
	public RecieverBean() {}
	
	public RecieverBean(String name, String mobile, String bgrp, String doi, String hospital, String reason, int units) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.bgrp = bgrp;
		this.doi = doi;
		this.hospital = hospital;
		this.reason = reason;
		this.units = units;
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

	public String getBgrp() {
		return bgrp;
	}

	public void setBgrp(String bgrp) {
		this.bgrp = bgrp;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

}
