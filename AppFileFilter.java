package com.taobao.tae.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-14
 * Time: ÏÂÎç1:35
 * To change this template use File | Settings | File Templates.
 */
public class AppFileFilter implements FilenameFilter {
    private List<String> types;

    public AppFileFilter(){
        types = new ArrayList<String>();
        types.add(".js");
        types.add(".css");
        types.add(".php");
        types.add(".tpl");
        types.add(".dll");
        types.add(".yml");
        types.add(".log");
        types.add(".jpg");
        types.add(".xml");
        types.add(".png");
        types.add(".gif");
    }

    @Override
    public boolean accept(File dir, String name) {
        File file = new File(dir,name);
        if (file.isFile()){
            for (int i=0;i<types.size();i++){
                if (name.endsWith(types.get(i))){
                    return true;
                }
            }
            return false;
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
