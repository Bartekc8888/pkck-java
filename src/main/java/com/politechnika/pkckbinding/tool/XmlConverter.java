package com.politechnika.pkckbinding.tool;

import javax.annotation.PostConstruct;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

import com.politechnika.pkckbinding.dto.flightschedule.FlightSchedule;
import com.politechnika.pkckbinding.exception.XmlLoadingException;
import com.politechnika.pkckbinding.exception.XmlSavingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

@Slf4j
@Component
@RequiredArgsConstructor
public class XmlConverter {
    @Value("${xml.base.path}")
    private String xmlFilePath;

    @Value("${xml.schema.path}")
    private String xmlSchemaPath;

    private final ResourceLoader resourceLoader;

    @Getter
    private FlightSchedule flightSchedule;

    @PostConstruct
    public void load() {
        flightSchedule = loadXmlFile();
        saveXmlFile(flightSchedule);
    }

    public FlightSchedule loadXmlFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(FlightSchedule.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            File xml = loadFileFromResource(xmlFilePath);

            Schema schema = loadSchema();
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
            return (FlightSchedule) unmarshaller.unmarshal(xml);
        } catch (Exception e) {
            throw new XmlLoadingException("Loading xml file failed!", e);
        }
    }

    public void saveXmlFile(FlightSchedule flightSchedule) {
        try {
            JAXBContext context = JAXBContext.newInstance(FlightSchedule.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File xml = new File("resultFile.xml");
            if (!xml.exists()) {
                xml.createNewFile();
            }

            Schema schema = loadSchema();
            marshaller.setSchema(schema);
            marshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
            marshaller.marshal(flightSchedule, xml);
        } catch (Exception e) {
            throw new XmlSavingException("Saving xml file failed!", e);
        }
    }

    private Schema loadSchema() throws SAXException, IOException {
        File xml = loadFileFromResource(xmlSchemaPath);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return sf.newSchema(xml);
    }

    private File loadFileFromResource(String filePath) throws IOException {
        Resource xmlResource = resourceLoader.getResource("classpath:" + filePath);
        return xmlResource.getFile();
    }
}
