package org.elevenfifty.smoothie;

import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.elevenfifty.smoothie.Configuration.configure;
import static org.elevenfifty.smoothie.util.Inventory.consumeIngredients;
import static org.elevenfifty.smoothie.util.Inventory.hasSufficientInventory;
import static org.elevenfifty.smoothie.util.PrettyPrinter.prettyPrint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elevenfifty.smoothie.beans.Recipe;

public class SmoothieShoppe implements Runnable {

	public static void main(String[] args) throws IOException {
		new SmoothieShoppe(configure("recipes.csv", "ingredients.csv")).run();
	}

	private final Configuration config;
	private final Browser browser;
	private final List<Recipe> cart = new ArrayList<>();

	public SmoothieShoppe(Configuration config) {
		this.config = config;
		this.browser = new Browser(config);
	}

	@Override
	public void run() {
		out.println();
		out.println("** Smoothie Shoppe **");

		while (true) {
			switch (menu()) {
			case 'b':
				browse();
				break;
			case 'm':
				makeSmoothies();
				break;
			case 'q':
				return;
			}

		}
	}

	private char menu() {
		out.println();
		out.println("Application Menu:");
		out.println("\t[b] Browse Smoothies");
		out.println("\t[m] Make Smoothies");
		out.println("\t[q] Quit");
		out.println();
		out.print("Select an option: ");

		while (true) {
			String choice = config.getScanner().next();
			if (asList("b", "m", "q").stream().anyMatch(e -> e.equals(choice))) {
				return choice.charAt(0);
			}
			out.println("Invalid option");
		}

	}

	private void showCart() {
		out.println();
		out.println("Selected Smoothies:");
		for (Recipe r : cart) {
			out.println("\t" + r.getName());
		}
	}

	private void browse() {
		browser.displayRecipes();
		Recipe selectedRecipe = browser.readRecipe();

		if (hasSufficientInventory(selectedRecipe)) {
			cart.add(selectedRecipe);
			consumeIngredients(selectedRecipe);
			showCart();
		} else {
			out.println("Insufficient Inventory");
		}
	}

	private void makeSmoothies() {
		if(cart.isEmpty()) {
			out.println();
			out.println("No smoothies selected!");
			return;
		}
		for (Recipe r : cart) {
			prettyPrint(r);
		}
		cart.clear();
	}

}