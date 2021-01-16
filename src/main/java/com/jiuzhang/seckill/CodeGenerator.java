package com.jiuzhang.seckill;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class CodeGenerator {

    /**
     * 读取控制台内容
     *
     * @param tip 提示文本
     * @return
     */
    public static String scanner(String tip) {
        try (Scanner scanner = new Scanner(System.in)) {
            StringBuilder help = new StringBuilder();
            help.append("请输入" + tip + "：");
            String input = scanner.next();
            assert StringUtils.isNotBlank(input);
            return input;
        } catch (Exception exception) {
            throw new MybatisPlusException("请输入正确的" + tip + "！");
        }
    }

    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("jiuzhang");
        globalConfig.setOpen(false);
        globalConfig.setSwagger2(true); // 实体属性 Swagger2 注解
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dateSourceConfig = new DataSourceConfig();
        dateSourceConfig.setUrl("jdbc:mysql://localhost:3306/flashsale");
        dateSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dateSourceConfig.setUsername("root");
        dateSourceConfig.setPassword("root");
        generator.setDataSource(dateSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.jiuzhang.seckill");
        generator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig config = new InjectionConfig() {
            @Override
            public void initMap() {
                // do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            /** 自定义输出文件名，如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化 */
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName() + "/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        config.setFileOutConfigList(focList);
        generator.setCfg(config);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        // 执行生成
        generator.execute();

    }

}
