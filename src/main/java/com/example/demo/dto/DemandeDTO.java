package com.example.demo.dto;

public class DemandeDTO {

	private String cle;

	public String getCle() {
		return cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}

	@Override
	public String toString() {
		return "DemandeDTO{" +
				"cle='" + cle + '\'' +
				'}';
	}
}
