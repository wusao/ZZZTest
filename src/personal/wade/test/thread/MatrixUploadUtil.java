/**
* Copy Right @ EA.Corp   
* @Title: MatrixUploadUtil.java 
* @Package com.ea.market.utils 
* @Description:  
* @author Wade.wuchao
* @date 2016年1月11日
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
* @date 2016年1月11日 
*  
*/
public class MatrixUploadUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(MatrixUploadUtil.class);
	
	 /**
     * 新启线程上传二维码
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
         * 上传二维码
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
                    logger.error("上传创建二维码失败",e);
                }
                inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                //上传值图片服务器
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
                    logger.error("生成二维码错误,流关闭异常",e);
                }
            }
            logger.info("生成二维码成功");
            return flag;
        }
    }
}
