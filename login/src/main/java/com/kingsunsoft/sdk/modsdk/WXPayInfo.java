// **********************************************************************
// This file was generated by a TARS parser!
// TARS version 1.0.1.
// **********************************************************************

package com.kingsunsoft.sdk.modsdk;

import com.qq.tars.protocol.util.*;
import com.qq.tars.protocol.annotation.*;
import com.qq.tars.protocol.tars.*;
import com.qq.tars.protocol.tars.annotation.*;

@TarsStruct
public class WXPayInfo {

	@TarsStructProperty(order = 0, isRequire = true)
	public String appid = "";
	@TarsStructProperty(order = 1, isRequire = true)
	public String partnerid = "";
	@TarsStructProperty(order = 2, isRequire = true)
	public String prepayid = "";
	@TarsStructProperty(order = 3, isRequire = true)
	public String wx_package = "";
	@TarsStructProperty(order = 4, isRequire = true)
	public String noncestr = "";
	@TarsStructProperty(order = 5, isRequire = true)
	public String timestamp = "";
	@TarsStructProperty(order = 6, isRequire = true)
	public String sign = "";

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getWx_package() {
		return wx_package;
	}

	public void setWx_package(String wx_package) {
		this.wx_package = wx_package;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public WXPayInfo() {
	}

	public WXPayInfo(String appid, String partnerid, String prepayid, String wx_package, String noncestr, String timestamp, String sign) {
		this.appid = appid;
		this.partnerid = partnerid;
		this.prepayid = prepayid;
		this.wx_package = wx_package;
		this.noncestr = noncestr;
		this.timestamp = timestamp;
		this.sign = sign;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + TarsUtil.hashCode(appid);
		result = prime * result + TarsUtil.hashCode(partnerid);
		result = prime * result + TarsUtil.hashCode(prepayid);
		result = prime * result + TarsUtil.hashCode(wx_package);
		result = prime * result + TarsUtil.hashCode(noncestr);
		result = prime * result + TarsUtil.hashCode(timestamp);
		result = prime * result + TarsUtil.hashCode(sign);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof WXPayInfo)) {
			return false;
		}
		WXPayInfo other = (WXPayInfo) obj;
		return (
			TarsUtil.equals(appid, other.appid) &&
			TarsUtil.equals(partnerid, other.partnerid) &&
			TarsUtil.equals(prepayid, other.prepayid) &&
			TarsUtil.equals(wx_package, other.wx_package) &&
			TarsUtil.equals(noncestr, other.noncestr) &&
			TarsUtil.equals(timestamp, other.timestamp) &&
			TarsUtil.equals(sign, other.sign) 
		);
	}

	public void writeTo(TarsOutputStream _os) {
		_os.write(appid, 0);
		_os.write(partnerid, 1);
		_os.write(prepayid, 2);
		_os.write(wx_package, 3);
		_os.write(noncestr, 4);
		_os.write(timestamp, 5);
		_os.write(sign, 6);
	}


	public void readFrom(TarsInputStream _is) {
		this.appid = _is.readString(0, true);
		this.partnerid = _is.readString(1, true);
		this.prepayid = _is.readString(2, true);
		this.wx_package = _is.readString(3, true);
		this.noncestr = _is.readString(4, true);
		this.timestamp = _is.readString(5, true);
		this.sign = _is.readString(6, true);
	}

}