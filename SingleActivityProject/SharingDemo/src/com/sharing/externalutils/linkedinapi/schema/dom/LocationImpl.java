
package com.sharing.externalutils.linkedinapi.schema.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sharing.externalutils.linkedinapi.schema.Country;
import com.sharing.externalutils.linkedinapi.schema.Location;

public class LocationImpl
    extends BaseSchemaEntity
    implements Location
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8469907482652062050L;
	protected String name;
    protected CountryImpl country;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country value) {
        this.country = ((CountryImpl) value);
    }

	@Override
	public void init(Element element) {
		Element countryElem = (Element) DomUtils.getChildElementByName(element, "country");
		if (countryElem != null) {
			CountryImpl countryImpl = new CountryImpl();
			countryImpl.init(countryElem);
			setCountry(countryImpl);
		}
		setName(DomUtils.getElementValueFromNode(element, "name"));
	}

	@Override
	public Element toXml(Document document) {
		Element element = document.createElement("location");
		DomUtils.setElementValueToNode(element, "name", getName());
		
		if (getCountry() != null) {
			element.appendChild(((CountryImpl) getCountry()).toXml(document));
		}
		return element;
	}
}
