package org.elevenfifty.smoothie.util;

import org.elevenfifty.smoothie.beans.Ingredient;
import org.elevenfifty.smoothie.decoratored.Item;

public class Inventory {

	private Inventory() {
	}

	public static boolean hasSufficientInventory(Item item) {
		for (Ingredient in : item.getIngredients()) {
			if (in.getQty() <= 0) {
				return false;
			}
		}
		return true;
	}

}
