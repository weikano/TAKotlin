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
public class Request {

	@TarsStructProperty(order = 1, isRequire = true)
	public Header header = null;
	@TarsStructProperty(order = 2, isRequire = false)
	public byte[] body = null;

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

	public Request() {
	}

	public Request(Header header, byte[] body) {
		this.header = header;
		this.body = body;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + TarsUtil.hashCode(header);
		result = prime * result + TarsUtil.hashCode(body);
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
		if (!(obj instanceof Request)) {
			return false;
		}
		Request other = (Request) obj;
		return (
			TarsUtil.equals(header, other.header) &&
			TarsUtil.equals(body, other.body) 
		);
	}

	public void writeTo(TarsOutputStream _os) {
		_os.write(header, 1);
		if (null != body) {
			_os.write(body, 2);
		}
	}

	static Header cache_header;
	static { 
		cache_header = new Header();
	}
	static byte[] cache_body;
	static { 
		cache_body = new byte[1];
		byte var_3 = (byte)0;
		cache_body[0] = var_3;
	}

	public void readFrom(TarsInputStream _is) {
		this.header = (Header) _is.read(cache_header, 1, true);
		this.body = (byte[]) _is.read(cache_body, 2, false);
	}

}
