package com.classes;

public class Factory {
	public static Sistema getSistema() {
		return Sistema.getInstance();
	}
}
