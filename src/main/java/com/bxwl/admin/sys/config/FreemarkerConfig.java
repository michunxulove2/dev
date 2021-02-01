package com.bxwl.admin.sys.config;

import com.bxwl.admin.sys.utils.DictUtils;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import jodd.util.LocaleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Configuration
public class FreemarkerConfig {

    @Autowired
    protected FreeMarkerViewResolver freeMarkerViewResolver;

    @PostConstruct
    public void setStaticModels() {
        FreemarkerStaticModels staticModels = FreemarkerConfig.FreemarkerStaticModels.getInstance();
        //设置在模板中调用的名称，和要调用的Java类
        staticModels.setStaticModels("LocaleUtil", LocaleUtil.class.getName());
        staticModels.setStaticModels("DictUtils", DictUtils.class.getName());
        freeMarkerViewResolver.setAttributesMap(staticModels);
    }
    static class FreemarkerStaticModels extends HashMap<String, Object> {
        private static FreemarkerStaticModels FREEMARKER_STATIC_MODELS;

        private FreemarkerStaticModels() {

        }

        public static FreemarkerStaticModels getInstance() {
            if (FREEMARKER_STATIC_MODELS == null) {
                FREEMARKER_STATIC_MODELS = new FreemarkerStaticModels();
            }
            return FREEMARKER_STATIC_MODELS;
        }

        void setStaticModels(String key, String value) {
            FREEMARKER_STATIC_MODELS.put(key, useStaticPackage(value));
        }

        static TemplateHashModel useStaticPackage(String packageName) {
            try {
                BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
                TemplateHashModel staticModels = wrapper.getStaticModels();
                return (TemplateHashModel) staticModels.get(packageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}


