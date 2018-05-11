package com.zc.travel.common.utils.export.csv;

import java.io.OutputStream;

import com.zc.travel.common.utils.export.DataField;
import com.zc.travel.common.utils.export.ExportDataSource;
import com.zc.travel.common.utils.export.txt.TxtDataExportor;

/**
 * 描述: csv格式数据导出工具
 * @author Hill
 *
 */
public class CsvDataExportor<T> extends TxtDataExportor<T> {
	public CsvDataExportor(DataField[] fields, ExportDataSource<T> dataSource,OutputStream os) {
		super(fields, dataSource, os,",");
	}
}
