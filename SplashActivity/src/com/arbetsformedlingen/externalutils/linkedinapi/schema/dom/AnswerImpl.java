
package com.arbetsformedlingen.externalutils.linkedinapi.schema.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.arbetsformedlingen.externalutils.linkedinapi.schema.Answer;
import com.arbetsformedlingen.externalutils.linkedinapi.schema.Author;

public class AnswerImpl
	extends BaseSchemaEntity
    implements Answer
{

    private final static long serialVersionUID = 2461660169443089969L;
    protected String id;
    protected String webUrl;
    protected AuthorImpl author;

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String value) {
        this.webUrl = value;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author value) {
        this.author = ((AuthorImpl) value);
    }
    
	@Override
	public void init(Element element) {
		setId(DomUtils.getElementValueFromNode(element, "id"));
		setWebUrl(DomUtils.getElementValueFromNode(element, "web-url"));
		
		Element authorElem = (Element) DomUtils.getChildElementByName(element, "author");
		if (authorElem != null) {
			AuthorImpl author = new AuthorImpl();
			author.init(authorElem);
			setAuthor(author);
		}
	}

	@Override
	public Element toXml(Document document) {
		Element element = document.createElement("answer");
		DomUtils.setElementValueToNode(element, "id", getId());
		DomUtils.setElementValueToNode(element, "web-url", getWebUrl());
		if (getAuthor() != null) {
			element.appendChild(((AuthorImpl) getAuthor()).toXml(document));
		}
		return element;
	}
}
