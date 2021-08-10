package br.com.live.util;

import org.apache.tomcat.util.codec.binary.Base64;

public class Criptografia {

	/**
	 * Codifica string na base 64 (Encoder)
	 */
	public static String criptografar(String senha) {
		return new Base64().encodeToString(senha.getBytes());
	}

	/**
	 * Decodifica string na base 64 (Decoder)
	 */
	public static String descriptografar(String senha) {
		return new String(new Base64().decode(senha));
	}

}
