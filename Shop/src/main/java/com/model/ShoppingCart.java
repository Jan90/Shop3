package com.model;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ShoppingCart {

	protected CopyOnWriteArrayList<CD> items;

	public ShoppingCart() {
		items = new CopyOnWriteArrayList<CD>();
	}

	@SuppressWarnings("unchecked")
	public CopyOnWriteArrayList<CD> getProducts() {
		return  items;
	}

	public void addItem(CD newItem) {

		if (newItem.getQuantity() ==1) {
			items.add(newItem);
			return;}
		for (int i = 0; i < items.size(); i++) {
			CD cd = (CD) items.get(i);
			if (cd.getName().equals(newItem.getName())) {
				cd.setQuantity();
				items.set(i,cd);
				return;
			}
		}
	}

	public void removeProduct(int productIndex) {
		items.remove(productIndex);
	}

}
