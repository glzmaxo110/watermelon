package com.zc.travel.common.utils.httpclient;

import com.alibaba.fastjson.JSONObject;
import com.zc.travel.common.utils.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * HTTP请求对象
 * @author Warmsheep
 */
public class HttpRequester {

	private static final String defaultContentEncoding = "UTF-8";

	public HttpRequester() {
	}

	public static JSONObject sendPostJson(String url, JSONObject jsonParam) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		JSONObject jsonResult = null;
		HttpPost method = new HttpPost(url);
		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam.toString(), defaultContentEncoding);
				entity.setContentEncoding(defaultContentEncoding);
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(method);
			url = URLDecoder.decode(url, defaultContentEncoding);
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200) {
				String str = "";
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());
					if (str != null && !"".equals(str)) {
						/** 把json字符串转换成json对象 **/
						jsonResult = JSONObject.parseObject(str);
					}
				} catch (Exception e) {
					System.out.println(("post请求提交失败:" + url + e));
				}
			}
		} catch (IOException e) {
			System.out.println("post请求提交失败:" + url + e);
		}
		return jsonResult;
	}

	/**
	 * POST数据到URL
	 * @param url
	 * @param postData
	 * @return
	 * @throws Exception
	 */
	public static String  Post(String url,String postData) throws  Exception {

		if(url == null || StringUtils.isBlank(url)){
			throw  new Exception("参数URL为空！");
		}
		if(postData == null || StringUtils.isBlank(postData)){
			throw  new Exception("参数postData为空！");
		}


		HttpClient httpClient = HttpClientBuilder.create().build();
		String postResult = "";  //返回参数
		HttpPost method = new HttpPost(url);


		if (null != postData) {
			// 解决中文乱码问题
			StringEntity entity = new StringEntity(postData, defaultContentEncoding);
			entity.setContentEncoding(defaultContentEncoding);
			method.setEntity(entity);
		}
		HttpResponse result = httpClient.execute(method);
		url = URLDecoder.decode(url, defaultContentEncoding);
		/** 请求发送成功，并得到响应 **/
		if (result.getStatusLine().getStatusCode() == 200) {
			try {
				/** 读取服务器返回过来的json字符串数据 **/
				postResult = EntityUtils.toString(result.getEntity());
			} catch (Exception e) {
				System.out.println(("post请求提交失败:" + url + e));
			}
		}

		return postResult;
	}
}
