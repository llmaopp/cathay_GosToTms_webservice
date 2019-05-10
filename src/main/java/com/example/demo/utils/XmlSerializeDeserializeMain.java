package com.example.demo.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @Auther: zhaoxinguo
 * @Date: 2018/8/23 10:48
 * @Description: 将Java对象序列化成XML格式，将XML反序列化还原为Java对象
 */
public  class XmlSerializeDeserializeMain {

    /**
     * 将Java对象序列化成XML格式
     * @param object
     * @return
     * @throws IOException
     */
    public static   String serialize(Object object){

        // 将employee对象序列化为XML
        XStream xStream = new XStream(new DomDriver());
        // 设置employee类的别名
        xStream.alias("employee", Object.class);
        String personXml = xStream.toXML(object);
        return personXml;
    }

    public static String beanToXml(Object obj,Class<?> load) throws JAXBException{
                JAXBContext context = JAXBContext.newInstance(load);
                 Marshaller marshaller = context.createMarshaller();
                 marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                 marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                 StringWriter writer = new StringWriter();
        marshaller.marshal(obj,writer);
                return writer.toString();
             }

    /**
     * 将XML反序列化还原为Java对象
     * @param personXml
     * @return
     */
    public static Object deserialize(String personXml) {
        // 将employee对象序列化为XML
        XStream xStream = new XStream(new DomDriver());
        Object t = (Object) xStream.fromXML(personXml);
        return t;
    }


    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }


//    public static void main(String [] args) {
//
//        Employee employee = new Employee();
//        employee.setEmployeeId(1);
//        employee.setEmployeeName("赵新国");
//        employee.setDepartment("软件工程师");
//        // 序列化
//        String serialize = serialize(employee);
//        System.out.println(serialize);
//        // 反序列化
//        Employee deserialize = deserialize(serialize);
//        System.out.println(deserialize.toString());
//
//    }

}
