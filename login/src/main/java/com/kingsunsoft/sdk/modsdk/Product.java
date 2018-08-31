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
public class Product {

	@TarsStructProperty(order = 0, isRequire = true)
	public int productID = 0;
	@TarsStructProperty(order = 1, isRequire = true)
	public String name = "";
	@TarsStructProperty(order = 2, isRequire = true)
	public int price = 0;
	@TarsStructProperty(order = 3, isRequire = false)
	public String iapIdentifier = "";
	@TarsStructProperty(order = 4, isRequire = false)
	public int subcatId = 0;

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getIapIdentifier() {
		return iapIdentifier;
	}

	public void setIapIdentifier(String iapIdentifier) {
		this.iapIdentifier = iapIdentifier;
	}

	public int getSubcatId() {
		return subcatId;
	}

	public void setSubcatId(int subcatId) {
		this.subcatId = subcatId;
	}

	public Product() {
	}

	public Product(int productID, String name, int price, String iapIdentifier, int subcatId) {
		this.productID = productID;
		this.name = name;
		this.price = price;
		this.iapIdentifier = iapIdentifier;
		this.subcatId = subcatId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + TarsUtil.hashCode(productID);
		result = prime * result + TarsUtil.hashCode(name);
		result = prime * result + TarsUtil.hashCode(price);
		result = prime * result + TarsUtil.hashCode(iapIdentifier);
		result = prime * result + TarsUtil.hashCode(subcatId);
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
		if (!(obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		return (
			TarsUtil.equals(productID, other.productID) &&
			TarsUtil.equals(name, other.name) &&
			TarsUtil.equals(price, other.price) &&
			TarsUtil.equals(iapIdentifier, other.iapIdentifier) &&
			TarsUtil.equals(subcatId, other.subcatId) 
		);
	}

	public void writeTo(TarsOutputStream _os) {
		_os.write(productID, 0);
		_os.write(name, 1);
		_os.write(price, 2);
		if (null != iapIdentifier) {
			_os.write(iapIdentifier, 3);
		}
		_os.write(subcatId, 4);
	}


	public void readFrom(TarsInputStream _is) {
		this.productID = _is.read(productID, 0, true);
		this.name = _is.readString(1, true);
		this.price = _is.read(price, 2, true);
		this.iapIdentifier = _is.readString(3, false);
		this.subcatId = _is.read(subcatId, 4, false);
	}

}
