/**
* Copy Right @ EA.Corp   
* @Title: MatrixUploadUtil.java 
* @Package com.ea.market.utils 
* @Description:  
* @author Wade.wuchao
* @date 2016��1��11��
*/
package personal.wade.test.thread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ea.market.common.config.ProperParam;
import com.ea.market.common.util.FtpTool;
import com.ea.market.model.dto.Shareinfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/** 
* @ClassName: MatrixUploadUtil 
* @Description: 
* @author Wade.wuchao
* @date 2016��1��11�� 
*  
*/
public class MatrixUploadUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(MatrixUploadUtil.class);
	
	 /**
     * �����߳��ϴ���ά��
     */
    static class UploadDimensionRunnable implements Runnable {

        private Shareinfo shareinfo;
        private ProperParam properParam;

        private UploadDimensionRunnable(Shareinfo shareinfo, ProperParam properParam) {
            this.shareinfo = shareinfo;
            this.properParam = properParam;
        }

        @Override
        public void run() {
            try {
                uploadDimension();
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        /**
         * �ϴ���ά��
         *
         * @return
         * @throws NumberFormatException
         * @throws WriterException
         */
        public boolean uploadDimension() throws NumberFormatException, WriterException {
            boolean flag = false;
            ByteArrayOutputStream outputStream = null;
            InputStream inputStream = null;
            try {
                Hashtable hints = new Hashtable();
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                BitMatrix bitMatrix = new MultiFormatWriter().encode((properParam.getMarketshareinfo() + shareinfo.getId()), BarcodeFormat.QR_CODE,
                        Integer.parseInt(properParam.getErweiwidth()), Integer.parseInt(properParam.getErweiheight()), hints);
                String filename = shareinfo.getId() + "." + properParam.getErweiformat();
                outputStream = new ByteArrayOutputStream();
                try {
                    MatrixToImageWriter.writeToStream(bitMatrix, properParam.getErweiformat(), outputStream);
                } catch (IOException e) {
                    logger.error("�ϴ�������ά��ʧ��",e);
                }
                inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                //�ϴ�ֵͼƬ������
                FtpTool ft = new FtpTool();
                String url = properParam.getFileparamip();
                String username = properParam.getFileparamusername();
                String password = properParam.getFileparampassword();
                String serverpath = properParam.getFileparamserverpath();
                flag = ft.uploadFile(url, username, password, serverpath, filename, inputStream);
            } finally {
                try {
                    if(inputStream != null){
                        inputStream.close();
                    }
                    if(outputStream != null){
                        outputStream.close();
                    }
                } catch (IOException e) {
                    logger.error("���ɶ�ά�����,���ر��쳣",e);
                }
            }
            logger.info("���ɶ�ά��ɹ�");
            return flag;
        }
    }
}
