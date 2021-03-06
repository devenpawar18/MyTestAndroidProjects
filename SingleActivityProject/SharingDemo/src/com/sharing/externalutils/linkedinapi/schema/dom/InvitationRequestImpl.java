
package com.sharing.externalutils.linkedinapi.schema.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sharing.externalutils.linkedinapi.schema.Authorization;
import com.sharing.externalutils.linkedinapi.schema.InvitationRequest;
import com.sharing.externalutils.linkedinapi.schema.InviteConnectType;

public class InvitationRequestImpl
    extends BaseSchemaEntity
    implements InvitationRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4777671180159209124L;
	protected InviteConnectType connectType;
    protected AuthorizationImpl authorization;

    public InviteConnectType getConnectType() {
        return connectType;
    }

    public void setConnectType(InviteConnectType value) {
        this.connectType = value;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization value) {
        this.authorization = ((AuthorizationImpl) value);
    }

	@Override
	public void init(Element element) {
		Element authElem = (Element) DomUtils.getChildElementByName(element, "authorization");
		if (authElem != null) {
			AuthorizationImpl authImpl = new AuthorizationImpl();
			authImpl.init(authElem);
			setAuthorization(authImpl);
		}
		String connectTypeStr = DomUtils.getElementValueFromNode(element, "connect-type");
		if (connectTypeStr != null) {
			setConnectType(InviteConnectType.fromValue(connectTypeStr));
		}
		
	}

	@Override
	public Element toXml(Document document) {
		Element element = document.createElement("invitation-request");
		if (getConnectType() != null) {
			DomUtils.setElementValueToNode(element, "connect-type", getConnectType().value());
		}
		
		if (getAuthorization() != null) {
			element.appendChild(((AuthorizationImpl) getAuthorization()).toXml(document));
		}
		return element;
	}
}
