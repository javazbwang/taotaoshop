package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.jupiter.api.Test;

import com.taotao.common.utils.FtpUtil;

public class TestFTP {

	@Test
	public void FTPClient() throws Exception{
//		创建FTPClient对象
		FTPClient client=new FTPClient();
		client.setControlEncoding("utf-8");
//		连接
		client.connect("192.168.10.50",21);
//		登录
		client.login("vftpuser", "root");
		int replyCode = client.getReplyCode();
		System.out.println(replyCode+"*"+FTPReply.isPositiveCompletion(replyCode));
		//		读文件
//		FileInputStream inputStream=new FileInputStream(new File("F:\\Spider\\images\\111.jpg"));
////		设置上传路径
//		boolean changeWorkingDirectory = client.changeWorkingDirectory("/images");
////		设置上传文件形式s
//		client.setFileType(FTP.BINARY_FILE_TYPE);
////		上传文件      参数1：服务器中文件名，参数2：本地inputStream
//		boolean storeFile = client.storeFile("dddd.jpg", inputStream);
//		FTPFile[] listFiles = client.listFiles("/images");
//		System.out.println(Arrays.toString(listFiles));
		boolean makeDirectory = client.makeDirectory("/images/kk");
		System.out.println(makeDirectory);
		client.logout();
		
	}
	@Test
	public void testFtpUtil() throws Exception{
		FileInputStream inputStream=new FileInputStream(new File("F:\\Spider\\images\\111.jpg"));
		FtpUtil.uploadFile("192.168.10.50", 21, "vftpuser", "root", "/images", "", "test01.jpg", inputStream);
		
	}
}
