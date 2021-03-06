package org.elevenfifty.smoothie;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.elevenfifty.smoothie.beans.Recipe;
import org.elevenfifty.smoothie.decoratored.beans.Smoothie;
import org.elevenfifty.smoothie.util.PrettyPrinter;


public class Main {
	private static final Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws IOException {
		Configuration config = Configuration.configure("recipes.csv", "ingredients.csv");

		// Recipe Example
		Recipe r = config.getRecipe("Best Smoothie");

		PrettyPrinter.print(r);

		// Decorator Pattern Example
		Smoothie s = new Smoothie(config.getIngredient("Orange"));
		s = new Smoothie(config.getIngredient("Banana"), s);

		logger.info(printPretty("Ingredients:", s.getIngredients()));
		logger.info(printPretty("Instructions:", s.getInstructions()));
		logger.info(s.getCost());
		
		Browser browser = new Browser(config);
		browser.displayRecipes();
		PrettyPrinter.print(browser.readRecipe());
	}

	private static String printPretty(String preamble, List<? extends Object> lines) {
		StringBuffer b = new StringBuffer(preamble);
		b.append("\n");
		for (Object line : lines) {
			b.append("\t");
			b.append(line.toString());
			b.append("\n");
		}
		return b.toString();
	}
}