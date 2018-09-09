package com.example.demo.rest;

import com.example.demo.dto.DemandeDTO;
import com.example.demo.dto.Message;
import com.example.demo.dto.ReponseDTO;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
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

			String s = demandeDTO.getCle();

			Security.addProvider(new BouncyCastleProvider());
			LOGGER.info("BouncyCastle provider added.");

			KeyFactory factory = KeyFactory.getInstance("RSA", "BC");

			PemObject pemReader2 = getPemObject(s);

			RSAPublicKey pub = (RSAPublicKey) generatePublicKey(factory, s);

//			byte[] content = pemReader2.getContent();
//			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
//
//			X509EncodedKeySpec spec1 = new X509EncodedKeySpec(keyBytes);
//			KeyFactory kf1 = KeyFactory.getInstance("RSA");
//			RSAPublicKey pubKey = (RSAPublicKey) kf1.generatePublic(spec1);

			LOGGER.info("Exponent : {}", pub.getPublicExponent());
			LOGGER.info("Modulus : {}", pub.getModulus());

			String message = "message0-" + System.currentTimeMillis();

			byte[] crypte = encrypt(pub, message.getBytes(StandardCharsets.UTF_8));

			reponseDTO.setReponse(Base64.toBase64String(crypte));

		} catch (Exception e) {
			LOGGER.error("Erreur", e);
		}

		return reponseDTO;
	}

	@RequestMapping(value = "/test3", method = RequestMethod.POST,
			produces = "application/json", consumes = "application/json")
	public ReponseDTO test3(@RequestBody DemandeDTO demandeDTO) {
		ReponseDTO reponseDTO = new ReponseDTO();
		try {

			LOGGER.info("demande={}", demandeDTO);

			reponseDTO.setReponse("coucou");

			byte[] keyBytes = demandeDTO.getCle().getBytes(StandardCharsets.UTF_8);

			String s = demandeDTO.getCle();

			Security.addProvider(new BouncyCastleProvider());
			LOGGER.info("BouncyCastle provider added.");

			KeyFactory factory = KeyFactory.getInstance("RSA", "BC");

			PemObject pemReader2 = getPemObject(s);

			RSAPublicKey pub = (RSAPublicKey) generatePublicKey(factory, s);

//			byte[] content = pemReader2.getContent();
//			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
//
//			X509EncodedKeySpec spec1 = new X509EncodedKeySpec(keyBytes);
//			KeyFactory kf1 = KeyFactory.getInstance("RSA");
//			RSAPublicKey pubKey = (RSAPublicKey) kf1.generatePublic(spec1);

			LOGGER.info("Exponent : {}", pub.getPublicExponent());
			LOGGER.info("Modulus : {}", pub.getModulus());

			String message = "message0-" + System.currentTimeMillis();

			KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
			keyGen.init(128);
			SecretKey secretKey = keyGen.generateKey();
			//secretKey=new SecretKeySpec("1234000000000000".getBytes(StandardCharsets.UTF_8),"EAS");

			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");

			SecureRandom randomSecureRandom = new SecureRandom();
			byte[] iv = new byte[aesCipher.getBlockSize()];
			randomSecureRandom.nextBytes(iv);
			IvParameterSpec ivParams = new IvParameterSpec(iv);

			reponseDTO.setIv(base64(iv));

			aesCipher.init(Cipher.ENCRYPT_MODE,secretKey,ivParams);

			byte[] byteCipherText = aesCipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

			//byte[] crypte = encrypt(pub, message.getBytes(StandardCharsets.UTF_8));

			//reponseDTO.setReponse(Base64.toBase64String(crypte));

			reponseDTO.setReponse(base64(byteCipherText));

			byte[] crypte = encrypt(pub, secretKey.getEncoded());

			reponseDTO.setCle(base64(crypte));

		} catch (Exception e) {
			LOGGER.error("Erreur", e);
		}

		return reponseDTO;
	}

	public String base64(byte[] buf){
		return Base64.toBase64String(buf);
	}

	public byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(plaintext);
	}

	public byte[] encrypt2(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher oaepFromInit = Cipher.getInstance("RSA/ECB/OAEPPadding");
		OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT);
		oaepFromInit.init(Cipher.ENCRYPT_MODE, key, oaepParams);
		return oaepFromInit.doFinal(plaintext);

//		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
//		cipher.init(Cipher.ENCRYPT_MODE, key);
//		return cipher.doFinal(plaintext);
	}

	public byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(ciphertext);
	}

	private PemObject getPemObject(String s) throws IOException {
		PemObject pemReader2 = null;
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
