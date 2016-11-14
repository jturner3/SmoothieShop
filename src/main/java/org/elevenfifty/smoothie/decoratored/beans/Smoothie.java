package org.elevenfifty.smoothie.decoratored.beans;

import java.util.ArrayList;
import java.util.List;

import org.elevenfifty.smoothie.beans.Ingredient;
import org.elevenfifty.smoothie.decoratored.Item;

public class Smoothie implements Item {

	private final String name;
	private final Item decorated;
	private final Ingredient ingredient;

	public Smoothie(Ingredient i) {
		this.name = i.getName() + " Smoothie";
		this.ingredient = i; // Espresso
		this.decorated = null;
	}

	public Smoothie(Ingredient i, Item decorated) {
		this.name = i.getName() + " Smoothie";
		this.ingredient = i; // Cream
		this.decorated = decorated; // Espresso
	}

	@Override
	public List<String> getInstructions() {
		List<String> steps = this.ingredient.getInstructions();

		if (decorated != null) {
			steps.addAll(decorated.getInstructions());
		}

		return steps;
	}

	@Override
	public double getCost() {
		double cost = this.ingredient.getCost();

		if (decorated != null) {
			cost += decorated.getCost();
		}

		return cost;
	}

	public List<Ingredient> getIngredients() {
		List<Ingredient> list;
		if (this.decorated == null) {
			list = new ArrayList<>();
		} else {
			list = decorated.getIngredients();
		}

		list.add(ingredient);
		return list;
	}

	@Override
	public String getName() {
		return name;
	}
}