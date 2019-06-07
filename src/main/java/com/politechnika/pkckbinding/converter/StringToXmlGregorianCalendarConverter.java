package com.politechnika.pkckbinding.converter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.core.convert.converter.Converter;


public class StringToXmlGregorianCalendarConverter implements Converter<String, XMLGregorianCalendar> {

    @Override
    public XMLGregorianCalendar convert(String dateTimeString) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
