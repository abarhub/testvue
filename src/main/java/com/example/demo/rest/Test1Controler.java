package com.example.demo.rest;

import com.example.demo.dto.DemandeDTO;
import com.example.demo.dto.Message;
import com.example.demo.dto.ReponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Test1Controler {

	private static Logger LOGGER = LoggerFactory.getLogger(Test1Controler.class);

	@RequestMapping("/entity/all")
	public List<String> findAll() {
		return new ArrayList<>();
	}

	@RequestMapping("/test1")
	public Message test1() {
		Message message = new Message();
		message.setMessage("Test");
		return message;
	}

	@RequestMapping(value = "/test2", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json")
	public ReponseDTO test2(DemandeDTO demandeDTO) {
		ReponseDTO reponseDTO = new ReponseDTO();
		try {

			LOGGER.info("demande={}", demandeDTO);

			reponseDTO.setReponse("coucou");

			byte[] keyBytes = demandeDTO.getCle().getBytes(StandardCharsets.UTF_8);

			X509EncodedKeySpec spec1 = new X509EncodedKeySpec(keyBytes);
			KeyFactory kf1 = KeyFactory.getInstance("RSA");
			RSAPublicKey pubKey = (RSAPublicKey) kf1.generatePublic(spec1);

			LOGGER.info("Exponent : {}", pubKey.getPublicExponent());
			LOGGER.info("Modulus : {}", pubKey.getModulus());

		} catch (Exception e) {
			LOGGER.error("Erreur", e);
		}

		return reponseDTO;
	}
}
