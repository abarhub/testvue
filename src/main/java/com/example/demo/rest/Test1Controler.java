package com.example.demo.rest;

import com.example.demo.dto.DemandeDTO;
import com.example.demo.dto.Message;
import com.example.demo.dto.ReponseDTO;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
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
	public ReponseDTO test2(@RequestBody DemandeDTO demandeDTO) {
		ReponseDTO reponseDTO = new ReponseDTO();
		try {

			LOGGER.info("demande={}", demandeDTO);

			reponseDTO.setReponse("coucou");

			byte[] keyBytes = demandeDTO.getCle().getBytes(StandardCharsets.UTF_8);

			String s=demandeDTO.getCle();

			Security.addProvider(new BouncyCastleProvider());
			LOGGER.info("BouncyCastle provider added.");

			KeyFactory factory = KeyFactory.getInstance("RSA", "BC");

			PemObject pemReader2 = getPemObject(s);

			RSAPublicKey pub = (RSAPublicKey)generatePublicKey(factory, s);

//			byte[] content = pemReader2.getContent();
//			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
//
//			X509EncodedKeySpec spec1 = new X509EncodedKeySpec(keyBytes);
//			KeyFactory kf1 = KeyFactory.getInstance("RSA");
//			RSAPublicKey pubKey = (RSAPublicKey) kf1.generatePublic(spec1);

			LOGGER.info("Exponent : {}", pub.getPublicExponent());
			LOGGER.info("Modulus : {}", pub.getModulus());

		} catch (Exception e) {
			LOGGER.error("Erreur", e);
		}

		return reponseDTO;
	}

	private PemObject getPemObject(String s) throws IOException {
		PemObject pemReader2=null;
		PemReader pemReader = new PemReader(new StringReader(s));
		try {
			pemReader2 = pemReader.readPemObject();
		} finally {
			pemReader.close();
		}
		return pemReader2;
	}


	private PublicKey generatePublicKey(KeyFactory factory,
	                                           String s) throws InvalidKeySpecException,
			FileNotFoundException, IOException {
		//PemFile pemFile = new PemFile(filename);
		byte[] content = getPemObject(s).getContent();
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
		return factory.generatePublic(pubKeySpec);
	}
}
