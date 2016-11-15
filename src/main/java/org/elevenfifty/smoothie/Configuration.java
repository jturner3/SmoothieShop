package org.elevenfifty.smoothie;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.elevenfifty.io.CSVReader;
import org.elevenfifty.smoothie.beans.Ingredient;
import org.elevenfifty.smoothie.beans.Ingredient.Type;
import org.elevenfifty.smoothie.beans.Produce;
import org.elevenfifty.smoothie.beans.Recipe;

public class Configuration {

	private final Scanner scan;
	private List<Ingredient> ingredients = new ArrayList<>();
	private List<Recipe> recipes = new ArrayList<>();
	private Map<String, Ingredient> ingredientMap = new HashMap<>();
	private Map<String, Recipe> recipeMap = new HashMap<>();

	private Configuration() {
		scan = new Scanner(System.in);
	}

	private CSVReader loadCSVFromResource(String filename) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
		return new CSVReader(new InputStreamReader(is));
	}

	private void loadRecipes(String filename) throws IOException {
		try (CSVReader csv = loadCSVFromResource(filename)) {
			csv.skipRow();
			for (String[] row = csv.readRow(); row != null; row = csv.readRow()) {
				Recipe recipe = new Recipe();
				recipe.setName(row[0]);
				System.out.println("Loaded: " + recipe.getName());
				List<Ingredient> ingredients = new ArrayList<>(row.length - 1);
				for (int i = 1; i < row.length; i++) {
					ingredients.add(getIngredient(row[i]));
				}
				recipe.setIngredients(ingredients);
				recipeMap.put(recipe.getName(), recipe);
				recipes.add(recipe);
			}
		}
	}

	private void loadIngredients(String filename) throws IOException {
		try (CSVReader csv = loadCSVFromResource(filename)) {
			csv.readHeaderRow();
			for (Map<String, String> row = csv.readRowAsMap(); row != null; row = csv
					.readRowAsMap()) {
				Type type = Type.valueOf(row.get("Type"));
				Ingredient ingredient;
				if (type == Type.FRUIT || type == Type.VEGETABLE) {
					Produce produce = new Produce();
					produce.setPeelable(Boolean.valueOf(row.get("Peelable")));
					produce.setPitable(Boolean.valueOf(row.get("Pitable")));
					produce.setNeedsWash(Boolean.valueOf(row.get("Needs Washed")));
					ingredient = produce;
				} else {
					ingredient = new Ingredient();
				}
				ingredient.setName(row.get("Name"));
				ingredient.setQty(Integer.valueOf(row.get("Quantity")));
				ingredient.setCost(Double.valueOf(row.get("Cost")));
				ingredient.setType(Type.valueOf(row.get("Type")));
				ingredientMap.put(ingredient.getName(), ingredient);
				ingredients.add(ingredient);
			}
		}
	}

	public List<Ingredient> listIngredients() {
		return ingredients;
	}

	public List<Recipe> listRecipes() {
		return recipes;
	}

	public Ingredient getIngredient(String name) {
		return ingredientMap.get(name);
	}

	public Ingredient getIngredient(int index) {
		return ingredients.get(index);
	}

	public Recipe getRecipe(String name) {
		return recipeMap.get(name);
	}

	public Recipe getRecipe(int index) {
		return recipes.get(index);
	}

	public Scanner getScanner() {
		return scan;
	}

	public static Configuration configure(String recipeFilename, String ingredientFilename)
			throws IOException {
		Configuration config = new Configuration();
		config.loadIngredients(ingredientFilename);
		config.loadRecipes(recipeFilename);
		return config;
	}

}