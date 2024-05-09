package br.com.digio.api.utils;

import br.com.digio.api.exception.ApiException;

public class AssertUtils {

	public static void assertTrue(Boolean bool, ApiException e) {
		if(!bool)
			throw e;
	}
}
