package cc.aaa.model;

import java.io.Serializable;
import java.util.Date;

public class ModelCategory implements Serializable {
	private static final long serialVersionUID = 1L;// ?
	// 类别表主键ID
	private int m_CategoryID;
	// 类别名称
	private String m_CategoryName;
	// 类型标记名称(字典类概念)
	private String m_TypeFlag;
	// 父类型ID
	private int m_ParentID = 0;
	// 路径
	private String m_Path;
	// 添加日期
	private Date m_CreateDate = new Date();
	// 状态 0失效 1启用
	private int m_State = 1;

	/**
	 * 账本表主键ID
	 */
	public int getCategoryID() {
		return m_CategoryID;
	}

	/**
	 * 账本表主键ID
	 */
	public void setCategoryID(int p_CategoryID) {
		this.m_CategoryID = p_CategoryID;
	}

	/**
	 * 账本名称
	 */
	public String getCategoryName() {
		return m_CategoryName;
	}

	/**
	 * 账本名称
	 */
	public void setCategoryName(String p_CategoryName) {
		this.m_CategoryName = p_CategoryName;
	}

	/**
	 * 类型标记名称
	 */
	public String getTypeFlag() {
		return m_TypeFlag;
	}

	/**
	 * 类型标记名称
	 */
	public void setTypeFlag(String p_TypeFlag) {
		this.m_TypeFlag = p_TypeFlag;
	}

	/**
	 * 父类型ID
	 */
	public int getParentID() {
		return m_ParentID;
	}

	/**
	 * 父类型ID
	 */
	public void setParentID(int p_ParentID) {
		this.m_ParentID = p_ParentID;
	}

	/**
	 * 路径
	 */
	public String getPath() {
		return m_Path;
	}

	/**
	 * 路径
	 */
	public void setPath(String p_Path) {
		this.m_Path = p_Path;
	}

	/**
	 * 添加日期
	 */
	public Date getCreateDate() {
		return m_CreateDate;
	}

	/**
	 * 添加日期
	 */
	public void setCreateDate(Date p_CreateDate) {
		this.m_CreateDate = p_CreateDate;
	}

	/**
	 * 状态 0失效 1启用
	 */
	public int getState() {
		return m_State;
	}

	/**
	 * 状态 0失效 1启用
	 */
	public void setState(int p_State) {
		this.m_State = p_State;
	}

	@Override
	public String toString() {
		return m_CategoryName;
	}
}
