package com.pushinginertia.commons.service.helper;

/**
 * Executes arbitrary logic when an entity is loaded.  For example, an exception can be thrown
 * if an entity is not found, or validation actions can be carried out when a matching entity is found.
 */
public abstract class LoadEntityAction<E> {
	/**
	 * Executed when an entity is found. This can be used to perform custom validation.
	 * @param entity entity found by the service
	 */
	public void onEntityFound(final E entity) {
	}

	/**
	 * Executed when no entity is found.
	 */
	public void onEntityNotFound() {
	}

	/**
	 * Called by the service loading an entity when the load operation completes. This method triggers the
	 * {@link #onEntityFound(Object)} and {@link #onEntityNotFound()} methods, depending on whether the entity
	 * passed in as input is null or not.
	 * @param entity entity loaded by the service or null
	 * @return the entity passed in
	 */
	public final E onLoadFinished(final E entity) {
		if (entity == null) {
			onEntityNotFound();
		} else {
			onEntityFound(entity);
		}
		return entity;
	}
}
