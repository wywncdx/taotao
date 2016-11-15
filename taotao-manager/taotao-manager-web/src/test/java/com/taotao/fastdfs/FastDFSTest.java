package com.taotao.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.csource.fastdfs.test.TestClient;
import org.junit.Test;

import com.taotao.common.utils.FastDFSClient;

public class FastDFSTest {

	@Test
	public void uploadFile() throws FileNotFoundException, IOException, MyException {
//		1、把FastDFS提供的jar包添加到工程中
//		2、初始化全局配置。加载一个配置文件。
		ClientGlobal.init("E:\\workspace_mars\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\resource\\client.conf");
//		3、创建一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
//		4、创建一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
//		5、声明一个StorageServer对象，null。
		StorageServer storageServer = null;
//		6、获得StorageClient对象。
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//		7、直接调用StorageClient对象方法上传文件即可。
		String[] file = storageClient.upload_file("C:\\Users\\wyw\\Desktop\\IMG_20160222_192710.jpg", "jpg", null);
		
		for (String string : file) {
			System.out.println(string);
		}
	}
	
	@Test
	public void TestClient() throws Exception {
		FastDFSClient client = new FastDFSClient("E:\\workspace_mars\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\resource\\client.conf");
		String filePath = client.uploadFile("F:\\DCIM360\\Camera\\IMG_20151205_230807.jpg", "jpg");
		System.out.println(filePath);
	}
}
