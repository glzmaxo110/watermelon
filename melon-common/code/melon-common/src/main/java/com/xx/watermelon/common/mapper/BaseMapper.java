package com.xx.watermelon.common.mapper;
import java.io.Serializable;

public class BaseMapper implements Serializable, Cloneable {

	private static final long serialVersionUID = -6313873934736738042L;
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 版本号
	 */
	private Integer version = 1;
	/**
	 * 删除状态（1-未删除；2-已删除）
	 */
	private Integer deleteStatus = 1;

	/**
	 * 主键ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 主键ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 版本号
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * 版本号
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 删除状态（1-未删除；2-已删除）
	 */
	public Integer getDeleteStatus() {
		return deleteStatus;
	}
	/**
	 * 删除状态（1-未删除；2-已删除）
	 */
	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

}
