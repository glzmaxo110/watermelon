package com.zc.travel.common.utils.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * CSV操作(导出和导入)
 * @description
 * @author 刘高林
 * @version 1.0,2016年4月10日 下午10:38:15,刘高林,Ins
 */
public class CsvUtils {

	/**
	 * 导出
	 * @param file csv文件(路径+文件名)
	 * @param dataList 数据
	 * @return
	 */
	public static void exportCsv(String path, List<String> dataList) throws Exception {
		if (StringUtils.isBlank(path) || path.toLowerCase().indexOf(".csv") <= 0)
			throw new Exception("生成的csv文件路径不合法!");
		
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			out = new FileOutputStream(new File(path));
			osw = new OutputStreamWriter(out, "GBK");
			bw = new BufferedWriter(osw);
			if (dataList != null && !dataList.isEmpty()) {
				for (String data : dataList) {
					bw.append(data).append("\r\n");
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 导入
	 * @param file csv文件(路径+文件)
	 * @return
	 */
	public static List<String> importCsv(File file) throws Exception {
		List<String> dataList = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}
	
	public static void main(String[] args) throws Exception {
		List<String> dataList = new ArrayList<String>();
		for (int i = 1; i <= 10; i++) {
			StringBuilder str = new StringBuilder();
			str.append(i).append(",")
				.append("853542010122802282").append(",")
				.append("测试").append(",")
				.append("").append(",")
				.append("102100000089").append(",")
				.append("行外").append(",")	//行内、行外
				.append(10).append(",")
				.append("普通").append(",")	//普通、实时
				.append("").append(",")
				.append("").append(",")
				.append("清算").append(",")	//提现、清算、额度调减、收益分配
				.append("").append(",")		//资金用途为提现时输入
				.append("").append(",")
				.append("").append(",")
				.append("").append(",")
				.append("").append(",")
				.append("").append(",")
				.append("").append(",")
				.append("").append(",")
				.append("").append(",");
			dataList.add(str.toString());
		}
		
		StringBuilder countSb = new StringBuilder();
		countSb.append("总笔数").append(",").append(dataList.size()).append(",")
			.append("总金额").append(",").append(100).append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",")
			.append(",");
		
		StringBuilder titleSb = new StringBuilder();
		titleSb.append("序号").append(",")
			.append("收款帐号").append(",")
			.append("收款账号户名").append(",")
			.append("收款账号开户行").append(",")
			.append("联行号").append(",")
			.append("收款账户类型").append(",")
			.append("交易金额").append(",")
			.append("转账类型").append(",")
			.append("用途").append(",")
			.append("备注").append(",")
			.append("资金用途").append(",")
			.append("手续费").append(",")
			.append("收单机构编码").append(",")
			.append("收单机构名称").append(",")
			.append("预留1").append(",")
			.append("预留2").append(",")
			.append("预留3").append(",")
			.append("预留4").append(",")
			.append("预留5").append(",")
			.append("预留6");
		
		List<String> allDataList = new ArrayList<String>();
		allDataList.add(countSb.toString());
		allDataList.add(titleSb.toString());
		allDataList.addAll(dataList);
		
		exportCsv("E:/aaa.csv", allDataList);
		System.out.println("生成文件成功");
	}
	
}
