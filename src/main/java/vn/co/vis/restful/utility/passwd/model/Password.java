package vn.co.vis.restful.utility.passwd.model;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Object data Password for password config in folder passwd
 *
 */
@Data
@XmlRootElement(name = "passwd")
@XmlAccessorType(XmlAccessType.FIELD)
public class Password {
    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "expires")
    private String expires;

    @XmlElement(name = "word")
    private Word word;
}
