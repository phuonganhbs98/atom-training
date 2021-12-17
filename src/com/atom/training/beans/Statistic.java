package com.atom.training.beans;

public class Statistic {
	private Integer authorityId;
	private String authorityName;
	private Integer genderId;
	private String genderName;
	private Integer totalFemale;
	private Integer totalMale;
	private Integer totalUnknown;
	private Integer totalAgeSmaller19;
	private Integer totalAgeGreater20;
	private Integer totalUnknownAge;
	public Statistic() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public Integer getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public Integer getGenderId() {
		return genderId;
	}
	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
	}
	public String getGenderName() {
		return genderName;
	}
	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}
	public Integer getTotalFemale() {
		return totalFemale;
	}
	public void setTotalFemale(Integer totalFemale) {
		this.totalFemale = totalFemale;
	}
	public Integer getTotalMale() {
		return totalMale;
	}
	public void setTotalMale(Integer totalMale) {
		this.totalMale = totalMale;
	}
	public Integer getTotalUnknown() {
		return totalUnknown;
	}
	public void setTotalUnknown(Integer totalUnknown) {
		this.totalUnknown = totalUnknown;
	}
	public Integer getTotalAgeSmaller19() {
		return totalAgeSmaller19;
	}
	public void setTotalAgeSmaller19(Integer totalAgeSmaller19) {
		this.totalAgeSmaller19 = totalAgeSmaller19;
	}
	public Integer getTotalAgeGreater20() {
		return totalAgeGreater20;
	}
	public void setTotalAgeGreater20(Integer totalAgeGreater20) {
		this.totalAgeGreater20 = totalAgeGreater20;
	}
	public Integer getTotalUnknownAge() {
		return totalUnknownAge;
	}
	public void setTotalUnknownAge(Integer totalUnknownAge) {
		this.totalUnknownAge = totalUnknownAge;
	}
	@Override
	public String toString() {
		return "Statistic [authorityId=" + authorityId + ", authorityName=" + authorityName + ", genderId=" + genderId
				+ ", genderName=" + genderName + ", totalFemale=" + totalFemale + ", totalMale=" + totalMale
				+ ", totalUnknown=" + totalUnknown + ", totalAgeSmaller19=" + totalAgeSmaller19 + ", totalAgeGreater20="
				+ totalAgeGreater20 + ", totalUnknownAge=" + totalUnknownAge + "]";
	}



}
