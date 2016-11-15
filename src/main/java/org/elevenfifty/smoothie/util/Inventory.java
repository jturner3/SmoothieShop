package org.elevenfifty.smoothie.util;

import org.elevenfifty.smoothie.beans.Ingredient;
import org.elevenfifty.smoothie.decoratored.Item;

public class Inventory {
	
		public static boolean hasSufficientInventory(Item item) {
			for (Ingredient in : item.getIngredients()) {
				if (in.getQty() <= 0) {
					return false;
				}
			}
			return true;
		}
		
		public static void consumeIngredients(Item item) {
			for (Ingredient in : item.getIngredients()) {
				in.setQty(in.getQty() - 1);
			}
		}
}