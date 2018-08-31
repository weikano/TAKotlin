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
public class PlaceOrderResponse {

	@TarsStructProperty(order = 0, isRequire = true)
	public String orderId = "";
	@TarsStructProperty(order = 1, isRequire = true)
	public int payType = 0;
	@TarsStructProperty(order = 2, isRequire = false)
	public WXPayInfo wxPayInfo = null;
	@TarsStructProperty(order = 3, isRequire = false)
	public AliPayInfo aliPayInfo = null;
	@TarsStructProperty(order = 4, isRequire = false)
	public AppleIAPInfo appleIAPInfo = null;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public WXPayInfo getWxPayInfo() {
		return wxPayInfo;
	}

	public void setWxPayInfo(WXPayInfo wxPayInfo) {
		this.wxPayInfo = wxPayInfo;
	}

	public AliPayInfo getAliPayInfo() {
		return aliPayInfo;
	}

	public void setAliPayInfo(AliPayInfo aliPayInfo) {
		this.aliPayInfo = aliPayInfo;
	}

	public AppleIAPInfo getAppleIAPInfo() {
		return appleIAPInfo;
	}

	public void setAppleIAPInfo(AppleIAPInfo appleIAPInfo) {
		this.appleIAPInfo = appleIAPInfo;
	}

	public PlaceOrderResponse() {
	}

	public PlaceOrderResponse(String orderId, int payType, WXPayInfo wxPayInfo, AliPayInfo aliPayInfo, AppleIAPInfo appleIAPInfo) {
		this.orderId = orderId;
		this.payType = payType;
		this.wxPayInfo = wxPayInfo;
		this.aliPayInfo = aliPayInfo;
		this.appleIAPInfo = appleIAPInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + TarsUtil.hashCode(orderId);
		result = prime * result + TarsUtil.hashCode(payType);
		result = prime * result + TarsUtil.hashCode(wxPayInfo);
		result = prime * result + TarsUtil.hashCode(aliPayInfo);
		result = prime * result + TarsUtil.hashCode(appleIAPInfo);
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
		if (!(obj instanceof PlaceOrderResponse)) {
			return false;
		}
		PlaceOrderResponse other = (PlaceOrderResponse) obj;
		return (
			TarsUtil.equals(orderId, other.orderId) &&
			TarsUtil.equals(payType, other.payType) &&
			TarsUtil.equals(wxPayInfo, other.wxPayInfo) &&
			TarsUtil.equals(aliPayInfo, other.aliPayInfo) &&
			TarsUtil.equals(appleIAPInfo, other.appleIAPInfo) 
		);
	}

	public void writeTo(TarsOutputStream _os) {
		_os.write(orderId, 0);
		_os.write(payType, 1);
		if (null != wxPayInfo) {
			_os.write(wxPayInfo, 2);
		}
		if (null != aliPayInfo) {
			_os.write(aliPayInfo, 3);
		}
		if (null != appleIAPInfo) {
			_os.write(appleIAPInfo, 4);
		}
	}

	static WXPayInfo cache_wxPayInfo;
	static { 
		cache_wxPayInfo = new WXPayInfo();
	}
	static AliPayInfo cache_aliPayInfo;
	static { 
		cache_aliPayInfo = new AliPayInfo();
	}
	static AppleIAPInfo cache_appleIAPInfo;
	static { 
		cache_appleIAPInfo = new AppleIAPInfo();
	}

	public void readFrom(TarsInputStream _is) {
		this.orderId = _is.readString(0, true);
		this.payType = _is.read(payType, 1, true);
		this.wxPayInfo = (WXPayInfo) _is.read(cache_wxPayInfo, 2, false);
		this.aliPayInfo = (AliPayInfo) _is.read(cache_aliPayInfo, 3, false);
		this.appleIAPInfo = (AppleIAPInfo) _is.read(cache_appleIAPInfo, 4, false);
	}

}
