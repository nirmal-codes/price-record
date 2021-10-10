package com.gcp.app.Exception;


public class BigQuerySearchException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BigQuerySearchException(final String message) {
        super(message);
    }

    public BigQuerySearchException(final String message, final Exception exception) {
        super(message, exception);
    }
}
