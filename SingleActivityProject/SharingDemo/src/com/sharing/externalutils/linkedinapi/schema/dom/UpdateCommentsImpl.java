
package com.sharing.externalutils.linkedinapi.schema.dom;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sharing.externalutils.linkedinapi.schema.UpdateComment;
import com.sharing.externalutils.linkedinapi.schema.UpdateComments;

public class UpdateCommentsImpl
	extends BaseSchemaEntity
    implements UpdateComments
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6923754393295869466L;
	protected List<UpdateComment> updateCommentList;
    protected Long total;

    public List<UpdateComment> getUpdateCommentList() {
        if (updateCommentList == null) {
            updateCommentList = new ArrayList<UpdateComment>();
        }
        return this.updateCommentList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long value) {
        this.total = value;
    }
    
	@Override
	public void init(Element element) {
		setTotal(DomUtils.getAttributeValueAsLongFromNode(element, "total"));
		List<Element> updateComments = DomUtils.getChildElementsByLocalName(element, "update-comment");
		for (Element updateComment : updateComments) {
			UpdateCommentImpl updateCommentImpl = new UpdateCommentImpl();
			updateCommentImpl.init(updateComment);
			getUpdateCommentList().add(updateCommentImpl);
		}
	}

	@Override
	public Element toXml(Document document) {
		Element element = document.createElement("update-comments");
		DomUtils.setAttributeValueToNode(element, "total", getTotal());
		for (UpdateComment updateComment : getUpdateCommentList()) {
			element.appendChild(((UpdateCommentImpl) updateComment).toXml(document));
		}
		return element;
	}
}
