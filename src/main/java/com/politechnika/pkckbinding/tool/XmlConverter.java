package com.politechnika.pkckbinding.tool;

import javax.annotation.PostConstruct;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

import com.politechnika.pkckbinding.dto.flightschedule.FlightSchedule;
import com.politechnika.pkckbinding.exception.HtmlGenerationException;
import com.politechnika.pkckbinding.exception.XmlLoadingException;
import com.politechnika.pkckbinding.exception.XmlSavingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.TransformerFactoryImpl;
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

    @Value("${xml.auxiliary.xsl.path}")
    private String auxiliaryXslPath;
    @Value("${xml.html.xsl.path}")
    private String htmlXslPath;

    @Value("${xml.auxiliary.xml.path}")
    private String auxiliaryResult;
    @Value("${xml.html.xml.path}")
    private String htmlResult;

    private final ResourceLoader resourceLoader;

    @Getter
    @Setter
    private FlightSchedule flightSchedule;

    @PostConstruct
    public void load() {
        flightSchedule = loadXmlFile();
        saveXmlFile(flightSchedule);

        generateHtml();
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

    public String generateHtml() {
        try {
            createAuxiliary();
            return createHtml();

        } catch (TransformerException | IOException e) {
            e.printStackTrace();
            throw new HtmlGenerationException("Html generation failed!", e);
        }
    }

    private String doTransformation(File sourceFile, File xslSourceFile, String targetFilePath) throws IOException, TransformerException {
        Source xmlSource = new StreamSource(sourceFile);
        Source htmlXsltSource = new StreamSource(xslSourceFile);
        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);

        TransformerFactory transformerFactory = new TransformerFactoryImpl();
        Transformer transformer = transformerFactory.newTransformer(htmlXsltSource);

        transformer.transform(xmlSource, result);
        FileWriter outputFileWriter = new FileWriter(new File(targetFilePath));
        String htmlString = stringWriter.toString();
        outputFileWriter.write(htmlString);
        outputFileWriter.close();

        return htmlString;
    }

    private String createHtml() throws IOException, TransformerException {
        return doTransformation(new File(auxiliaryResult), loadFileFromResource(htmlXslPath), htmlResult);
    }

    private void createAuxiliary() throws IOException, TransformerException {
        doTransformation(loadFileFromResource(xmlFilePath), loadFileFromResource(auxiliaryXslPath), auxiliaryResult);
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
