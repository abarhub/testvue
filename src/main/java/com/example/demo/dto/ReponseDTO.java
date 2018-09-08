package com.example.demo.dto;

public class ReponseDTO {

	private String reponse;

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	@Override
	public String toString() {
		return "ReponseDTO{" +
				"reponse='" + reponse + '\'' +
				'}';
	}
}
