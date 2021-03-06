
package com.arbetsformedlingen.externalutils.linkedinapi.schema.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.arbetsformedlingen.externalutils.linkedinapi.schema.Error;

public class ErrorImpl
    extends BaseSchemaEntity
    implements Error
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7306088000916275288L;
	protected Long status;
    protected Long timestamp;
    protected String errorCode;
    protected String message;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long value) {
        this.status = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long value) {
        this.timestamp = value;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

	@Override
	public void init(Element element) {
		setStatus(DomUtils.getElementValueAsLongFromNode(element, "status"));
		setTimestamp(DomUtils.getElementValueAsLongFromNode(element, "timestamp"));
		setErrorCode(DomUtils.getElementValueFromNode(element, "error-code"));
		setMessage(DomUtils.getElementValueFromNode(element, "message"));
	}

	@Override
	public Element toXml(Document document) {
		Element element = document.createElement("error");
		DomUtils.setElementValueToNode(element, "status", getStatus());
		DomUtils.setElementValueToNode(element, "timestamp", getTimestamp());
		DomUtils.setElementValueToNode(element, "error-code", getErrorCode());
		DomUtils.setElementValueToNode(element, "message", getMessage());
		return element;
	}
}
