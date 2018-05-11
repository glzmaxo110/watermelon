package com.zc.travel.webboss.utils.fastdfs;

import com.zc.travel.common.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description FastDFS上传文件帮助类
 * @remark
 */
public class FastDFSUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(FastDFSUtil.class);

	/**
	 * 上传服务器本地文件-通过Linux客户端,调用客户端命令上传
	 * @param filePath	文件绝对路径
	 * @return code-返回代码, group-文件组, msg-文件路径/错误信息
	 * @remark 返回码描述：
	 * 	0000-成功；
	 * 	0001-上传失败；
	 * 	0002-系统异常。
	 */
	public static Map<String, Object> uploadLocalFile(String filePath) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		/**
		 * 1.上传文件的命令
		 */
		String command = "fdfs_upload_file /etc/fdfs/client.conf  " + filePath;
		/**
		 * 2.定义文件的返回信息
		 */
		String fileId = "";
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			/**
			 * 3.通过调用api, 执行linux命令上传文件
			 */
			Process process = Runtime.getRuntime().exec(command);
			/**
			 * 4.读取上传后返回的信息
			 */
			inputStreamReader = new InputStreamReader(process.getInputStream());
			bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			if ((line = bufferedReader.readLine()) != null) {
				fileId = line;
			}
			/**
			 * 5.如果fileId包含M00，说明文件已经上传成功。否则文件上传失败
			 */
			if (fileId.contains("M00")) {
				retMap.put("code", "0000");
				retMap.put("group", fileId.substring(0, 6));
				retMap.put("msg", fileId.substring(7, fileId.length()));
			} else {
				retMap.put("code", "0001");	//上传错误
				retMap.put("msg", fileId);	//返回信息
			}
		} catch (Exception e) {
			LOGGER.error("IOException:" + e.getMessage());
			retMap.put("code", "0002");
			retMap.put("msg", e.getMessage());
		} finally {
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return retMap;
	}

	/**
	 * 直接通过fdfs java客户端上传到服务器-读取本地文件上传
	 * @param filePath	本地文件绝对路径
	 * @return code-返回代码, group-文件组, msg-文件路径/错误信息
	 */
	public static Map<String, Object> upload(String filePath) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		File file = new File(filePath);
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		if (file.isFile()) {
			try {
				String tempFileName = file.getName();
				byte[] fileBuff = FileUtils.readFileToByteArray(file);// FileUtil.getBytesFromFile(file);
				String fileId = "";
				// 截取后缀
				String fileExtName = tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
				FastDFSConfigServer FastDFSConfigServer = new FastDFSConfigServer().invoke(1);
				StorageClient1 storageClient1 = FastDFSConfigServer.getStorageClient1();
				storageServer = FastDFSConfigServer.getStorageServer();
				trackerServer = FastDFSConfigServer.getTrackerServer();

				/**
				 * 4.设置文件的相关属性。调用客户端的upload_file1的方法上传文件
				 */
				NameValuePair[] metaList = new NameValuePair[3];
				// 原始文件名称
				metaList[0] = new NameValuePair("fileName", tempFileName);
				// 文件后缀
				metaList[1] = new NameValuePair("fileExtName", fileExtName);
				// 文件大小
				metaList[2] = new NameValuePair("fileLength", String.valueOf(file.length()));
				// 开始上传文件
				fileId = storageClient1.upload_file1(fileBuff, fileExtName, metaList);
				retMap = handleResult(retMap, fileId);
			} catch (Exception e) {
				e.printStackTrace();
				retMap.put("code", "0002");
				retMap.put("msg", e.getMessage());
			} finally {
				/**
				 * 5.关闭跟踪服务器的连接
				 */
				colse(storageServer, trackerServer);
			}
		} else {
			retMap.put("code", "0001");
			retMap.put("msg", "error:本地文件不存在!");
		}
		return retMap;
	}

	/**
	 * 远程选择上传文件-通过MultipartFile
	 * @param file	文件流
	 * @return code-返回代码, group-文件组, msg-文件路径/错误信息
	 */
	public static Map<String, Object> upload(MultipartFile file) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			if (file.isEmpty()) {
				retMap.put("code", "0001");
				retMap.put("msg", "error:文件为空!");
			} else {
				FastDFSConfigServer FastDFSConfigServer = new FastDFSConfigServer().invoke(1);
				StorageClient1 storageClient1 = FastDFSConfigServer.getStorageClient1();
				storageServer = FastDFSConfigServer.getStorageServer();
				trackerServer = FastDFSConfigServer.getTrackerServer();
				String tempFileName = file.getOriginalFilename();
				// 设置元信息
				NameValuePair[] metaList = new NameValuePair[3];
				// 原始文件名称
				metaList[0] = new NameValuePair("fileName", tempFileName);
				// 文件后缀
				byte[] fileBuff = file.getBytes();
				String fileId = "";
				// 截取后缀
				String fileExtName = tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
				
				
				metaList[1] = new NameValuePair("fileExtName", fileExtName);
				// 文件大小
				metaList[2] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
				/**
				 * 4.调用客户端呢的upload_file1的方法开始上传文件
				 */
				fileId = storageClient1.upload_file1(fileBuff, fileExtName, metaList);
				retMap = handleResult(retMap, fileId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "0002");
			retMap.put("msg", "error:文件上传失败!");
		} finally {
			/**
			 * 5.关闭跟踪服务器的连接
			 */
			colse(storageServer, trackerServer);
		}
		return retMap;
	}
	
	public static byte[] download(String group, String filepath) {
		StorageServer storageServer = null;
		TrackerServer trackerServer = null;
		try {
			FastDFSConfigServer FastDFSConfigServer = new FastDFSConfigServer().invoke(0);
			StorageClient storageClient = FastDFSConfigServer.getStorageClient();
			storageServer = FastDFSConfigServer.getStorageServer();
			trackerServer = FastDFSConfigServer.getTrackerServer();
			//调用客户端的下载download_file的方法
			return storageClient.download_file(group, filepath);
		} catch (Exception e) {
			LOGGER.error("####ERROR: FastDFS下载文件异常! " + e);
			e.printStackTrace();
		} finally {
			//关闭跟踪服务器的连接
			colse(storageServer, trackerServer);
		}
		return null;
	}

	/**
	 * 下载文件
	 * @param response
	 * @param filepath	数据库存的文件路径
	 * @param downname	下载后的名称 filepath M00/开头的文件路径 group 文件所在的组 如：group0
	 * @throws IOException
	 */
	public static void download(HttpServletResponse response, String group,
			String filepath, String downname) {
		StorageServer storageServer = null;
		TrackerServer trackerServer = null;
		try {
			FastDFSConfigServer FastDFSConfigServer = new FastDFSConfigServer().invoke(0);
			StorageClient storageClient = FastDFSConfigServer.getStorageClient();
			storageServer = FastDFSConfigServer.getStorageServer();
			trackerServer = FastDFSConfigServer.getTrackerServer();
			/**
			 * 4.调用客户端的下载download_file的方法
			 */
			byte[] b = storageClient.download_file(group, filepath);
			if (b == null) {
				LOGGER.error("Error1 : file not Found!");
				response.getWriter().write("Error1 : file not Found!");
			} else {
				LOGGER.info("下载文件..");
				downname = new String(downname.getBytes("utf-8"), "ISO8859-1");
				response.setHeader("Content-Disposition", "attachment;fileName=" + downname);
				OutputStream out = response.getOutputStream();
				out.write(b);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("Error1 : file not Found!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			/**
			 * 5.关闭跟踪服务器的连接
			 */
			colse(storageServer, trackerServer);
		}
	}

	/**
	 * 删除文件
	 * @param group	文件分组,filepath 已M00/ 开头的文件路径
	 * @return code-返回代码,msg-错误信息
	 */
	public static Map<String, Object> delete(String group, String filepath) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		StorageServer storageServer = null;
		TrackerServer trackerServer = null;
		try {
			FastDFSConfigServer FastDFSConfigServer = new FastDFSConfigServer().invoke(0);
			StorageClient storageClient = FastDFSConfigServer.getStorageClient();
			storageServer = FastDFSConfigServer.getStorageServer();
			trackerServer = FastDFSConfigServer.getTrackerServer();
			/**
			 * 4.调用客户端的delete_file方法删除文件
			 */
			int i = storageClient.delete_file(group, filepath);
			if (i == 0) {
				retMap.put("code", "0000");
				retMap.put("msg", "删除成功！");
			} else {
				retMap.put("code", "0001");
				retMap.put("msg", "文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "0002");
			retMap.put("msg", "删除失败！");
		} finally {
			/**
			 * 5.关闭跟踪服务器的连接
			 */
			colse(storageServer, trackerServer);
		}
		return retMap;
	}

	/**
	 * 关闭服务器
	 * @param storageServer
	 * @param trackerServer
	 */
	private static void colse(StorageServer storageServer, TrackerServer trackerServer) {
		if (storageServer != null && trackerServer != null) {
			try {
				storageServer.close();
				trackerServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 处理上传到文件服务器之后，返回来的结果
	 * @param retMap
	 * @param fileId
	 * @return
	 */
	private static Map<String, Object> handleResult(Map<String, Object> retMap, String fileId) {
		if (!fileId.equals("") && fileId != null) {
			retMap.put("code", "0000");
			retMap.put("group", fileId.substring(0, 6));
			retMap.put("msg", fileId.substring(7, fileId.length()));
		} else {
			retMap.put("code", "0003");
			retMap.put("msg", "error:上传失败!");
		}
		return retMap;
	}
	
	/**
	 * 判断上传的文件是否为图片
	 * @param Type 文件后缀
	 * @return
	 */
	public static boolean isImag(String Type){
		String imageType[] = {"jpg","jpeg","png","bmp","gif"};
		List<String> imageTypeList = Arrays.asList(imageType);
		if(imageTypeList.contains(Type)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 将本地文件
	 * @param realPath 本地真实路径
	 * @param filePath 本地文件路径字符串, 多个中间","隔开
	 * @return 保存到FastDFS服务器的文件路径, 多个中间","隔开
	 */
	public static String localToFastDFS(String realPath, String filePath) throws Exception{
		if(StringUtils.isEmpty(filePath)) {
			return filePath;
		}
		StringBuffer dfsPath = new StringBuffer();
		String[] arr = filePath.split(",");
		Map<String, Object> resultMap = null;

		for (int i = 0; i <arr.length; i++) {
			if(StringUtils.isNotEmpty(arr[i])) {
				File file = new File(realPath + "/" + arr[i]);
				if(file.exists() && file.isFile()) {
					resultMap = FastDFSUtil.upload(realPath + "/" + arr[i]);
					if ("0000".equals(resultMap.get("code"))) {
						//TODO 通过http访问时，必须带组名+地址的方式进行文件下载等，如果此处去掉group参数，那么文件下载时，则需要制定下载所在组
						dfsPath.append(resultMap.get("group") + "/" + resultMap.get("msg")).append(",");
						//删除本地文件
						file.delete();
					} else {
						throw new Exception("文件上传DFS失败");
					}
				} else {
					dfsPath.append(arr[i]).append(",");
				}
			}
		}
		return dfsPath.toString().substring(0, dfsPath.toString().length() - 1);
	}
}