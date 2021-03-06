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
public class Response {

	@TarsStructProperty(order = 1, isRequire = false)
	public Header header = null;
	@TarsStructProperty(order = 2, isRequire = false)
	public byte[] body = null;
	@TarsStructProperty(order = 3, isRequire = false)
	public Result result = null;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Response() {
	}

	public Response(Header header, byte[] body, Result result) {
		this.header = header;
		this.body = body;
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + TarsUtil.hashCode(header);
		result = prime * result + TarsUtil.hashCode(body);
		result = prime * result + TarsUtil.hashCode(result);
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
		if (!(obj instanceof Response)) {
			return false;
		}
		Response other = (Response) obj;
		return (
			TarsUtil.equals(header, other.header) &&
			TarsUtil.equals(body, other.body) &&
			TarsUtil.equals(result, other.result) 
		);
	}

	public void writeTo(TarsOutputStream _os) {
		if (null != header) {
			_os.write(header, 1);
		}
		if (null != body) {
			_os.write(body, 2);
		}
		if (null != result) {
			_os.write(result, 3);
		}
	}

	static Header cache_header;
	static { 
		cache_header = new Header();
	}
	static byte[] cache_body;
	static { 
		cache_body = new byte[1];
		byte var_4 = (byte)0;
		cache_body[0] = var_4;
	}
	static Result cache_result;
	static { 
		cache_result = new Result();
	}

	public void readFrom(TarsInputStream _is) {
		this.header = (Header) _is.read(cache_header, 1, false);
		this.body = (byte[]) _is.read(cache_body, 2, false);
		this.result = (Result) _is.read(cache_result, 3, false);
	}

}
