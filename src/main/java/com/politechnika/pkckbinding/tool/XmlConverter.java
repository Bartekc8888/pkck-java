package com.politechnika.pkckbinding.tool;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

import com.politechnika.pkckbinding.dto.flightschedule.FlightSchedule;
import com.politechnika.pkckbinding.exception.XmlLoadingException;
import com.politechnika.pkckbinding.exception.XmlSavingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class XmlConverter {
    @Value("${xml.base.path}")
    private String xmlFilePath;

    @Value("${xml.schema.path}")
    private String xmlSchemaPath;

    private final ResourceLoader resourceLoader;

    @PostConstruct
    public void load() {
        FlightSchedule flightSchedule = loadXmlFile();
        saveXmlFile(flightSchedule);
    }

    public FlightSchedule loadXmlFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(FlightSchedule.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            Resource xmlResource = resourceLoader.getResource("classpath:" + xmlFilePath);
            File xml = xmlResource.getFile();

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

            marshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
            marshaller.marshal(flightSchedule, xml);
        } catch (Exception e) {
            throw new XmlSavingException("Saving xml file failed!", e);
        }
    }
}
