package com.citizen.study.deploytool;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;

import java.io.File;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2019/3/29.
 * <p>
 * 一键将各工程下的配置文件发布到nacos，因为在nacos下修改文件太麻烦了。并且不能自动补全和纠错。
 */
public class NacosClientPushConfig {

    public static void main(String[] args) throws Exception {
        ConfigService configService = getConfigService();
        String groupName = "Public";
        List<File> configfiles = getConfigFiles();
        publishConfig(configService, groupName, configfiles);
    }

    private static List<File> getConfigFiles() {
        String baseDir = getBaseDir();

        List<File> configfiles = new ArrayList<File>();
        // 发布网关文件
        configfiles.add(new File(baseDir + "/citizenstudy-gateway/src/main/resources/application-gateway.yml"));
        // 发布auth文件
        configfiles.add(new File(baseDir + "/citizenstudy-auth/src/main/resources/application-auth.yml"));
        return configfiles;
    }

    public static ConfigService getConfigService() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", "127.0.0.1:8848");
        properties.put("contextPath", "nacos");
        ConfigService configService = NacosFactory.createConfigService(properties);
        return configService;
    }

    private static void publishConfig(ConfigService configService, String groupName, List<File> configfiles) throws Exception {
        for (File file : configfiles) {
            String content = readFileToString(file);
            boolean success = configService.publishConfig(file.getName(), groupName, content);
            if (!success) {
                throw new Exception("发布失败:" + file.getAbsolutePath());
            }
        }
    }





    public static String getBaseDir() {
        String cruDir = NacosClientPushConfig.class.getResource("../../../../").getPath();
        File baseFile = new File(cruDir).getParentFile().getParentFile().getParentFile();
        return baseFile.getAbsolutePath();
    }

    public static String readFileToString(File file) {
        String result = null;
        try {
            if (!file.exists()) {
                return null;
            }
            FileInputStream inputStream = new FileInputStream(file);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            result = new String(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
