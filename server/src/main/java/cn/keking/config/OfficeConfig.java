package cn.keking.config;

import com.sun.star.document.UpdateDocMode;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DocumentFormatRegistry;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class OfficeConfig {

    public void test(){
        LocalOfficeUtils.getDefaultOfficeHome();
    }
    private Map<String,Object> getLoadProperties() {
        Map<String,Object> loadProperties = new HashMap<>(10);
        loadProperties.put("Hidden", true);
        loadProperties.put("ReadOnly", true);
        loadProperties.put("UpdateDocMode", UpdateDocMode.QUIET_UPDATE);
        loadProperties.put("CharacterSet", StandardCharsets.UTF_8.name());
        return loadProperties;
    }

    @Bean
    @ConditionalOnBean(name = {"localOfficeManager", "documentFormatRegistry"})
    DocumentConverter localDocumentConverter(
            final OfficeManager localOfficeManager, final DocumentFormatRegistry documentFormatRegistry) {

        return LocalConverter.builder()
                .loadProperties(getLoadProperties())
                .officeManager(localOfficeManager)
                .formatRegistry(documentFormatRegistry)
                .build();
    }
}
