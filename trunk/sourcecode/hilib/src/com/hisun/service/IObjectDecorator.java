package com.hisun.service;

public abstract interface IObjectDecorator {
    public abstract Object decorate(Object paramObject) throws Exception;

    public abstract Object decorate(Object paramObject, String paramString) throws Exception;
}