//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.05 at 05:59:53 PM CEST 
//


package com.politechnika.pkckbinding.dto.flightschedule;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

import lombok.Data;
import com.politechnika.pkckbinding.dto.rockets.Cost;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="launch_date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element ref="{http://example.com/rockets}cost"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}launchattr"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "launchDate",
    "cost"
})
@XmlRootElement(name = "launch")
public class Launch {

    @XmlElement(name = "launch_date", required = true)
    @XmlSchemaType(name = "dateTime")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    protected XMLGregorianCalendar launchDate;
    @XmlElement(required = true)
    protected Cost cost;
    @XmlAttribute(name = "launch_id", required = true)
    protected String launchId;
    @XmlAttribute(name = "launchpad_ref", required = true)
    protected String launchpadRef;
    @XmlAttribute(name = "customer_ref", required = true)
    protected String customerRef;
    @XmlAttribute(name = "payload_ref", required = true)
    protected String payloadRef;
    @XmlAttribute(name = "rocket_ref", required = true)
    protected String rocketRef;
}
