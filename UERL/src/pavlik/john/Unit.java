package pavlik.john;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Unit {
	Map<String, Item> inventory;
	String name, uic;

	public Unit(String name, String uic) {
		inventory = new TreeMap<>();
		this.name = name;
		this.uic = uic;
	}

	public Unit() {
		inventory = new TreeMap<>();
	}

	public void addInventory(Item item) {
		inventory.put(item.lin, item);
	}

	public void addInventory(List<Item> parseInventory) {
		for (Item i : parseInventory)
			inventory.put(i.lin, i);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return uic + " - " + name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the uic
	 */
	public String getUic() {
		return uic;
	}

	/**
	 * @param uic
	 *            the uic to set
	 */
	public void setUic(String uic) {
		this.uic = uic;
	}

	public Map<String, Item> getInventory() {
		return inventory;
	}

}
