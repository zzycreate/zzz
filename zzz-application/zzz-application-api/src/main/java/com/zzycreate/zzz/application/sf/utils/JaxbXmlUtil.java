package com.zzycreate.zzz.application.sf.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author zhenyao.zhao
 * @date 2019/4/30
 */
public class JaxbXmlUtil {

    /**
     * jaxb 对象转xml
     * 默认不带头部，字符集UTF-8
     *
     * @param obj jaxb对象
     * @return 字符串
     * @throws JAXBException jaxb转换异常
     */
    public static String toXml(Object obj) throws JAXBException {
        return toXml(obj, false);
    }

    /**
     * jaxb 对象转xml
     *
     * @param obj      jaxb对象
     * @param needHead 是否需要头部
     * @return 字符串
     * @throws JAXBException jaxb转换异常
     */
    public static String toXml(Object obj, boolean needHead) throws JAXBException {
        return toXml(obj, needHead, "UTF-8");
    }

    /**
     * jaxb 对象转xml
     *
     * @param obj      jaxb对象
     * @param needHead 是否需要头部
     * @param encoding 字符集
     * @return 字符串
     * @throws JAXBException jaxb转换异常
     */
    public static String toXml(Object obj, boolean needHead, String encoding) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        // 是否省略xml头信息
        if (!needHead) {
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        } else {
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        }
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);

        return writer.toString();
    }

    public static <T> T toBean(String xml, Class<T> clazz) throws JAXBException, XMLStreamException {
        JAXBContext context = JAXBContext.newInstance(clazz);

        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xml));

        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(xmlStreamReader);
    }

    public static <T> T toBean(InputStream inputStream, Class<T> clazz) throws JAXBException, XMLStreamException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (T) unmarshaller.unmarshal(xmlStreamReader);

    }

}
