package com.search.entity;

public class User {
	private String countryName;
	private String EmpName;
	private String id;

	public User() {

	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getEmpName() {
		return EmpName;
	}

	public User(String countryName, String empName) {
		super();
		this.countryName = countryName;
		EmpName = empName;
	}

	public void setEmpName(String empName) {
		EmpName = empName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
