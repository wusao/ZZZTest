package personal.wade.test.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileCopyTest {
	
	public static final String src = "C:\\Users\\Administrator\\Desktop\\Personal\\Copy_Env\\aaa.txt";
	public static final String dest1 = "C:\\Users\\Administrator\\Desktop\\Personal\\Copy_Env\\DESC\\OLD1.txt";
	public static final String dest2 = "C:\\Users\\Administrator\\Desktop\\Personal\\Copy_Env\\DESC\\OLD2.txt";
	public static final String dest3 = "C:\\Users\\Administrator\\Desktop\\Personal\\Copy_Env\\DESC\\OLD3.txt";
	
	
	/**
	 * ʹ��RandomAccessFile��ʽʵ���ļ�copy
	 */
	public static void copyFileWithRandomAccessFile(){
		long beginTime = System.currentTimeMillis();
		try {
			RandomAccessFile inRaf = new RandomAccessFile(src, "r");
			RandomAccessFile outRaf = new RandomAccessFile(dest1, "rw");
			int len = (int) inRaf.length();
			System.out.println("len="+len);
			byte[] b = new byte[len];
			inRaf.read(b, 0, len);
			System.out.println(b);
			outRaf.write(b);
			inRaf.close();
			outRaf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("����RandomAccessFilel��������ʱ��"
	                + (endTime - beginTime));
	}
	
	/**
	 * ͨ��NIOʵ���ļ�copy
	 */
	public static void copyFileWtihNIO(){
		long beginTime = System.currentTimeMillis();
		try {
			FileInputStream in = new FileInputStream(src);
			FileChannel inChannel = in.getChannel();
			
			FileOutputStream out = new FileOutputStream(dest2);
			FileChannel outChannel = out.getChannel();
			
			int size = (int) inChannel.size();
			
			ByteBuffer bf = ByteBuffer.allocate(size);
			inChannel.read(bf);
			bf.flip();
			outChannel.write(bf);
			bf.clear();
			
			outChannel.close();
			out.close();
			inChannel.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("����NIO��������ʱ��"
	                + (endTime - beginTime));
	}
	
	/**
	 * ͨ����ͨioʵ���ļ�copy
	 */
	public static void copyFileWithIO(){
		 long beginTime = System.currentTimeMillis();
		 try {
			FileInputStream fis = new FileInputStream(src);
			FileOutputStream fos = new FileOutputStream(dest3);
			
			int len = (int) fis.getChannel().size();
			byte[] b = new byte[len];
			fis.read(b, 0, len);
			fos.write(b);
			fos.flush();
			fos.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		long endTime = System.currentTimeMillis();

	    System.out.println("���ô�ͳIO��������ʱ��"
	                + (endTime - beginTime));

	}
	
	
	public static void main(String[] args) {
//		copyFileWithIO();
//		copyFileWtihNIO();
		copyFileWithRandomAccessFile();
	}
	
//	RandomAccessFile NIO IO
	
}
