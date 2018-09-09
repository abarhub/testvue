package com.example.demo.dto;

public class Message2 {

	private String key;
	private String iv;
	private int id;
	private String message;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	@Override
	public String toString() {
		return "Message2{" +
				"key='" + key + '\'' +
				", iv='" + iv + '\'' +
				", id=" + id +
				", message='" + message + '\'' +
				'}';
	}
}
