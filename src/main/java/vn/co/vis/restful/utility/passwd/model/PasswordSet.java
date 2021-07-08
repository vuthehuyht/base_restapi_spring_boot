package vn.co.vis.restful.utility.passwd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * PasswordSet object for passwdset
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "passwdset")
@XmlAccessorType(XmlAccessType.FIELD)
public class PasswordSet {
    @XmlElement(name = "passwd")
    List<Password> passwordList;
}
