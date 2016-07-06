package personal.wade.test.socket;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import personal.wade.rsa.tools.Base64;

public class CallBackClient_Hele {

	public String[] myargs;

	public CallBackClient_Hele(String[] myargs) {
		//super();
		this.myargs = myargs;

	}
	public CallBackClient_Hele() {
		//super();
	}

	public static void main(String[] args) throws IOException {

		new CallBackClient_Hele().postOrderDeliver("O2016031510491737849999","00_O2016031510491737845101_44_1603159999");
		System.out.println();
		new CallBackClient_Hele().postOrderService("R22016031513591740822665", "WRE160315140101707");

	}

	public void postOrderService(String fParam1,String fParam2) throws IOException {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		try {
			
			// Hele回调时的请求
			// String sBody = {"data":{"spReturnId":"R22016031513591740822665","returnId":"WRE160315140101707","refundType":"0","returnStaus":"10","comment":"","returnAmount":"0.01","returnInfo":"","operateTime":"2016-03-15 14:07:06"},"encrypt":"DES","desSign":"pmMmX1kXVeFrI6038XXtW3HwhZZA42aows6KW88Ci4MLOZ/Inkn9xV/3jq79CbMnCjxve2iN/x2wZg9XrDA7gRHTfQC7TQjF5851xkVLFvMwRYTcclrJLd0QkZ7BC/bnVMxR5Kgg++UMAPn99UdE/+ecU4/Xp9Sj3RCRnsEL9ue07lPsdhnb9z5PwZgwMiah0MnpxDfzJiFCHmAW3Rks4hcWwUNsFSpNavXcbdjBBhSuq/rv5F2UtzESoAtZcduPjvLPMZrKqJc="}
			// String sBodySub = "{\"spReturnId\":\"R22016031513591740822665\",\"returnId\":\"WRE160315140101707\",\"refundType\":\"0\",\"returnStaus\":\"10\",\"comment\":\"\",\"returnAmount\":\"0.01\",\"returnInfo\":\"\",\"operateTime\":\"2016-03-15 14:07:06\"}";
			// 完成子串 sBodySub 的动态修改
			String sBodySub = "{\"spReturnId\":\"R22016031513591740822665\",\"returnId\":\"WRE160315140101707\",\"refundType\":\"0\",\"returnStaus\":\"10\",\"comment\":\"\",\"returnAmount\":\"0.01\",\"returnInfo\":\"\",\"operateTime\":\"2016-03-15 14:07:06\"}";
			//System.out.println("[BodySub Data]:\t\t" + sBodySub);
			JSONObject jBodySub = JSON.parseObject(sBodySub);
			Map<String, String> mBodySub = JSON.toJavaObject(jBodySub,Map.class);
//			mBodySub.put("spReturnId","R22016031513591740822665");
//			mBodySub.put("returnId", "WRE160315140101707");
			mBodySub.put("spReturnId",fParam1);
			mBodySub.put("returnId",fParam2);
			jBodySub = (JSONObject) JSON.toJSON(mBodySub);
			sBodySub = JSONObject.toJSONString(jBodySub);
			Process.log.info("[BodySub Data]:\t\t" + sBodySub);

			// 完成子串的摘要生成（其实是DES）
			// String BodyMiWen = "{\"deliveryInfo\":\"\",\"deliverySn\":\"122222222222222\",\"deliveryTime\":\"2016-03-15 13:58:22\",\"expressCompanyId\":\"7\",\"expressCompanyMark\":\"yunda\",\"expressCompanyName\":\"韵达快递\",\"expressCompanyType\":\"\",\"heleOrderCode\":\"00_O2016031510491737845101_44_1603151054\",\"operationType\":\"deliver\",\"orderCode\":\"O2016031510491737845101\"}";
			String password = "yiyatong";
			byte[] BodyMiWen_Byte = encrypt(sBodySub.getBytes(), password);
			sBodySub = Base64.encode(BodyMiWen_Byte);
			Process.log.info("[DES Encrypted]:\t" + sBodySub);

			// 完成母串 sBody
			String sBody = null;
			JSONObject jBody = new JSONObject();

			jBody.put("data", jBodySub);
			jBody.put("encrypt", "DES");
			jBody.put("desSign", sBodySub);
			sBody = JSONObject.toJSONString(jBody);
			Process.log.info("[Request Body]:\t\t" + sBody);

			// 完成eBody,并执行
			StringEntity eBody = new StringEntity(sBody, "utf-8");
			// 创建默认的httpClient实例.
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://172.30.2.27:8001/trade/return/returnStatusAudit.do");
			httppost.setEntity(eBody);
			Process.log.info("[Request Url]:\t\t" + httppost.getURI());
			response = httpclient.execute(httppost);


			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String s2 = EntityUtils.toString(entity, "UTF-8");
				Process.log.info("[Response content]:\t" + s2);
			}

			response.close();
			httpclient.close();

		} catch (IOException e) {
			response.close();
			httpclient.close();
			e.printStackTrace();

		}

	}
	public void postOrderDeliver(String fparam1,String fparam2) throws IOException {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		try {

			// Hele回调时的请求
			// String sBody = "{\"data\":{\"deliveryInfo\":\"\",\"deliverySn\":\"122222222222222\",\"deliveryTime\":\"2016-03-15 13:58:22\",\"expressCompanyId\":\"7\",\"expressCompanyMark\":\"yunda\",\"expressCompanyName\":\"韵达快递\",\"expressCompanyType\":\"\",\"heleOrderCode\":\"00_O2016031510491737845101_44_1603151054\",\"operationType\":\"deliver\",\"orderCode\":\"O2016031510491737845101\"},\"encrypt\":\"DES\"}";

			// 完成子串 sBodySub 的动态修改
			String sBodySub = "{\"deliveryInfo\":\"\",\"deliverySn\":\"122222222222222\",\"deliveryTime\":\"2016-03-15 13:58:22\",\"expressCompanyId\":\"7\",\"expressCompanyMark\":\"yunda\",\"expressCompanyName\":\"韵达快递\",\"expressCompanyType\":\"\",\"heleOrderCode\":\"00_O2016031510491737845101_44_1603151054\",\"operationType\":\"deliver\",\"orderCode\":\"O2016031510491737845101\"}";
			JSONObject jBodySub = JSON.parseObject(sBodySub);
			Map<String, String> mBodySub = JSON.toJavaObject(jBodySub,Map.class);
//			mBodySub.put("heleOrderCode","00_O2016031510491737845101_44_1603159999");
//			mBodySub.put("orderCode", "O2016031510491737849999");
			mBodySub.put("heleOrderCode",fparam2);
			mBodySub.put("orderCode", fparam1);
			jBodySub = (JSONObject) JSON.toJSON(mBodySub);
			sBodySub = JSONObject.toJSONString(jBodySub);
			//System.out.println("[BodySub Data]:\t\t" + sBodySub);
			Process.log.info("BodySub Data:\t" + sBodySub);
			// 完成子串的摘要生成（其实是DES）
			// String BodyMiWen = "{\"deliveryInfo\":\"\",\"deliverySn\":\"122222222222222\",\"deliveryTime\":\"2016-03-15 13:58:22\",\"expressCompanyId\":\"7\",\"expressCompanyMark\":\"yunda\",\"expressCompanyName\":\"韵达快递\",\"expressCompanyType\":\"\",\"heleOrderCode\":\"00_O2016031510491737845101_44_1603151054\",\"operationType\":\"deliver\",\"orderCode\":\"O2016031510491737845101\"}";
			String password = "yiyatong";
			byte[] BodyMiWen_Byte = encrypt(sBodySub.getBytes(), password);
			sBodySub = Base64.encode(BodyMiWen_Byte);
			//System.out.println("[DES Encrypted]:\t" + sBodySub);
			Process.log.info("DES Encrypted:\t" + sBodySub);
			// 完成母串 sBody
			String sBody = null;
			JSONObject jBody = new JSONObject();
			jBody.put("encrypt", "DES");
			jBody.put("desSign", sBodySub);
			jBody.put("data", jBodySub);
			sBody = JSONObject.toJSONString(jBody);
			//System.out.println("[Request Body]:\t\t" + sBody);
			Process.log.info("Request Body:\t" + sBody);
			// 完成eBody,并执行
			StringEntity eBody = new StringEntity(sBody, "utf-8");
			// 创建默认的httpClient实例.
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://172.30.2.27:8001/trade/order/orderDeliver.do");
			//System.out.println("[Request Url]:\t\t" + httppost.getURI());
			Process.log.info("Request Url:\t" + httppost.getURI());
			httppost.setEntity(eBody);		
			response = httpclient.execute(httppost);


			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String s2 = EntityUtils.toString(entity, "UTF-8");
				//System.out.println("[Response Body]:\t" + s2);
				Process.log.info("Response Body:\t" + s2);
			}

			response.close();
			httpclient.close();

		} catch (IOException e) {
			response.close();
			httpclient.close();
			e.printStackTrace();

		}

	}

	public static byte[] encrypt(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}

}

/*
 * 调用Hele接口的Data的RSA加密
 * 
 * // 明文的 BodyMiWen =
 * "{\"heleOrderCode\":\"00_O2015111216221244547149_54_1511129999\",\"orderCode\":\"O2015111216221244549999\",\"receiverName\":\"你是我的\",\"receiverTime\":\"2015-11-12 17:51:49\"}"
 * ;
 * 
 * 
 * System.out.println("二、第三方系统使用私钥加密的例子");
 * System.out.println("1.第三方系统使用私钥加密前的原文："); System.out.println(BodyMiWen);
 * 
 * System.out.println("2.第三方系统使用私钥加密后的文字："); byte[] data = BodyMiWen.getBytes();
 * byte[] encodedData = RSAPrivate.encryptByPrivateKey(data,
 * RSAPrivate.getPrivateKey(APIConstants.PRIVATE_KEY)); //密文的 BodyMiWen =
 * URLEncoder.encode(Base64.encode(encodedData), "UTF-8");
 * System.out.println(BodyMiWen); System.out.println(); System.out.println();
 */