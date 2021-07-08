package vn.co.vis.restful.utility.passwd.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Object data Word for word in config file in folder passwd
 *
 */
@Data
@XmlRootElement(name = "word")
@XmlAccessorType(XmlAccessType.FIELD)
public class Word {
    @XmlAttribute(name = "value")
    String value;
    @XmlAttribute(name = "start")
    String start;
    @XmlAttribute(name = "end")
    String end;
}
