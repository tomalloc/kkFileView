package cn.keking.service;


import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.SimpleDocumentFormatRegistry;


/**
 * 重写了DefaultDocumentFormatRegistry类，因为要添加自定义行为，比如字符编码。。。
 * @author yudian-it
 * @date 2017/12/5
 */
public class OfficePluginExtendFormatRegistry extends SimpleDocumentFormatRegistry {

    public OfficePluginExtendFormatRegistry() {
        addFormat(DefaultDocumentFormatRegistry.PDF);
        addFormat(DefaultDocumentFormatRegistry.SWF);
        addFormat(DefaultDocumentFormatRegistry.HTML);
        addFormat(DefaultDocumentFormatRegistry.ODT);
        addFormat(DefaultDocumentFormatRegistry.SXW);
        addFormat(DefaultDocumentFormatRegistry.DOC);
        addFormat(DefaultDocumentFormatRegistry.DOCX);
        addFormat(DefaultDocumentFormatRegistry.RTF);
        addFormat(DefaultDocumentFormatRegistry.RTF);
        addFormat(DefaultDocumentFormatRegistry.WPD);
        addFormat(DefaultDocumentFormatRegistry.TXT);
        addFormat(DefaultDocumentFormatRegistry.ODS);
        addFormat(DefaultDocumentFormatRegistry.SXC);
        addFormat(DefaultDocumentFormatRegistry.XLS);
        addFormat(DefaultDocumentFormatRegistry.XLSX);
        addFormat(DefaultDocumentFormatRegistry.CSV);
        addFormat(DefaultDocumentFormatRegistry.TSV);
        addFormat(DefaultDocumentFormatRegistry.ODP);
        addFormat(DefaultDocumentFormatRegistry.SXI);
        addFormat(DefaultDocumentFormatRegistry.PPT);
        addFormat(DefaultDocumentFormatRegistry.PPTX);
        addFormat(DefaultDocumentFormatRegistry.ODG);
        addFormat(DefaultDocumentFormatRegistry.SVG);
    }

}
