/*
 *  Copyright 1999-2019 Evision Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.feng.hotel;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.feng.hotel.utils.GlobalUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * mybatis plus代码生成器
 * 因为源码的比较弱鸡，所以增强了部分功能
 * 1. 不生成controller，生成后会手动删除
 * 2. 将不同的文件打包到不同的module中
 * 3. 去除了系统间差异[win linux mac]都可以使用
 *
 * @author asheng
 * @since 2019/1/23
 */
public class MybatisGeneratorJava {

    private static final String OS = System.getProperty("os.name");

    private static final String WIN_PREFIX = "win";

    private static final String PACKAGE = "com.feng.hotel";

    //修改为你要生成的表名
    private static final String TABLE_NAME = "tb_order_room";

    //修改为表的前缀
    private static final String TABLE_PREFIX = "tb_";

    @Test
    public void generate() throws IOException {
        Map<String, String> envMaps = System.getenv();

        String author = envMaps.get("USER");
        if (StringUtils.isBlank(author)) {
            author = "evision";
        }

        URL resource = ClassLoader.getSystemClassLoader().getResource("application.properties");
        if (resource == null) {
            throw new RuntimeException("please check out put dir.");
        }

        String projectPath = null;

        String dirPath = null;
        if ("FILE".equalsIgnoreCase(resource.getProtocol()) && !StringUtils
            .isBlank(resource.getPath())) {
            String path = resource.getPath().replace("/target/classes/application.properties", "");

            projectPath = path;

            path = path + "/src/main/java";
            dirPath = path.replace("/", File.separator);

            if (OS.toLowerCase().startsWith(WIN_PREFIX)) {
                dirPath = dirPath.substring(1);
            }
        }

        System.out.println(dirPath);

        if (StringUtils.isBlank(dirPath)) {
            dirPath = "D:\\workspace\\fraud\\bl-demo\\bl-demo-core\\src\\main\\java";
        }

        AutoGenerator generator = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.setSwagger2(false);

        globalConfig.setOutputDir(dirPath);
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(true);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(false);
        globalConfig.setEnableCache(false);
        globalConfig.setAuthor(author);
        globalConfig.setServiceName("I%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setOpen(false);
        generator.setGlobalConfig(globalConfig);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUrl(
            "jdbc:mysql://127.0.0.1:3306/hotel?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        generator.setDataSource(dsc);

        PackageConfig packConfig = new PackageConfig();
        packConfig.setParent(PACKAGE);
        packConfig.setMapper("mapper");
        packConfig.setEntity("domain");
        generator.setPackageInfo(packConfig);

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        final String finalProjectPath = projectPath;

        String mapperTemplatePath = "/templates/mapper.xml.vm";
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(mapperTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return (finalProjectPath + "/src/main/resources/mapper/"
                    + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML
                ).replace("/", File.separator);
            }
        });

        String serviceTemplatePath = "/templates/service.java.vm";
        focList.add(new FileOutConfig(serviceTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {

                return (finalProjectPath + "/src/main/java/" + PACKAGE.replace(".", File.separator)
                    + "/service/"
                    + String.format(globalConfig.getServiceName(), tableInfo.getEntityName())
                    + ".java"
                ).replace("/", File.separator);

            }
        });

        String serviceImplTemplatePath = "/templates/serviceImpl.java.vm";
        focList.add(new FileOutConfig(serviceImplTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {

                return (finalProjectPath + "/src/main/java/" +
                    PACKAGE.replace(".", File.separator) + "/service/impl/"
                    + String.format(globalConfig.getServiceImplName(), tableInfo.getEntityName())
                    + ".java"
                ).replace("/", File.separator);

            }
        });

        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);

        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
        strategy.setEntityColumnConstant(true);
      //  strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass("com.evision.common.share.BaseController");
        strategy.setTablePrefix(TABLE_PREFIX);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(TABLE_NAME);

        generator.setStrategy(strategy);

        generator.execute();

        System.out.println("============= 开始删除多余文件 ================");

        String rootPath =
            finalProjectPath + "/src/main/java/" + PACKAGE.replace(".", File.separator);

        //  String serviceImplPath = (rootPath + "/service/impl").replace("/", File.separator);

        // String servicePath = (rootPath + "/service").replace("/", File.separator);

        List<String> allPaths = Arrays
            .asList(
                (rootPath + "/mapper/xml").replace("/", File.separator),
                (rootPath + "/mapper/xml").replace("/", File.separator)
            );
        for (String path : allPaths) {
            System.out.println("======== 删除文件位置: " + path + " =======");

            File file = new File(path);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (!GlobalUtils.isNull(files)) {
                    for (File sonFile : files) {
                        sonFile.delete();
                    }
                }
                file.delete();
            }
        }
    }
}
