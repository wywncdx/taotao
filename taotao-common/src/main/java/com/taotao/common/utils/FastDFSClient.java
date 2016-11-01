package com.taotao.common.utils;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient {
	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient storageClient = null;
	
	public FastDFSClient(String conf) throws Exception {
		if(conf.contains("classpath:")) {
			conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
		}
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient(trackerServer, storageServer);
	}
	
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
		String url = null;
		String[] result = storageClient.upload_file(fileName, extName, metas);
		for (String string : result) {
			if(url == null) {
				url = string;
			} else {
				url = url + "/" + string;
			}
		}
		return url;
	}
	
	public String uploadFile(String fileName) throws Exception {
		return uploadFile(fileName, null, null);
	}
	
	public String uploadFile(String fileName, String extName) throws Exception {
		return uploadFile(fileName, extName, null);
	}
	
	public String uploadFile(byte[] fileBuff, String extName, NameValuePair[] metas) throws Exception {
		String url = null;
		String[] result = storageClient.upload_file(fileBuff, extName, metas);
		for (String string : result) {
			if(url == null) {
				url = string;
			} else {
				url = url + "/" + string;
			}
		}
		return url;
	}
	
	public String uploadFile(byte[] fileBuff) throws Exception {
		return uploadFile(fileBuff, null, null);
	}
	
	public String uploadFile(byte[] fileBuff, String extName) throws Exception {
		return uploadFile(fileBuff, extName, null);
	}
}
