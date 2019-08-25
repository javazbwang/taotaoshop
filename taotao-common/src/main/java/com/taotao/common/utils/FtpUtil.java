package com.taotao.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.channels.NonWritableChannelException;
import java.util.zip.InflaterInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp上传下载工具类
 * @ClassName: FtpUtil.java
 * @version: v1.0.0
 * @author: dwg
 */
public class FtpUtil {
	/**
	 * 上传文件到FTP服务器
	 * @author dwg
	 * @param host 服务器ip
	 * @param port 端口
	 * @param username 用户名
	 * @param password 密码
	 * @param basePath ftp基础目录  例如 /images
	 * @param filePath ftp服务器存放路径  /images下的img2019目录 /img2019
 	 * @param fileName 上传到服务器的文件名
	 * @param input 输入流
	 * @return boolean 
	 */
	public static boolean uploadFile(String host,int port,String username,String password,String basePath,String filePath,String fileName,InputStream input) {
		boolean result=false;
		FTPClient ftpClient=new FTPClient();
		try {
			int reply;
			ftpClient.connect(host, port);
			ftpClient.login(username, password);
			reply=ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return result;
			}
			
			if(!ftpClient.changeWorkingDirectory(basePath+filePath)) {
				String[] dirs=filePath.split("/");
				String tempPath=basePath;
				for (String dir : dirs) {
					if(dir==null&&dir.equals("")) continue;
					tempPath+="/"+dir;
					if(!ftpClient.changeWorkingDirectory(tempPath)) {
						if(!ftpClient.makeDirectory(tempPath)) {
							return result;
						}else {
							ftpClient.changeWorkingDirectory(tempPath);
						}
						
					}
					
				}
			}
//		设置上传文件类型为二进制
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if(!ftpClient.storeFile(fileName, input)) {
				return result;
			}
			input.close();
			ftpClient.logout();
			result=true;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	/**
	 * 从FTP服务器下载文件
	 * @author dwg
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param remotePath 服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return boolean
	 */
	public static boolean downloadFile(String host,int port,String username,String password,String remotePath,String fileName,String localPath) {
		boolean result=false;
		FTPClient ftpClient=new FTPClient();
		try {
			int reply;
			ftpClient.connect(host, port);
			ftpClient.login(username, password);
			reply=ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return result;
			}
			ftpClient.changeWorkingDirectory(remotePath);
			FTPFile[] fs= ftpClient.listFiles();
			for (FTPFile ff : fs) {
				if(ff.getName().equals(fileName)) {
					File localFile=new File(localPath+"/"+ff.getName());
					OutputStream is=new FileOutputStream(localFile);
					ftpClient.retrieveFile(ff.getName(), is);
					is.close();
				}
			}
			
			ftpClient.logout();
			result=true;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	
	
	
}
