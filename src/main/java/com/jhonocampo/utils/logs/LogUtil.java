package com.jhonocampo.utils.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    Logger logger = null;

    public LogUtil(Class<?> classLog) {
        logger = LoggerFactory.getLogger(classLog);    
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void error(String msg){
        logger.error(msg);
    }
}
