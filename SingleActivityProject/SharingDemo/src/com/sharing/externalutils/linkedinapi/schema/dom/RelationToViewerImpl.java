
package com.sharing.externalutils.linkedinapi.schema.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sharing.externalutils.linkedinapi.schema.RelationToViewer;

public class RelationToViewerImpl
    extends BaseSchemaEntity
    implements RelationToViewer
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5385043041125747824L;
	protected Long distance;

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long value) {
        this.distance = value;
    }

	@Override
	public void init(Element element) {
		setDistance(DomUtils.getElementValueAsLongFromNode(element, "distance"));
	}

	@Override
	public Element toXml(Document document) {
		Element element = document.createElement("relation-to-viewer");
		DomUtils.setElementValueToNode(element, "distance", getDistance());
		return element;
	}
}
