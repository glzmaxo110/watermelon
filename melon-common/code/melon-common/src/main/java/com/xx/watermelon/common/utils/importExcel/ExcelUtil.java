package com.xx.watermelon.common.utils.importExcel;

import com.xx.watermelon.common.utils.string.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel导入工具类
 */
public class ExcelUtil {

    /**
     * Excel文件内容读取
     *
     * @param input     文件读取流
     * @param isExcel07 是否2007版本（true是；false否）
     * @param startRow  数据读取开始行（默认从0开始）
     * @param length    结果集大小
     * @return
     * @version 1.0, 2015年12月4日 下午2:42:52,Liugl,Ins
     */
    public static List<String[]> readExcel(InputStream input, boolean isExcel07, int startRow, int length) {
        List<String[]> data = new ArrayList<String[]>();
        try {
            //根据文件格式(2003或者2007)来初始化
            Workbook wb = isExcel07 ? new XSSFWorkbook(input) : new HSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);                //获得第一个表单
            Iterator<Row> rows = sheet.rowIterator();    //获得第一个表单的迭代器
            String[] str = null;
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss");
            while (rows.hasNext()) {
                Row row = rows.next();//获得行数据
                if (startRow-- > 0) {
                    continue;
                }
                str = new String[length];
                short lastCellNum = row.getLastCellNum();
                length = length > lastCellNum ? lastCellNum : length;
                for (int i = 0; i < length; i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    //根据cell中的类型来输出数据
                    String strValue = null;
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                //处理日期格式、时间格式
                                if (null != cell.getCellStyle().getDataFormatString() && "yyyymmdd".equals(cell.getCellStyle().getDataFormatString()))
                                    strValue = sdfDate.format(cell.getDateCellValue());
                                else if (null != cell.getCellStyle().getDataFormatString() && "hhmmss".equals(cell.getCellStyle().getDataFormatString()))
                                    strValue = sdfTime.format(cell.getDateCellValue());
                            } else if (cell.getCellStyle().getDataFormat() == 58) {
                                //处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                                final double value = cell.getNumericCellValue();
                                final Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                                strValue = formatDate.format(date);
                            } else {
                                //数值
                                if (StringUtils.isNotNull(cell.getNumericCellValue()))
                                    strValue = String.valueOf(cell.getNumericCellValue());
                            }
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            strValue = cell.getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            if (StringUtils.isNotNull(cell.getBooleanCellValue()))
                                strValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            strValue = cell.getCellFormula();
                            break;
                        default:
                            System.out.println("UnSuported Cell Type " + ">> " + i);
                            break;
                    }//end switch
                    str[i] = strValue;
                }//end while
                //结果集组装
                data.add(str);
            }//end while
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public static void main(String[] args) throws Exception {
        String dir = "D:\\Work\\新建 Microsoft Office Excel 工作表.xlsx";
        File file = new File(dir);
        InputStream input = new FileInputStream(file);
        List<String[]> data = readExcel(input, true, 0, 17);
        System.out.println("结果集大小:" + data.size());
        for (String[] s : data) {
            System.out.println(Arrays.toString(s));
        }
    }

}
