package pl.maro.analise.model;

import java.util.HashMap;

public class Sum {
	private HashMap<Range, Integer> map;
	
	public Sum() {
		this.map = new HashMap<>();
	}
	
	
	public void increment(Range range) {
		var newValue = this.map.getOrDefault(range, 0) + 1;
		this.map.put(range, newValue);
	}
	
	public int get(Range range) {
		return this.map.get(range);
	}
}
