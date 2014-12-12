package pavlik.john;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ReadWriteListModel implements ListModel<Unit> {
	List<Unit> units = new ArrayList<>();
	List<ListDataListener> listeners = new ArrayList<>();

	@Override
	public int getSize() {
		return units.size();
	}

	@Override
	public Unit getElementAt(int index) {
		return units.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	public void addUnit(Unit unit) {
		units.add(unit);
		int newIndex = units.indexOf(unit);
		for (ListDataListener l : listeners) {
			l.intervalAdded(new ListDataEvent(this,
					ListDataEvent.INTERVAL_ADDED, newIndex, newIndex));
		}
	}

	public void removeUnit(Unit u) {
		int oldIndex = units.indexOf(u);
		units.remove(u);
		for (ListDataListener l : listeners) {
			l.intervalRemoved(new ListDataEvent(this,
					ListDataEvent.INTERVAL_REMOVED, oldIndex, oldIndex));
		}
	}

	public List<Unit> getUnits() {
		return units;
	}
}