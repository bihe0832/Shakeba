package com.bihe0832.shakeba.framework.request;
public interface HttpResponseHandler<T> {
	public void onResponse(T response);
}
