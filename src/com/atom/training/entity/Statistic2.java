package com.atom.training.entity;

public class Statistic2 {
	private Integer authorityId;
	private String authorityName;
	private Integer total;
	public Statistic2(Integer authorityId, Integer total) {
		super();
		this.authorityId = authorityId;
		this.total = total;
	}
	public Statistic2() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}


	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public Integer getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Statistic2 [authorityId=" + authorityId + ", authorityName=" + authorityName + ", total=" + total + "]";
	}



}
