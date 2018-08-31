// **********************************************************************
// This file was generated by a TARS parser!
// TARS version 1.0.1.
// **********************************************************************

package com.kingsunsoft.sdk.mod;

import com.qq.tars.protocol.util.*;
import com.qq.tars.protocol.annotation.*;
import com.qq.tars.protocol.tars.*;
import com.qq.tars.protocol.tars.annotation.*;

@TarsStruct
public class Result {

	@TarsStructProperty(order = 1, isRequire = true)
	public int retCode = 0;
	@TarsStructProperty(order = 2, isRequire = true)
	public String msg = "";

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result() {
	}

	public Result(int retCode, String msg) {
		this.retCode = retCode;
		this.msg = msg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + TarsUtil.hashCode(retCode);
		result = prime * result + TarsUtil.hashCode(msg);
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
		if (!(obj instanceof Result)) {
			return false;
		}
		Result other = (Result) obj;
		return (
			TarsUtil.equals(retCode, other.retCode) &&
			TarsUtil.equals(msg, other.msg) 
		);
	}

	public void writeTo(TarsOutputStream _os) {
		_os.write(retCode, 1);
		_os.write(msg, 2);
	}


	public void readFrom(TarsInputStream _is) {
		this.retCode = _is.read(retCode, 1, true);
		this.msg = _is.readString(2, true);
	}

}
