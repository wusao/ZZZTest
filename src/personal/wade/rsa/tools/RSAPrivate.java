package personal.wade.rsa.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import personal.wade.rsa.help.StringUtils;


/**
 * �ļ�����: RSAPrivate.java
 * ����: RSAPrivate
 * ����: 2015��8��27��
 *
 * ����������RSA˽Կ���߰�
 * �ַ�����ʽ����Կ��δ������˵������¶�ΪBASE64�����ʽ<br/>
 */
public class RSAPrivate {

	/** �����㷨RSA */
	public static final String KEY_ALGORITHM = "RSA";

	/** ǩ���㷨 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/** RSA���������Ĵ�С */
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_ENCRYPT_BLOCK_public = 128;
	/** RSA���������Ĵ�С */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * ��������: getPrivateKey
	 * ��������: ��ȡ˽Կ
	 *
	 * @param sysEnum
	 * @return String
	 */
	public static String getPrivateKey(final String privateKeyStr) {
		if(StringUtils.isBlank(privateKeyStr)) {
			throw new RuntimeException("ϵͳ��Ӧ��˽ԿΪ��");
		}
		return privateKeyStr;
	}
	
	public static String getPublicKey(final String publicKeyStr) {
		if(StringUtils.isBlank(publicKeyStr)) {
			throw new RuntimeException("ϵͳ��Ӧ�Ĺ�ԿΪ��");
		}
		return publicKeyStr;
	}

	public static byte[] encryptByPublicKey(final byte[] data, final String publicKey) {
		try {
			//System.out.println("key Paramter: "+new String(publicKey));
			byte[] keyBytes = Base64.decode(publicKey);
			//PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);
			
			//System.out.println("keyBytes: "+new String(keyBytes));
			
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			
			//Key publicK = keyFactory.generatePrivate(pkcs8KeySpec);
			Key publicK = keyFactory.generatePublic(pkcs8KeySpec);
			
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			
			//System.out.println("key: "+new String(publicK.getEncoded()));
			
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			
			while (inputLen - offSet > 0) {
				if(inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ����޴˼����㷨");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ��̼���˽Կ�Ƿ�");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ��̼���˽Կ�Ƿ�");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ������ĳ��ȷǷ�");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ���������������");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ���ʧ��");
		}
	}
	
	public static byte[] decryptByPublicKey(final byte[] encryptedData, final String publicKey) {
		try {
			
//			byte[] keyBytes = Base64.decode(publicKey);
//		
//			X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);
//			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//			Key publicK = keyFactory.generatePublic(pkcs8KeySpec);			
//			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			
			
			
			byte[] keyBytes = Base64.decode(publicKey);
			
			//PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);
			
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicK = keyFactory.generatePublic(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if(inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ����޴��㷨");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ��̽���˽Կ�Ƿ�");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ��̽���˽Կ�Ƿ�");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ������ĳ��ȷǷ�");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ���������������");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("��Կ���ܹ���ʧ��");
		}
	}

	
	/**
	 * ��������: encryptByPrivateKey
	 * ��������: ˽Կ���ܹ���
	 * <p>
	 * ˽Կ����
	 * </p>
	 *
	 * @param data Դ����
	 * @param privateKey ˽Կ(BASE64����)
	 * @return byte[] ����
	 */
	public static byte[] encryptByPrivateKey(final byte[] data, final String privateKey) {
		try {
			byte[] keyBytes = Base64.decode(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if(inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ����޴˼����㷨");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ��̼���˽Կ�Ƿ�");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ��̼���˽Կ�Ƿ�");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ������ĳ��ȷǷ�");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ���������������");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ���ʧ��");
		}
	}

	/**
	 * ��������: decryptByPrivateKey
	 * ��������: ˽Կ���ܹ���
	 * <P>
	 * ˽Կ����
	 * </p>
	 *
	 * @param encryptedData �Ѽ�������
	 * @param privateKey ˽Կ(BASE64����)
	 * @return
	 * byte[]
	 */
	public static byte[] decryptByPrivateKey(final byte[] encryptedData, final String privateKey) {
		try {
			byte[] keyBytes = Base64.decode(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if(inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ����޴��㷨");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ��̽���˽Կ�Ƿ�");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ��̽���˽Կ�Ƿ�");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ������ĳ��ȷǷ�");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ���������������");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կ���ܹ���ʧ��");
		}
	}

	/**
	 * ��������: sign
	 * ��������: 
	 * <p>
	 * ��˽Կ����Ϣ��������ǩ��
	 * </p>
	 *
	 * @param data �Ѽ�������
	 * @param privateKey ˽Կ(BASE64����)
	 * @return String ǩ��ֵ
	 */
	public static String sign(final byte[] data, final String privateKey) {
		try {
			byte[] keyBytes = Base64.decode(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateK);
			signature.update(data);
			return Base64.encode(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("˽Կǩ���쳣");
		}
	}
}
