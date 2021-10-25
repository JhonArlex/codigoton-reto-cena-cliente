package com.jhonocampo.utils.controllers;

import com.jhonocampo.utils.logs.LogUtil;

public class GenericController {
    public LogUtil logUtil = null;
    public GenericController(Class<?> classController) {
        this.logUtil = new LogUtil(classController);
    }
}
