package com.list.entity;

import java.util.ArrayList;

import com.list.data.ListData;

public class ArrayLists {

	public static ArrayList<ListData> getListData_arr;
	public static ListData listDataObject;

	public ArrayLists() {
		getListData_arr = new ArrayList<ListData>();

	}

	public static ArrayList<ListData> getListData() {
		getListData_arr.add(new ListData("deven", "parel mumbai-400012", "9930486071"));
		getListData_arr.add(new ListData("sachin", "kopar khairane mumbai-400012", "9930486071"));
		getListData_arr.add(new ListData("gaurang", "kurla mumbai-400012", "9930486071"));
		getListData_arr.add(new ListData("sidd", "Thane mumbai-400012", "9930486071"));
		getListData_arr.add(new ListData("suraj", "Nerul mumbai-400012", "9930486071"));
		getListData_arr.add(new ListData("Abhay", "Badlapur mumbai-400012", "9930486071"));
		return getListData_arr;

	}

}
