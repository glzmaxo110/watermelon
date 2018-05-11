package com.zc.travel.webboss.utils.fastdfs;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * @description FastDFS服务配置和连接服务
 * @remark
 */
public class FastDFSConfigServer {
	
	private static final Logger LOGGER = Logger.getLogger(FastDFSConfigServer.class);
	private TrackerServer trackerServer;
	private StorageServer storageServer;
	private StorageClient storageClient;
	private StorageClient1 storageClient1;

	public TrackerServer getTrackerServer() {
		return trackerServer;
	}

	public StorageServer getStorageServer() {
		return storageServer;
	}

	public StorageClient getStorageClient() {
		return storageClient;
	}

	public StorageClient1 getStorageClient1() {
		return storageClient1;
	}

	public FastDFSConfigServer invoke(int flag) throws IOException, Exception {
		/**
		 * 1.读取fastDFS客户端配置文件
		 */
		ClassPathResource cpr = new ClassPathResource("fdfs_client.conf");
		/**
		 * 2.配置文件的初始化信息，需要将路径中的中文编码进行转换
		 */
		ClientGlobal.init(URLDecoder.decode(cpr.getClassLoader().getResource("fdfs_client.conf").getPath(), "utf-8"));
		TrackerClient tracker = new TrackerClient();
		/**
		 * 3.建立连接
		 */
		trackerServer = tracker.getConnection();
		storageServer = null;
		/**
		 * 如果flag=0时候，构造StorageClient对象否则构造StorageClient1
		 */
		if (flag == 0) {
			storageClient = new StorageClient(trackerServer, storageServer);
		} else {
			storageClient1 = new StorageClient1(trackerServer, storageServer);
		}
		boolean bool = ProtoCommon.activeTest(this.trackerServer.getSocket());
		if (!bool) {
			LOGGER.error("####ERROR: 连接trackerServer服务器异常!");
		}
		return this;
	}
}
