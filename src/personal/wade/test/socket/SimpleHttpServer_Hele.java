package personal.wade.test.socket;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import personal.wade.rsa.help.APIConstants;
import personal.wade.rsa.tools.Base64;
import personal.wade.rsa.tools.RSAPrivate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SimpleHttpServer_Hele {

	ServerSocket serverSocket;// ������Socket
	ExecutorService pool = Executors.newFixedThreadPool(2);
	public static int PORT = 80;// ��׼HTTP�˿�

	public SimpleHttpServer_Hele() throws IOException {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (Exception e) {
			System.out.println("�޷�����HTTP������:" + e.getMessage());
		}
		if (serverSocket == null)	System.exit(1);// �޷���ʼ������

		System.out.println("HTTP��������������,�˿�:" + PORT);
		System.out.println();
				
		while (true) {
	        Socket cs = serverSocket.accept();
	        pool.execute(new Process(cs));   
		}
		
		
	}


	public static void main(String[] args) throws IOException {
		try {
			if (args.length != 1) {
				System.out.println("����һ���򵥵�web������ ���˿��ǣ� 80.");
			} else if (args.length == 1) {
				PORT = Integer.parseInt(args[0]);
			}
		} catch (Exception ex) {
			System.err.println("��������ʼ������" + ex.getMessage());
		}

		new SimpleHttpServer_Hele();
	}
}

class MyLogHander extends Formatter {
    @Override
    public String format(LogRecord record) {
            return record.getMessage()+"\r\n";
    }
} 



class Process extends Thread {
	
	private Socket socket;
	static Logger log = Logger.getLogger("EA");
	static 
	{
		
		log.setUseParentHandlers(false);
        
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new MyLogHander());
        log.addHandler(consoleHandler);

        
        FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("./testlog%g.log");
	        fileHandler.setFormatter(new MyLogHander());
	        log.addHandler(fileHandler);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	Process(Socket s) throws SecurityException, IOException {
		//System.out.println("thread create");
		this.socket = s;

	}
	
	
	
	
	public void run() {
		try {
			log.info("");
			log.info("["+new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS").format( new Date() )+"]" );
			Socket client = socket;

			log.info("Accepted Client:" + client);

			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String line = in.readLine();
			log.info("Request HEAD��\t"+line);
			
			String resource = line.substring(line.indexOf('/'),	line.lastIndexOf('/') - 5);
			resource = URLDecoder.decode(resource, "gbk");// ������

			String method = new StringTokenizer(line).nextElement().toString();// ��ȡ���󷽷�, GET ���� POST

			
			if ("HEAD".equalsIgnoreCase(method))
			{
				// ��ȡ��������͹������������ͷ����Ϣ
				while ((line = in.readLine()) != null) {
					if (line.equals(""))
						break;
				}
				resource = "/web/head.html";
			}// end of HEAD
			
			if ("POST".equalsIgnoreCase(method))
			{
				// ��ȡ��������͹������������ͷ����Ϣ
				String params = null;
				char[] params_c = new char[2048];
				//����Head,ֱ��Body���Կ���Ϊ�ֽ�
				while ((line = in.readLine()) != null) {
					//System.out.println(line);//��ӡHeadÿ�е�����
					if (line.equals(""))
						break;
				}
				//��ȡBody
				int plen = in.read(params_c);
				params = new String(params_c,0,plen);
				

				log.info("Request BODY:\t"+params);
				
				
				JSONObject  dataJson = JSONObject.parseObject(params);
				String requestData = dataJson.getString("data");
				String requestLogic = dataJson.getString("logic");
//				System.out.println("3.Heleϵͳʹ�ù�Կ����ǰ��ԭ���֣�");
//				System.out.println(requestData);
				
				//log.info("Heleϵͳʹ�ù�Կ���ܺ�����֣�");
				byte[] decodedData =
						RSAPrivate.decryptByPublicKey(Base64.decode(URLDecoder.decode(requestData, "UTF-8")),
							RSAPrivate.getPublicKey(APIConstants.PUBLIC_KEY));
				String source = new String(decodedData);
				log.info("Reqest Body����:\t"+source);
				
				//��Source���ȡorderCode
				//Start of Data   data responseBody dataJson value dataJson dataBody data
				//String responseBody = new String(data);
				//log.info("Response Body1��\t"+responseBody);
				
				
				
				String  p_orderCode;
				String  p_heleOrderCode;
				String 	p_spReturnId;
				String  p_returnId;
				
				switch(requestLogic)
				{
				case "order":
					//����Body��{"data":"DwZTSrDlahdetGgohklCEqQHDkGRD%2B9toCJKeYqSgckDh7NGYi8w9asRXPr8vttSwAlff4lyYHqYph2uZG%2BPWURcE7EkbQWSs%2BPfxSX8GYO%2FAUqumV3iYGR7l%2FPvE7IR6Omfo%2B533UDiGyq78hmd7E2dDPdMXRtKYRVPNul4o7wC%2FvGVnVSvcMtpgGTIR9JJPu%2F5UFVNHIq%2FvPj7pa2vO3jKpc1KVoTHmB92DbSKp35DXpqboDPUVhf%2BUBQDEWFJBlslroBySUlIW40yuszQo%2Bbw%2BUzLNwdi7ox1IeX5A6mx1bLNbg%2BtQzGI9PQLNgIKH33Xs5aoj1XtuIeDLeZFSzwGr9jU8c8lZzGqY4iSAJiVgaQsXYbbI3LWwllFRyvS1MQlwGyVotYk%2FggQ4eQvW%2BlgzmIQ68RBiZiYa8wZWC2UAhddKS7dx2TTZpnMNPLSNXGGQN5AKMmsKFcMDaF2PY36sgLVMOjmnjkoLcJRCTNA3WaTGdfzorgZ2BTRR8PkPIq81hQlR6n6QCDlaHyrkZftVumJNlPL4oCoPluj9OWjJ8z0U5H%2FGlXHmEDAL2zmI2ylS%2FNp0AoeDtXtPvyaESQV%2FlWDK%2Bst7Z77cioY1i8OzyyyH8syGBYtr3F8MySz58EEP8p91IHwp%2BLbMsTKO8mMkyx0YXbDxdwSRfnafDCKeHMWXDUHsHqOGcozJ9FyGbM2z%2FmrV2v3J6BoqxfPeVWdDXbOzKFtZf6fjlc9hoe7IB74cXu%2FUcxS4aMXbAMgBnszv7cREOKAAqsw4bJRJ7XU44uMAtAT1wg78brSuth52JwrwZ80nSk97iSCOV5pQuB88tGbQqk2m%2BSD1vATi05bYu5NOLYNqP7YZNu69xZ35slJDC0IVLdI0a8OnhVaM9e17L%2FD5cvDTdOufHLMi6VaHg6YFVH20FBVbc0ggNHMI%2BCi1brkDZRZvT0o1xYTXiuUePtrHPL3WZwBsmCIiM6Ug37huln95yvsxypdDnJZhn%2FfJpf4FuvA7C8rjZ%2FAKcsUhzAHWC7sR3NGAy8XBlPTYXjC9ocagrqTymU%2FQUxOXafY%2FVv5cUx5hL3dn20wqV34lV2ZACE%2BcdUlTk5yLuSnqdfkciCPRgf57OazADFWjUx%2B2rVsRUmF5PmokZ%2FMT2lN2HAb8iatbRzS5ArYMf8bSt5H3H90NoBzt5EXZrc%3D","encrypt":"RSA","logic":"order","rsaSign":"g%2FNC0LjUcvFalz1iyToc2qLzauy2gW2%2FRex4e1iCltXlkg8x60t50nolZQnbAOpAXgWeJ%2B%2BuFm8JhePwDKfkolZK8EML4U77PCB6ojPGDOqRxD%2FqTzmW06ZeLdWvAiRLJ4zZpCfiU5x%2BSXJJZXvjZyYrzzProfoMCPhRMG8ah58%3D","sys":"1"}
					//����Data��[{"deliveryTime":"","deliveryType":"0","goods":[{"goodsId":"14336","goodsMainPath":"http://img.365hele.com/upload/data/16510_1_.jpg_middle.jpg","goodsName":"���������Ӥ��ֽ����к�(M)64Ƭ","goodsPrice":"0.01","goodsQty":"1","skuId":"","skuName":""}],"goodsAmount":"0.01","invoiceTitle":"","invoiceType":"2","orderCode":"O2015111217024427429025","orderType":"ms","outOrderId":"1008290720201511121572032455","receiverArea":"������","receiverAreaCode":"4521986","receiverAreaInfo":"��һ���","receiverMobile":"13528419073","receiverName":"�����ҵ�","receiverTelephone":"","receiverZip":"","remark":"","shipPrice":"0.00","storeId":"54","storeName":"","totalAmount":"0.01","tradeNo":"2015111217024427451510","transport":"�̼ҳе�[0.00Ԫ]"}]
					//��ӦData��{"code":"1","msg":"","result":[{"orderCode":"O2015111217024427429025","heleOrderCode":"00_O2015111217024427429025_54_1511121755"}]}
					JSONArray  sourceJson_order =(JSONArray) JSONArray.parse(source);
					p_orderCode = sourceJson_order.getJSONObject(0).getString("orderCode");
					p_heleOrderCode = response_Order(client,p_orderCode);
					
					
					
					log.info("\nResponse to TransactionCenter &����  Hele �ص���������ͬ�������ɹ���������������\n");
					//ʹ��CallBackClient���ص�  ����ͬ����Ϣ �� ��ʵ��  ���׷������� 
					//new CallBackClient_Hele().postOrderDeliver("O2016031510491737849999","00_O2016031510491737845101_44_1603159999");
					new CallBackClient_Hele().postOrderDeliver(p_orderCode,p_heleOrderCode);
					
					
					
					
					break;
				case "orderReceipt":
					//����Body��{"data":"tX6rTcSVGgm1Wm%2Be8ah87fP02%2FF1Q%2Btq6B%2B0GpdSoYLVFG9QoBwreePqxXPPg8IPggOJjCciVm5nSCBQQn2imNDiWjIn3Jbt8rqzQyfWc6zmsGcZYXYf865DQaabMNSCv3nlPN4lC%2F%2BVg808pgES7Su7HrMETXSbcOK980a2sINuMDlw9GwQLCkU%2FiNGjSAd4nHPOcxdIaRyU9syBnMxSwkkGqixDURFcFntKRQRe2cl3MDdzTQ2L%2BzwvQC2xgHyqdVaTw4GXqLeWK9h6fr1ATRvlrs5zEBg4YFOQkDfy27CnHPJJz98VNIPyuDJ%2F6PPFDX8OWC5GgjU2ITICPaC6A%3D%3D","encrypt":"RSA","logic":"orderReceipt","rsaSign":"l8O1%2BZcuQkAzMZ1QtZ35Q4D5v3vS0%2B1qRws%2BUhDqEpo7o%2Fe7VNllc%2FKwSacInhUozZuFy8OrxHGzZcN5Vahz6GQloSZRRoxfdSqjau2p8q0tTFVpKrLqFWvHz1D2TmOVC%2FL96GnpTBArh8nEK%2BF468qyxUTZaJ%2BdXl4bGJZYSDI%3D","sys":"1"}
		            //       "{\"data\":\"tX6rTcSVGgm1Wm%2Be8ah87fP02%2FF1Q%2Btq6B%2B0GpdSoYLVFG9QoBwreePqxXPPg8IPggOJjCciVm5nSCBQQn2imNDiWjIn3Jbt8rqzQyfWc6zmsGcZYXYf865DQaabMNSCv3nlPN4lC%2F%2BVg808pgES7Su7HrMETXSbcOK980a2sINuMDlw9GwQLCkU%2FiNGjSAd4nHPOcxdIaRyU9syBnMxSwkkGqixDURFcFntKRQRe2cl3MDdzTQ2L%2BzwvQC2xgHyqdVaTw4GXqLeWK9h6fr1ATRvlrs5zEBg4YFOQkDfy27CnHPJJz98VNIPyuDJ%2F6PPFDX8OWC5GgjU2ITICPaC6A%3D%3D\",\"encrypt\":\"RSA\",\"logic\":\"orderReceipt\",\"rsaSign\":\"l8O1%2BZcuQkAzMZ1QtZ35Q4D5v3vS0%2B1qRws%2BUhDqEpo7o%2Fe7VNllc%2FKwSacInhUozZuFy8OrxHGzZcN5Vahz6GQloSZRRoxfdSqjau2p8q0tTFVpKrLqFWvHz1D2TmOVC%2FL96GnpTBArh8nEK%2BF468qyxUTZaJ%2BdXl4bGJZYSDI%3D\",\"sys\":\"1\"}";
					//����Data��{"heleOrderCode":"00_O2015111216221244547149_54_1511129999","orderCode":"O2015111216221244549999","receiverName":"�����ҵ�","receiverTime":"2015-11-12 17:51:49"}
					//       "{\"heleOrderCode\":\"00_O2015111216221244547149_54_1511129999\",\"orderCode\":\"O2015111216221244549999\",\"receiverName\":\"�����ҵ�\",\"receiverTime\":\"2015-11-12 17:51:49\"}";
					//��ӦData��{"result":{\"status\":\"1\",\"orderCode\":\"O2015111216221244547149\",\"heleOrderCode\":\"00_O2015111216221244547149_54_1511121655\",\"orderStatus\":\"40\"},"code":"1","msg":"�ջ��ɹ���","logic":"orderReceipt"}
					JSONObject  sourceJson_orderReceipt =(JSONObject) JSONObject.parse(source);
					p_orderCode = sourceJson_orderReceipt.getString("orderCode");
					p_heleOrderCode = sourceJson_orderReceipt.getString("heleOrderCode");	
					
					response_OrderReceipt(client,p_orderCode,p_heleOrderCode);
					
					
					
					break;
					
				case "orderRefundApply":
					
					
					JSONObject  sourceJson_orderRefundApply =(JSONObject) JSONObject.parse(source);
					p_spReturnId = sourceJson_orderRefundApply.getString("spReturnId");
					//p_heleOrderCode = sourceJson_orderRefundApply.getString("heleOrderCode");	
					p_returnId = response_orderRefundApply(client,p_spReturnId);
					
					log.info("\nResponse to TransactionCenter &����  Hele �ص���������ͬ��ȷ���ջ���������������\n");
					
					//���ջ���ͬ��
					//new CallBackClient_Hele().postOrderService("R22016031513591740822665", "WRE160315140101707");
					new CallBackClient_Hele().postOrderService(p_spReturnId, p_returnId);
					//new CallBackClient_Hele().postOrderService("R22016031513591740822665", "WRE160315140101707");
				case "reverse":
					
					break;
					
				default:
					log.info("no match Logic");
				}
				


				
				
				
				

				
			}// end of POST
			

			
			// �رտͻ�������
			client.close();
			//System.out.println("�ͻ��˷�����ɣ�\n\n");



		} catch (Exception e) {
			log.info("HTTP����������:" + e.getMessage());
			e.printStackTrace();
		}
	}


	String response_Order(Socket socket,String p_orderCode) throws IOException {
		

		PrintStream out = new PrintStream(socket.getOutputStream(), true);
		String responseBody = "{\"code\":\"1\",\"msg\":\"\",\"result\":[{\"orderCode\":\"O2016010813520166117069\",\"heleOrderCode\":\"00_O2016010813520166117069_72_1601081400\"}]}";
//		O2016010813520166117069
//	 00_O2016010813520166117069_72_1601081400
		String sHeleid = "00_"+p_orderCode+"72"+new SimpleDateFormat("yyMMddHHmm").format( new Date() );
		
		out.println("HTTP/1.0 200 OK");// ����Ӧ����Ϣ,������Ӧ��
		out.println("Content-Type:text/html;charset=GBK");// + contentType);
		//System.out.println(responseBody.length());
//		out.println("Content-Length:" + responseBody.length());// ���������ֽ���
//		out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ		
		try {
			//Body����
			//log.info("Response Body1��\t"+responseBody);			
			JSONObject  dataJson =(JSONObject) JSONObject.parse(responseBody);
			
			JSONArray arrayJson = dataJson.getJSONArray("result");
			JSONObject  dataJson2 = arrayJson.getJSONObject(0);
			
			//�б�Ԫ�ظ���
			Map<String,String> value2 = JSON.toJavaObject(dataJson2, Map.class);
			value2.put("orderCode",p_orderCode);//�ı�zzmm��ֵ	
			value2.put("heleOrderCode",sHeleid);
			dataJson2 = (JSONObject) JSON.toJSON(value2);			
			//�б����
			arrayJson.clear();
			arrayJson.add(dataJson2);			
			
			
			Map<String,JSONArray> value = JSON.toJavaObject(dataJson, Map.class);
			value.put("result",arrayJson);//�ı�zzmm��ֵ	
			dataJson = (JSONObject) JSON.toJSON(value);			
			responseBody = JSONObject.toJSONString(dataJson);	
			
			
			log.info("Response Body��\t"+responseBody);
			
			//data = responseBody.getBytes();
			
			byte[] encodedData =
					RSAPrivate.encryptByPublicKey(responseBody.getBytes(),
						RSAPrivate.getPublicKey(APIConstants.PUBLIC_KEY));					
			//end of ����Data				
			String s2 = URLEncoder.encode(Base64.encode(encodedData), "UTF-8");				
			log.info("Response Body����:"+s2);
			//����
			//System.out.println(responseBody.length());
			//System.out.println(encodedData.length);
			out.println("Content-Length:" + encodedData.length);// ���������ֽ���
			out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ	
			out.write(encodedData);
			out.flush();
			
			out.close();
			
			return sHeleid;

		} catch (IOException e) {
			out.println("<h1>500����!</h1>" + e.getMessage());
			throw e;
		} finally {
			out.close();
		}
			
	}// end fo Fun

	
	void response_OrderReceipt(Socket socket,String p_orderCode,String p_heleOrderCode) throws IOException {
		

		PrintStream out = new PrintStream(socket.getOutputStream(), true);
		String responseBody = "{\"result\":{\"status\":\"1\",\"orderCode\":\"O2015111216433697084626\",\"heleOrderCode\":\"00_O2015111216433697084626_54_1511121650\",\"orderStatus\":\"40\"},\"code\":\"1\",\"msg\":\"�ջ��ɹ���\",\"logic\":\"orderReceipt\"}" ;


		out.println("HTTP/1.0 200 OK");// ����Ӧ����Ϣ,������Ӧ��
		out.println("Content-Type:text/html;charset=GBK");// + contentType);
		//out.println("Content-Length:" + responseBody.length());// ���������ֽ���
		//out.println("Content-Length:3");// ���������ֽ���
		//out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ		
		try {//Body����
			//log.info("Response Body1��\t"+responseBody);			
			JSONObject  dataJson =(JSONObject) JSONObject.parse(responseBody);
			//JSONArray arrayJson = dataJson.getJSONArray("result");
			JSONObject  dataJson2 = dataJson.getJSONObject("result");
			
			
			Map<String,String> value2 = JSON.toJavaObject(dataJson2, Map.class);
			value2.put("orderCode",p_orderCode);
			value2.put("heleOrderCode", p_heleOrderCode);
			dataJson2 = (JSONObject) JSON.toJSON(value2);			
		
			//arrayJson.clear();
			//arrayJson.add(dataJson2);			
			
			Map<String,JSONObject> value = JSON.toJavaObject(dataJson, Map.class);
			value.put("result",dataJson2);//�ı�zzmm��ֵ	
			dataJson = (JSONObject) JSON.toJSON(value);
			
			responseBody = JSONObject.toJSONString(dataJson);			
			log.info("Response Body��\t"+responseBody);
			
			//data = responseBody.getBytes();
			
			byte[] encodedData =
					RSAPrivate.encryptByPublicKey(responseBody.getBytes(),
						RSAPrivate.getPublicKey(APIConstants.PUBLIC_KEY));					
			//end of ����Data				
			String s2 = URLEncoder.encode(Base64.encode(encodedData), "UTF-8");				
			log.info("ResponseBody����:\t\t"+s2);

			
			
//			System.out.println(responseBody.length());
//			System.out.println(encodedData.length);
//			System.out.println(s2.length());
//			System.out.println(s2.getBytes().length);
			//out.println("Content-Length:" + responseBody.length());// ���������ֽ���
			//out.println("Content-Length:" + encodedData.length);// ���������ֽ���
			out.println("Content-Length:" + s2.length());// ���������ֽ���
			out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ	
			out.write(s2.getBytes());	
			
			
			//out.println("Content-Length:3");// ���������ֽ���
			//out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ	
			//System.out.print("abc");
			//out.write("abc".getBytes());
			
			
				
			out.flush();
			out.close();

		} catch (IOException e) {
			out.println("<h1>500����!</h1>" + e.getMessage());
			throw e;
		} finally {
			out.close();
		}
			
	}// end fo Fun
	

	String response_orderRefundApply(Socket socket,String f_param1) throws IOException{
		

		PrintStream out = new PrintStream(socket.getOutputStream(), true);
//		String responseBody = "{\"code\":\"1\",\"msg\":\"\",\"result\":[{\"orderCode\":\"O2016010813520166117069\",\"heleOrderCode\":\"00_O2016010813520166117069_72_1601081400\"}]}";
//		O2016010813520166117069
//	 00_O2016010813520166117069_72_1601081400
		
		//R12015111216561563593325
		// WRE151112170500762 
		//{\"result\":\"{\"spReturnId\":\"R12015111216561563593325\",\"returnId\":\"WRE151112170500762\"}",\"code\":\"1\",\"msg\":\"����ɹ�\"}
		//String responseBody = "{\"code\":\"1\",\"result\":\"{\"spReturnId\":\"R12015111216561563593325\",\"returnId\":\"WRE151112170500762\"}\",\"msg\":\"����ɹ�\"}";
		//String responseBody = "{\"result\":\"{\\\"spReturnId\\\":\\\"R12015111216561563593325\\\",\\\"returnId\\\":\\\"WRE151112170500762\\\"}\",\"code\":\"1\",\"msg\":\"����ɹ�\"}";
		String responseBody = "{\"code\":\"1\",\"msg\":\"����ɹ�\"}";
		String responseBodySub = "{\"spReturnId\":\"R12015111216561563593325\",\"returnId\":\"WRE151112170500762\"}";
		
		String returnId = "WRE"+new SimpleDateFormat("yyMMddHHmmSSS").format( new Date() );
		
		out.println("HTTP/1.0 200 OK");// ����Ӧ����Ϣ,������Ӧ��
		out.println("Content-Type:text/html;charset=GBK");// + contentType);
//		out.println("Content-Length:" + responseBody.length());// ���������ֽ���
//		out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ		
		try {
			//Body����
			log.info("Response Body1��\t"+responseBody);			
			JSONObject  dataJson =(JSONObject) JSONObject.parse(responseBody);
			
			
//			JSONArray arrayJson = dataJson.getJSONArray("result");
//			JSONObject  dataJson2 = arrayJson.getJSONObject(0);
			
			JSONObject dataJson2 = (JSONObject) JSONObject.parse(responseBodySub);
			
			
			//�б�Ԫ�ظ���
			Map<String,String> value2 = JSON.toJavaObject(dataJson2, Map.class);
			value2.put("spReturnId",f_param1);//�ı�zzmm��ֵ	
			value2.put("returnId",returnId);
			dataJson2 = (JSONObject) JSON.toJSON(value2);			
			//�б����
			//arrayJson.clear();
			//arrayJson.add(dataJson2);			
			
			
			//Map<String,JSONObject> value = JSON.toJavaObject(dataJson, Map.class);
			Map<String,String> value = JSON.toJavaObject(dataJson, Map.class);
			value.put("result",JSONObject.toJSONString(dataJson2));//�ı�zzmm��ֵ	
			
			dataJson = (JSONObject) JSON.toJSON(value);			
			responseBody = JSONObject.toJSONString(dataJson);	
			
			
			log.info("Response Body��\t"+responseBody);
			
			//data = responseBody.getBytes();
			
			byte[] encodedData =
					RSAPrivate.encryptByPublicKey(responseBody.getBytes(),
						RSAPrivate.getPublicKey(APIConstants.PUBLIC_KEY));					
			//end of ����Data				
			String s2 = URLEncoder.encode(Base64.encode(encodedData), "UTF-8");				
			log.info("Response Body����:"+s2);
			//����
			out.println("Content-Length:" + encodedData.length);// ���������ֽ���
			out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ		
			out.write(encodedData);
			out.flush();
			out.close();
			
			return returnId;

		} catch (IOException e) {
			out.println("<h1>500����!</h1>" + e.getMessage());
			throw e;
		} finally {
			out.close();
		}
			
	}// end fo Fun
	
}//end of Class


/*
void fileReaderAndReturn(String fileName, Socket socket) throws IOException {
	
	//Ԥ����
	if ("/".equals(fileName)) {// ���û�ӭҳ�棬�Ǻǣ�
		fileName = "/index.html";
		//fileName = "/GeTui.html";
	}
	fileName = fileName.substring(1);
	PrintStream out = new PrintStream(socket.getOutputStream(), true);
	File fileToSend = new File(fileName);

	//Response��Head����
	log.info("TEMPLATE��\t"+fileName);
	String fileEx = fileName.substring(fileName.indexOf(".") + 1);
	String contentType = null;
	// ���÷��ص���������
	// �˴���������tomcat/conf/web.xml�����õ�mime-mapping������һ�µġ�����֮�ã���д��ô������
	if ("htmlhtmxml".indexOf(fileEx) > -1) {
		contentType = "text/html;charset=GBK";
	} else if ("jpegjpggifbpmpng".indexOf(fileEx) > -1) {
		contentType = "application/binary";
	}

	if (fileToSend.exists() && !fileToSend.isDirectory()) {
		// http Э�鷵��ͷ
		out.println("HTTP/1.0 200 OK");// ����Ӧ����Ϣ,������Ӧ��
		out.println("Content-Type:" + contentType);
		out.println("Content-Length:" + fileToSend.length());// ���������ֽ���
		out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ
		
		FileInputStream fis = null;
		fis = new FileInputStream(fileToSend);
		try {//Body����
			
			byte data[];			
			data = new byte[fis.available()];
			fis.read(data);
			
//			String responseBody = new String(data);
//			JSONObject  dataJson =(JSONObject) JSONObject.parse(responseBody);
//			dataJson.
//			//ת��Map
//			Map<String,String> value = JSON.parseObject(json,Map.class);
//			value.put("zzmm","newValue");//�ı�zzmm��ֵ
//			JSON.toJSONString(value)��//����ת��json�ַ���������name��:"����","age":"20","xb":���С�,"zzmm":"newValue"��
			
			log.info("Response Body��\t"+new String(data));
			
			byte[] encodedData =
					RSAPrivate.encryptByPublicKey(data,
						RSAPrivate.getPublicKey(APIConstants.PUBLIC_KEY));
				

			String s2 = URLEncoder.encode(Base64.encode(encodedData), "UTF-8");
			
			log.info("���� :\t\t"+s2);
			
			//����
			out.write(encodedData);
			out.flush();
			

		} catch (IOException e) {
			out.println("<h1>500����!</h1>" + e.getMessage());
			throw e;
		} finally {
			out.close();
			fis.close();

		}
	} else {
		out.println("<h1>404����</h1>" + "�ļ�û���ҵ�,by SimpleHttpServer!");
		out.close();

	}

}

*/
