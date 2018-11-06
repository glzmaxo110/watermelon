package com.xx.watermelon.common.enums;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

/**
 * @description: 数据导出类型的枚举
 * @author: xiesx
 * @createTime: 2018-06-05 11:50
 * @version: 1.0.0
 * @copyright: 金锡科技
 * @modify: xiesx
 **/

@ToString
public enum ExportType {
    /**
     * 预览
     */
    PREVIEW(1, "预览"),
    /**
     * 导出excel文件
     */
    EXCEL(2, "excel文件"),
    /**
     * 导出pdf文件
     */
    PDF(3, "pdf文件"),
    /**
     * 导出html文件
     */
    HTML(4, "html文件");


    @Getter
    @Setter
    private int index;

    @Getter
    @Setter
    private String name;


    ExportType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static ExportType getByIndex(int index) {
        return Arrays.stream(ExportType.values()).filter(at -> at.index == index).findFirst().orElse(null);
    }

    public static ExportType getByName(String name) {
        return Arrays.stream(ExportType.values()).filter(at -> at.name.equals(name)).findFirst().orElse(null);
    }
}
