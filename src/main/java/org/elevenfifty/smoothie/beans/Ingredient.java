package org.elevenfifty.smoothie.beans;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
	
	public static enum Type { FRUIT, VEGETABLE, BASE, MISC, ADDON };

	protected String name;
	protected Type type;
	protected double cost = 0d;
	protected int qty = 0;
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Ingredient)){
			return false;
		}
		
		return ((Ingredient) obj).name.equalsIgnoreCase(this.name);
	}

	@Override
	public int hashCode() {
		return this.name.toLowerCase().hashCode();
	}
	
	public List<String> getInstructions(){
		List<String> instructions = new ArrayList<String>();
		instructions.add("Add " + this.type + ":" + this.name);
		return instructions;
	}

	@Override
	public String toString() {
		return String.format("%s %s (%d on hand)", type, name, qty);	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
}
