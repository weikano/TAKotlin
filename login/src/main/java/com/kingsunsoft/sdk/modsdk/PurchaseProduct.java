// **********************************************************************
// This file was generated by a TARS parser!
// TARS version 1.0.1.
// **********************************************************************

package com.kingsunsoft.sdk.modsdk;

import com.qq.tars.protocol.tars.TarsInputStream;
import com.qq.tars.protocol.tars.TarsOutputStream;
import com.qq.tars.protocol.tars.annotation.TarsStruct;
import com.qq.tars.protocol.tars.annotation.TarsStructProperty;
import com.qq.tars.protocol.util.TarsUtil;

@TarsStruct
public class PurchaseProduct {

	@TarsStructProperty(order = 0, isRequire = true)
	public Product product = null;
	@TarsStructProperty(order = 1, isRequire = true)
	public int duration = 0;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public PurchaseProduct() {
	}

	public PurchaseProduct(Product product, int duration) {
		this.product = product;
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + TarsUtil.hashCode(product);
		result = prime * result + TarsUtil.hashCode(duration);
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
		if (!(obj instanceof PurchaseProduct)) {
			return false;
		}
		PurchaseProduct other = (PurchaseProduct) obj;
		return (
			TarsUtil.equals(product, other.product) &&
			TarsUtil.equals(duration, other.duration) 
		);
	}

	public void writeTo(TarsOutputStream _os) {
		_os.write(product, 0);
		_os.write(duration, 1);
	}

	static Product cache_product;
	static { 
		cache_product = new Product();
	}

	public void readFrom(TarsInputStream _is) {
		this.product = (Product) _is.read(cache_product, 0, true);
		this.duration = _is.read(duration, 1, true);
	}

}
