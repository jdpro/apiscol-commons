package fr.ac_versailles.crdp.apiscol.database;

import fr.ac_versailles.crdp.apiscol.ApiscolException;

public class InexistentResourceInDatabaseException extends ApiscolException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InexistentResourceInDatabaseException(String message) {
		super(message, false);
	}
}
