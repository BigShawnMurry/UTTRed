package com.ovation.utt.domain;

import java.beans.PropertyChangeSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ovation.uttdb.UTTDBManager;


public abstract class UTTAbstractModel<U> {
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);
	protected List<U> getObjects(String query) {
		ResultSet rs = UTTDBManager.executeQuery(query);
		return translate(rs);
	}
	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}
private List<U> translate(ResultSet rs) {
	List<U> objects = new ArrayList<U>();
	if (rs != null) {
		try {
			while (rs.next()) {
				U o = map(rs);
				objects.add(o);
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	return objects;
}
protected abstract U map(ResultSet rs) throws SQLException;
}
