package com.pushinginertia.commons.domain.base;

import java.io.Serializable;

/**
 * Base container object.
 */
public abstract class BaseDCO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Performs any necessary business logic to prepare the object before it is created in the database.
	 */
	public void onBeforeCreate() {
	}

	/**
	 * Performs any necessary business logic to prepare the object before it is updated in the database.
	 */
	public void onBeforeUpdate() {
	}
}
