package com.pushinginertia.commons.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Logic that manipulates Java lists.
 */
public class ListUtils {
	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
		final List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}
}
