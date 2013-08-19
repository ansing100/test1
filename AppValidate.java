package com.taobao.tae.utils;

import com.taobao.tae.config.ConfigParseException;
import com.taobao.tae.config.ConfigParser;
import com.taobao.tae.config.domain.Config;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-14
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public class AppValidate {
    private StringBuffer errMsg = new StringBuffer();

    public StringBuffer getErrMsg() {
        return errMsg;
    }

    public boolean validate(String path, String appName) {
        /**
         * 目前仅验证app.yml文件，在打包的时候会过滤文件类型
         */
        boolean flag = true;
        File appyml = new File(path, "app.yml");
        if (appyml.exists() && appyml.isFile() && appyml.length() > 0) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(appyml);
                String sourceContent = IOUtils.toString(inputStream, SystemUtils.FILE_ENCODING);
                Config config = ConfigParser.parse(sourceContent);
                String content;
                if (config != null) {
                    //判断应用名
                    content = config.getName();
                    if (StringUtil.isEmpty(content)) {
                        flag = false;
                        errMsg.append("App name in app.yml can not be empty!");
                        errMsg.append("\n");
                    } else {
                        if (!content.trim().equals(appName) || content.trim().length() > 64) {
                            flag = false;
                            errMsg.append("App name in app.yml not right!");
                            errMsg.append("\n");
                        }
                    }
                    //判断标题
                    content = config.getTitle();
                    if (StringUtil.isEmpty(content)) {
                        flag = false;
                        errMsg.append("App title in app.yml can not be empty!");
                        errMsg.append("\n");
                    } else {
                        if (content.length() > 128) {
                            flag = false;
                            errMsg.append("App title in app.yml is too long!");
                            errMsg.append("\n");
                        }
                    }
                    //判断作者
                    content = config.getAuthor();
                    if (StringUtil.isEmpty(content)) {
                        flag = false;
                        errMsg.append("App author in app.yml can not be empty!");
                        errMsg.append("\n");
                    } else {
                        if (content.trim().length() > 40) {
                            flag = false;
                            errMsg.append("App author in app.yml is too long!");
                            errMsg.append("\n");
                        }
                    }
                    //判断执行语言
                    content = config.getLanguage();
                    if (StringUtil.isEmpty(content)) {
                        flag = false;
                        errMsg.append("App language in app.yml can not be empty!");
                        errMsg.append("\n");
                    } else {
                        if (!content.toUpperCase().trim().equals("PHP")) {
                            flag = false;
                            errMsg.append("App language in app.yml must be php!");
                            errMsg.append("\n");
                        }
                    }
                    //判断首页
//                    content = config.getIndex();
//                    if (StringUtil.isEmpty(content)) {
//                        flag = false;
//                        errMsg.append("App index in app.yml can not be empty!");
//                        errMsg.append("\n");
//                    } else {
//                        if (!content.trim().toLowerCase().endsWith(".php")) {
//                            flag = false;
//                            errMsg.append("App index in app.yml can only be php file!");
//                            errMsg.append("\n");
//                        }
//                        File file = new File(path + content.trim());
//                        if (!file.exists()) {
//                            flag = false;
//                            errMsg.append("The index file doesn't exist in the path:" + content.trim());
//                            errMsg.append("\n");
//                        }
//                    }
                    //判断缩略图
                    content = config.getThumbnail();
                    if (StringUtil.isEmpty(content)) {
                        flag = false;
                        errMsg.append("App thumbnail in app.yml can not be empty!");
                        errMsg.append("\n");
                    } else {
                        if (!content.trim().toLowerCase().endsWith("jpg") && !content.trim().toLowerCase().endsWith("png") && !content.trim().toLowerCase().endsWith("gif")) {
                            flag = false;
                            errMsg.append("App thumbnail's type can only be jpg, png, and gif!");
                            errMsg.append("\n");
                        }
                        File file = new File(path + content.trim());
                        if (!file.exists()) {
                            flag = false;
                            errMsg.append("The thumbnail file doesn't exist in the path:" + content.trim());
                            errMsg.append("\n");
                        }
                    }
                    //判断类型
                    int type = config.getType();
                    if (type != 1 && type != 2 && type != 3 && type != 4) {
                        flag = false;
                        errMsg.append("App type in app.yml file can only be 1 to 4!");
                        errMsg.append("\n");
                    }
                    //判断描述
                    content = config.getDescription();
                    if (!StringUtil.isEmpty(content) && content.length() > 1024) {
                        flag = false;
                        errMsg.append("App description in app.yml is too long!");
                        errMsg.append("\n");
                    }
                    //判断全局css的配置
                    content = config.getCss();
                    if (StringUtil.isEmpty(content)) {
                        flag = false;
                        errMsg.append("App global css file in app.yml can not be empty!");
                        errMsg.append("\n");
                    } else {
                        if (!content.trim().toLowerCase().endsWith("css")) {
                            flag = false;
                            errMsg.append("App global css file's type is not css!");
                            errMsg.append("\n");
                        }
                        File file = new File(path + content.trim());
                        if (!file.exists()) {
                            flag = false;
                            errMsg.append("The global css file doesn't exist in the path:" + content.trim());
                            errMsg.append("\n");
                        }
                    }
                }
            } catch (IOException e) {
                errMsg.append("Can not read app.yml.");
                System.out.println(e);
                LogUtil.log(e);
                return false;
            } catch (ConfigParseException e) {
                errMsg.append("Can not parse app.yml, the file content contains errors.");
                System.out.println(e);
                LogUtil.log(e);
                return false;
            }
        } else {
            errMsg.append("app.yml not exist.");
            flag = false;
        }
        return flag;
    }

    public static void main(String args[]) {

    }
}
