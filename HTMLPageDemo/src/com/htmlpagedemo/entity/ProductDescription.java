package com.htmlpagedemo.entity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductDescription implements JSONSerializable {
	private String description;
	private String amazonDescription;
	private String parentAsin;
	private String productAbout;
	private String category;
	private String measurements;
	private ArrayList<String> bulletList;
	private String categoryString;
	public static HashMap<String, ArrayList<String>> bullets;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<String> getBulletList() {
		return bulletList;
	}

	public String getMeasurements() {
		return measurements;
	}

	public void setMeasurements(String measurements) {
		this.measurements = measurements;
	}

	public void setBulletList(ArrayList<String> bulletList) {
		this.bulletList = bulletList;
	}

	public String getCategoryString() {
		return categoryString;
	}

	public void setCategoryString(String categoryString) {
		this.categoryString = categoryString;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmazonDescription() {
		return amazonDescription;
	}

	public void setAmazonDescription(String amazonDescription) {
		this.amazonDescription = amazonDescription;
	}

	public String getParentAsin() {
		return parentAsin;
	}

	public void setParentAsin(String parentAsin) {
		this.parentAsin = parentAsin;
	}

	public String getProductAbout() {
		return productAbout;
	}

	public void setProductAbout(String productAbout) {
		this.productAbout = productAbout;
	}

	public HashMap<String, ArrayList<String>> getBullets() {
		return bullets;
	}

	public void setBullets(HashMap<String, ArrayList<String>> bullets) {
		this.bullets = bullets;
	}

	@Override
	public void deserializeJSON(JSONObject jsonObject) throws Exception {
		JSONArray jsonBulletArray = jsonObject.getJSONArray("bullets");
		this.description = jsonObject.has("description") ? jsonObject
				.getString("description") : "";
		this.amazonDescription = jsonObject.has("amazonDescription") ? jsonObject
				.getString("amazonDescription") : "";
		this.parentAsin = jsonObject.has("parentAsin") ? jsonObject
				.getString("parentAsin") : "";
		this.productAbout = jsonObject.has("prodAbout") ? jsonObject
				.getString("prodAbout") : "";
		this.description = jsonObject.has("description") ? jsonObject
				.getString("description") : "";
		this.amazonDescription = jsonObject.has("amazonDescription") ? jsonObject
				.getString("amazonDescription") : "";

		bullets = new HashMap<String, ArrayList<String>>();

		for (int i = 0; i < jsonBulletArray.length(); i++) {
			bulletList = new ArrayList<String>();
			this.category = jsonBulletArray.getJSONObject(i).has("category") ? jsonBulletArray
					.getJSONObject(i).getString("category") : "";
			JSONArray jsonBulletListArray = jsonBulletArray.getJSONObject(i)
					.getJSONArray("bulletsList");
			for (int j = 0; j < jsonBulletListArray.length(); j++) {
				bulletList.add(jsonBulletListArray.get(j).toString());

			}
			this.categoryString = jsonBulletArray.getJSONObject(i).has(
					"categoryString") ? jsonBulletArray.getJSONObject(i)
					.getString("categoryString") : "";
			bullets.put(categoryString, bulletList);
			System.out.println("category "+categoryString);
		}

	}

	@Override
	public JSONObject serializeJSON() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
