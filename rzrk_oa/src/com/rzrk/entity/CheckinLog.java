package com.rzrk.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@Entity
public class CheckinLog extends BaseEntity{
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8871916949233406087L;
	private String checkinTime;
	private String checkoutTime;
	private String checkinDate;
	private Admin admin;
	private String name;
	private double checkinX;
	private double checkinY;
	private double checkoutX;
	private double checkoutY;
	
	public enum CheckStatus{
		正常, 迟到, 早退, 矿工, 未刷卡, 加班
	}
	
	private double lateHours; //迟到时间（小时）
	private double earlyHours; //早退时间（小时）
	private double absenteeism; //矿工（天）
	private int forgetCheckTime; //未刷卡次数
	private double overtimes; //加班时长（小时）
	
	private CheckStatus checkStatus;//状态
	private boolean checked;//是否校验过

	public double getOvertimes() {
		return overtimes;
	}
	public void setOvertimes(double overtimes) {
		this.overtimes = overtimes;
	}
	public int getForgetCheckTime() {
		return forgetCheckTime;
	}
	public void setForgetCheckTime(int forgetCheckTime) {
		this.forgetCheckTime = forgetCheckTime;
	}
	public double getLateHours() {
		return lateHours;
	}
	public void setLateHours(double lateHours) {
		this.lateHours = lateHours;
	}
	public double getAbsenteeism() {
		return absenteeism;
	}
	public void setAbsenteeism(double absenteeism) {
		this.absenteeism = absenteeism;
	}
	public CheckStatus getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(CheckStatus checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCheckinTime() {
		return checkinTime == null ? "" : checkinTime;
	}
	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}
	public String getCheckoutTime() {
		return checkoutTime == null ? "" : checkoutTime;
	}
	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}
	public String getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}
	public double getCheckoutX() {
		return checkoutX;
	}
	public void setCheckoutX(double checkoutX) {
		this.checkoutX = checkoutX;
	}
	public double getCheckoutY() {
		return checkoutY;
	}
	public void setCheckoutY(double checkoutY) {
		this.checkoutY = checkoutY;
	}
	public double getEarlyHours() {
		return earlyHours;
	}
	public void setEarlyHours(double earlyHours) {
		this.earlyHours = earlyHours;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_admin_checkinlog")
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public double getCheckinX() {
		return checkinX;
	}
	public void setCheckinX(double checkinX) {
		this.checkinX = checkinX;
	}
	public double getCheckinY() {
		return checkinY;
	}
	public void setCheckinY(double checkinY) {
		this.checkinY = checkinY;
	}
	
}
