package codes.recursive.cnms.user;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestBody {
    @XmlElement String PRODUCT_ID;
    @XmlElement String PRODUCT_NAME;
    @XmlElement String LIST_PRICE;
}
