package org.elevenfifty.smoothie;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elevenfifty.io.CSVReader;
import org.elevenfifty.smoothie.beans.Ingredient;
import org.elevenfifty.smoothie.beans.Ingredient.Type;
import org.elevenfifty.smoothie.beans.Produce;
import org.elevenfifty.smoothie.beans.Recipe;

public class Configuration {

	Map<String, Ingredient> ingredients = new HashMap<>();
	Map<String, Recipe> recipes = new HashMap<>();

	private Configuration() {
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
				List<Ingredient> ingredients = new ArrayList<>(row.length - 1);
				for (int i = 1; i < row.length; i++) {
					ingredients.add(getIngredient(row[i]));
				}
				recipe.setIngredients(ingredients);
				recipes.put(recipe.getName(), recipe);
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
				ingredient.setCost(Double.valueOf(row.get("Cost")));
				ingredient.setType(Type.valueOf(row.get("Type")));
				ingredients.put(ingredient.getName(), ingredient);
			}
		}
	}

	public Collection<Ingredient> listIngredients() {
		return ingredients.values();
	}

	public Collection<Recipe> listRecipes() {
		return recipes.values();
	}

	public Ingredient getIngredient(String name) {
		return ingredients.get(name);
	}

	public Recipe getRecipe(String name) {
		return recipes.get(name);
	}

	public static Configuration configure(String recipeFilename, String ingredientFilename)
			throws IOException {
		Configuration config = new Configuration();
		config.loadIngredients(ingredientFilename);
		config.loadRecipes(recipeFilename);
		return config;
	}

}
