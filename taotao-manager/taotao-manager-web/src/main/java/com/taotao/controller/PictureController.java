package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

@Controller
public class PictureController {
	
	@Autowired
	private PictureService pictureService;
	
	
	/*
	 * ResponseBody:直接调用response的write方法把返回结果直接输出到客户端。
	 * 				但是我们不能直接把一个java对象直接响应给客户端，
	 * 				所以，ResponseBody的默认行为是调用jackson包，把pojo对象转换成json.
	 * 注意：这个图片上传功能在firefox中存在兼容性问题。所以这里，我们需要手动转换成json的字符串。
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody 
	public String uploudFile(MultipartFile uploadFile) {
		PictureResult result = pictureService.uploadPic(uploadFile);
		String json = JsonUtils.objectToJson(result);
		return json;
	}
}




