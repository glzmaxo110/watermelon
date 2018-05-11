package com.zc.travel.webboss.utils.kindeditor;

import java.io.Serializable;

/**
 * @description 文件上传对象实体
 * @copyright 2017年9月12日 @ 金锡科技
 * @version 1.0,2017年9月12日 上午9:56:17,Liugl,Ins
 * @remark
 */
public class UploadFileItem implements Serializable {

	private static final long serialVersionUID = -6063908493137335121L;
	/**
	 * 对应表单控件名称
	 */
	private String formFieldName;
    /**
     * 文件名
     */
	private String fileName;

    public UploadFileItem(String formFieldName, String fileName) {
        this.formFieldName = formFieldName;
        this.fileName = fileName;
    }

    /**
	 * 对应表单控件名称
	 */
    public String getFormFieldName() {
        return formFieldName;
    }
    /**
	 * 对应表单控件名称
	 */
    public void setFormFieldName(String formFieldName) {
        this.formFieldName = formFieldName;
    }

    /**
     * 文件名
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * 文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}