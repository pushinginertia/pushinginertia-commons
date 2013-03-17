package com.pushinginertia.commons.service.helper;

import javax.persistence.EntityNotFoundException;

/**
 * Fails with a {@link EntityNotFoundException} exception when an entity is not found.
 */
public class AssertEntityFoundAction<E> extends LoadEntityAction<E> {
	public void onEntityNotFound() {
		throw new EntityNotFoundException();
	}
}
