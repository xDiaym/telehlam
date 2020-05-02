package org.juicecode.telehlam.utils;

public interface ApiCallback<T> {
    void execute(T response);
}
