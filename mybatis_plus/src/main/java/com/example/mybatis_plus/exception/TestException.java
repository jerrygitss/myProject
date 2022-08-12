package com.example.mybatis_plus.exception;

public class TestException extends RuntimeException {

	private static final long serialVersionUID = -7285211528095468156L;

	public TestException() {
	}

	public TestException(String msg) {
		super(msg);
	}
}