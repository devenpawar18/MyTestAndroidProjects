
package com.arbetsformedlingen.externalutils.linkedinapi.schema.dom;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.arbetsformedlingen.externalutils.linkedinapi.schema.Recipient;
import com.arbetsformedlingen.externalutils.linkedinapi.schema.Recipients;

public class RecipientsImpl
    extends BaseSchemaEntity
    implements Recipients
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5874984453850790428L;
	protected List<Recipient> recipientList;

    public List<Recipient> getRecipientList() {
        if (recipientList == null) {
            recipientList = new ArrayList<Recipient>();
        }
        return this.recipientList;
    }

	@Override
	public void init(Element element) {
		List<Element> recipientElems = DomUtils.getChildElementsByLocalName(element, "recipient");
		for (Element recepient : recipientElems) {
			RecipientImpl recipientImpl = new RecipientImpl();
			recipientImpl.init(recepient);
			getRecipientList().add(recipientImpl);
		}
	}

	@Override
	public Element toXml(Document document) {
		Element element = document.createElement("recipients");
		for (Recipient recepient : getRecipientList()) {
			element.appendChild(((RecipientImpl) recepient).toXml(document));
		}
		return element;
	}
}
