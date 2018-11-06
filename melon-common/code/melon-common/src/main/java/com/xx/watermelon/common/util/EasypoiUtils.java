package com.xx.watermelon.common.util;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.html.ExcelToHtmlService;
import cn.afterturn.easypoi.pdf.PdfExportUtil;
import cn.afterturn.easypoi.pdf.entity.PdfExportParams;
import com.xx.watermelon.common.enums.ExportType;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @description: easypoi工具类
 * @author:
 * @createTime: 2018-06-04 17:28
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/

public class EasypoiUtils {

    /**
     * 导出excel文件
     *
     * @param response
     * @param fileName 导出文件名
     * @param clasz    列栏标题
     * @param dataList 数据
     */
    public static void exportToExcel(HttpServletResponse response, String fileName, Class clasz, List dataList) throws Exception {
        setExcelHead(response, fileName);
        Workbook book = ExcelExportUtil.exportExcel(new ExportParams(), clasz, dataList);
        book.write(response.getOutputStream());
    }

    /**
     * 导出excel文件
     *
     * @param response
     * @param fileName 导出文件名
     * @param colList  列栏标题
     * @param dataList 数据
     */
    public static void exportToExcel(HttpServletResponse response, String fileName, List colList, List dataList) throws Exception {
        setExcelHead(response, fileName);
        Workbook book = ExcelExportUtil.exportExcel(new ExportParams(), colList, dataList);
        book.write(response.getOutputStream());
    }


    /**
     * 按给定模板导出excel文件
     * @param fileName 导出文件名
     * @param templatePath  模板路径,classes下的路径
     * @param map 数据集
     */
    public static void exportTemplateToExcel(HttpServletResponse response, String fileName, String templatePath, Map map) throws Exception {
        setExcelHead(response, fileName);

        TemplateExportParams params = new TemplateExportParams(templatePath);
        Workbook book = ExcelExportUtil.exportExcel(params, map);
        book.write(response.getOutputStream());
    }


    /**
     * 导出pdf文件
     *
     * @param response
     * @param fileName 导出文件名
     * @param clasz    列栏标题
     * @param dataList 数据
     */
    public static void exportToPdf(HttpServletResponse response, String fileName, Class clasz, List dataList) throws Exception {
        setPdfHead(response, fileName);
        PdfExportUtil.exportPdf(new PdfExportParams(), clasz, dataList, response.getOutputStream());
    }


    /**
     * 导出pdf文件
     *
     * @param response
     * @param fileName 导出文件名
     * @param colList  列栏标题
     * @param dataList 数据
     */
    public static void exportToPdf(HttpServletResponse response, String fileName, List colList, List dataList) throws Exception {
        setPdfHead(response, fileName);
        PdfExportUtil.exportPdf(new PdfExportParams(), colList, dataList, response.getOutputStream());
    }


    /**
     * 导出html文件
     *
     * @param response
     * @param fileName 导出文件名
     * @param clasz    列栏标题
     * @param dataList 数据
     */
    public static void exportToHtml(HttpServletResponse response, String fileName, Class clasz, List dataList) throws Exception {
        setHtmlHead(response, fileName);
        Workbook book = ExcelExportUtil.exportExcel(new ExportParams(), clasz, dataList);
        response.getOutputStream().write(ExcelXorHtmlUtil.toAllHtml(book).getBytes());
    }

    /**
     * 导出html文件
     *
     * @param response
     * @param fileName 导出文件名
     * @param colList  列栏标题
     * @param dataList 数据
     */
    public static void exportToHtml(HttpServletResponse response, String fileName, List colList, List dataList) throws Exception {
        setHtmlHead(response, fileName);
        Workbook book = ExcelExportUtil.exportExcel(new ExportParams(), colList, dataList);
        response.getOutputStream().write(ExcelXorHtmlUtil.toAllHtml(book).getBytes());
    }

    /**
     * 预览
     *
     * @param response
     * @param clasz    列栏标题
     * @param dataList 数据
     */
    public static void toView(HttpServletResponse response, Class clasz, List dataList) throws Exception{
        Workbook book = ExcelExportUtil.exportExcel(new ExportParams(), clasz, dataList);
        ExcelToHtmlParams params = new ExcelToHtmlParams(book, true);
        response.getOutputStream().write(new ExcelToHtmlService(params).printPage().getBytes());
    }

    /**
     * 预览
     *
     * @param response
     * @param colList  列栏标题
     * @param dataList 数据
     */
    public static void toView(HttpServletResponse response, List colList, List dataList) throws Exception {
        Workbook book = ExcelExportUtil.exportExcel(new ExportParams(), colList, dataList);
        ExcelToHtmlParams params = new ExcelToHtmlParams(book, true);
        response.getOutputStream().write(new ExcelToHtmlService(params).printPage().getBytes());
    }

    /**
     * 数据导出或预览
     * @param response
     * @param fileName
     * @param type 导出类型
     * @param clasz 列栏标题
     * @param dataList 数据
     */
    public static void toExport(HttpServletResponse response, String fileName, int type, Class clasz, List dataList) throws Exception {
        switch (ExportType.getByIndex(type)) {
            case PREVIEW:
                toView(response, clasz, dataList);
                break;
            case EXCEL:
                exportToExcel(response, fileName, clasz, dataList);
                break;
            case PDF:
                exportToPdf(response, fileName, clasz, dataList);
                break;
            case HTML:
                exportToHtml(response, fileName, clasz, dataList);
                break;
            default:
                break;
        }
    }


    /**
     * 数据导出或预览
     * @param response
     * @param fileName
     * @param type 导出类型
     * @param colList 列栏标题
     * @param dataList 数据
     */
    public static void toExport(HttpServletResponse response, String fileName, int type, List colList, List dataList) throws Exception {
        switch (ExportType.getByIndex(type)) {
            case PREVIEW:
                toView(response, colList, dataList);
                break;
            case EXCEL:
                exportToExcel(response, fileName, colList, dataList);
                break;
            case PDF:
                exportToPdf(response, fileName, colList, dataList);
                break;
            case HTML:
                exportToHtml(response, fileName, colList, dataList);
                break;
            default:
                break;
        }
    }


    /**
     * 设置pdf文件头
     *
     * @param response
     * @param fileName
     * @throws UnsupportedEncodingException
     */
    private static void setPdfHead(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setHeader("content-Type", "application/pdf");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".pdf");
        response.setCharacterEncoding("UTF-8");
    }

    /**
     * 设置html文件头
     *
     * @param response 响应
     * @param fileName 文件名
     * @throws UnsupportedEncodingException
     */
    private static void setHtmlHead(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setHeader("content-Type", "application/text/html");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".html");
        response.setCharacterEncoding("UTF-8");
    }

    /**
     * 设置Excel文件头
     *
     * @param response 响应
     * @param fileName 文件名
     * @throws UnsupportedEncodingException
     */
    private static void setExcelHead(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
        response.setCharacterEncoding("UTF-8");
    }
}

