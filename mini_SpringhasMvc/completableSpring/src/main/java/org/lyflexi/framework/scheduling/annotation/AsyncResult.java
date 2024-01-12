package org.lyflexi.framework.scheduling.annotation;

import java.util.concurrent.TimeUnit;

import org.lyflexi.framework.util.concurrent.FailureCallback;
import org.lyflexi.framework.util.concurrent.ListenableFuture;
import org.lyflexi.framework.util.concurrent.SuccessCallback;

public class AsyncResult<V> implements ListenableFuture<V> {
	private final V value;
	private final Throwable executionException;

	public AsyncResult(V value) {
		this(value, null);
	}
	private AsyncResult(V value, Throwable ex) {
		this.value = value;
		this.executionException = ex;
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	public boolean isCancelled() {
		return false;
	}

	public boolean isDone() {
		return true;
	}

	public V get() {
		return this.value;
	}

	public V get(long timeout, TimeUnit unit) {
		return get();
	}

	@Override
	public void addCallback(SuccessCallback<? super V> successCallback, FailureCallback failureCallback) {
		try {
			if (this.executionException != null) {
				failureCallback.onFailure(this.executionException);
			}
			else {
				successCallback.onSuccess(this.value);
			}
		}
		catch (Throwable ex) {
		}
	}
}
