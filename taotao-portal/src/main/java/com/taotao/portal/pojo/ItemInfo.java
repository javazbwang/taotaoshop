package com.taotao.portal.pojo;

import com.taotao.pojo.Item;

public class ItemInfo extends Item {
	public String[] getImages() {
		String image = getImage();
		if (image!=null) {
			String[] images=image.split(",");
			return images;
			
		}
		return null;
	}
}
