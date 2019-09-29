package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.service.PictureService;
/**
 * 图片管理
 * @ClassName: PictureServiceImpl.java
 * @version: v1.0.0
 * @author: dwg
 */
@Service
public class PictureServiceImpl implements PictureService {
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
/**
 * 图片上传
 * @Function uploadPicture
 * @author dwg
 * @param uploaFile
 * @return 
 * @see com.taotao.service.PictureService#uploadPicture(org.springframework.web.multipart.MultipartFile)
 */
	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		Map resultMap = new HashMap();
		try {
			// 生成新文件名
			// 获取原文件名
			String oldName = uploadFile.getOriginalFilename();
			String newName = UUID.randomUUID().toString();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			String imagePath=new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath, newName, uploadFile.getInputStream());

			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL+imagePath+"/"+newName);
			return resultMap;
		} catch (IOException e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传异常");
			return resultMap;
		}

	}

}
