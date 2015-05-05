package com.ics.entity;

import java.util.ArrayList;

public class Faq {
	private String title;
	private String actionText;
	private int actionType;
	private String content;
	private ArrayList<Faq> items;
	
	public enum ActionTypes {
    	SITE,
    	CALL,
    	FILE

    }
	
	public String getTitle() {
		return title;
	}
	
	public String getActionText() {
		return actionText;
	}
	
	public int getActionType() {
		return actionType;
	}
	
	public String content() {
		return content;
	}
	
	public ArrayList<Faq> getItems() {
		return items;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setActionText(String actionText) {
		this.actionText = actionText;
	}
	
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setItems(ArrayList<Faq> items) {
		this.items = items;
	}
	
}
