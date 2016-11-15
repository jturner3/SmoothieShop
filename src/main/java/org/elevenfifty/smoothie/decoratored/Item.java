package org.elevenfifty.smoothie.decoratored;

import java.util.List;

import org.elevenfifty.smoothie.beans.Ingredient;

public interface Item {
	
	public String getName();

	public List<String> getInstructions();
	
	public double getCost();

	public List<Ingredient> getIngredients();
	
	public void consumeIngredients();
}
