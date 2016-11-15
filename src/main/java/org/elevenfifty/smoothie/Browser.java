package org.elevenfifty.smoothie;

import static java.lang.System.out;
import static org.elevenfifty.smoothie.util.Inventory.hasSufficientInventory;

import java.util.Scanner;

import org.elevenfifty.smoothie.beans.Recipe;

public class Browser {

	private final Configuration config;
	private final Scanner in = new Scanner(System.in);

	public Browser(Configuration config) {
		this.config = config;
	}

	public void displayRecipes() {
		out.println();
		out.println("Available Recipes:");
		for (int i = 0; i < config.listRecipes().size(); i++) {
			Recipe r = config.getRecipe(i);
			if (hasSufficientInventory(r)) {
				out.format("%d: %s $%,01.2f%n", i + 1, r.getName(), r.getCost());
			} else {
				out.format("%d: %s (insufficient inventory)%n", i + 1, r.getName());
			}
		}
	}

	public Recipe readRecipe() {
		System.out.println();
		System.out.print("Select a Smoothie Recipe: ");

		return config.getRecipe(in.nextInt() - 1);
	}

}