/**
 *
 */
package com.sharing.externalutils.linkedinapi.client.impl;
import java.io.InputStream;

import com.sharing.externalutils.linkedinapi.client.constant.LinkedInApiUrls.LinkedInApiUrlBuilder;
import com.sharing.externalutils.linkedinapi.schema.SchemaElementFactory;

/**
 * @author Nabeel Mukhtar
 *
 */
public class LinkedInApiJaxbClient extends BaseLinkedInApiClient {

    /** Field description */
    private static final String JAXB_PACKAGE_NAME = "com.arbetsformedlingen.externalutils.linkedinapi.schema";
    
    /** Field description */
//    private static final SchemaElementFactory<JAXBElement<?>> OBJECT_FACTORY = new JaxbElementFactory();
//    
//    /** Do not access directly. It may be null!!!. Use {@link #getJaxbContext()} */
//    private static JAXBContext JAXB_CONTEXT;

    /**
     * Constructs ...
     *
     *
     * @param consumerKey
     * @param consumerSecret
     */
    public LinkedInApiJaxbClient(String consumerKey, String consumerSecret) {
        super(consumerKey, consumerSecret);
    }

    /**
     * Method description
     *
     *
     * @param xmlContent
     * @param <T>
     *
     * @return
     */
    @SuppressWarnings("unchecked")
//    protected <T> T unmarshallObject(Class<T> clazz, InputStream xmlContent) {
////        try {
////            Unmarshaller u  = getJaxbContext().createUnmarshaller();
////
////            return (T) u.unmarshal(xmlContent);
////        } catch (JAXBException e) {
////            throw new LinkedInApiClientException(e);
////        }
//    }

    /**
     * Method description
     *
     *
     * @param element
     *
     * @return
     */
//    protected String marshallObject(Object element) {
////        try {
////            StringWriter writer = new StringWriter();
////            Marshaller   m = getJaxbContext().createMarshaller();
////
////            m.marshal(element, writer);
////
////            return writer.toString();
////        } catch (JAXBException e) {
////            throw new LinkedInApiClientException(e);
////        }
//    }

    /**
     * Method description
     *
     * @return
//     */
//    protected SchemaElementFactory<?> createObjectFactory() {
//    	return OBJECT_FACTORY;
//    }
    
    /**
     * Method description
     *
     *
     * @param urlFormat
     *
     * @return
     */
    protected LinkedInApiUrlBuilder createLinkedInApiUrlBuilder(String urlFormat) {
        return new LinkedInApiUrlBuilder(urlFormat);
    }

	@Override
	protected SchemaElementFactory<?> createObjectFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String marshallObject(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T> T unmarshallObject(Class<T> clazz, InputStream xmlContent) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * Method description
     *
     */
//	protected JAXBContext getJaxbContext() throws JAXBException {
//		if (JAXB_CONTEXT == null ) {
//			JAXB_CONTEXT = JAXBContext.newInstance(JAXB_PACKAGE_NAME);
//		}
//		return JAXB_CONTEXT;
//	}
//
//    /**
//     * Method description
//     *
//     */
//	protected void setJaxbContext(JAXBContext context) {
//		JAXB_CONTEXT = context;
//	}
//	
//	private static class JaxbElementFactory extends ObjectFactory implements SchemaElementFactory<JAXBElement<?>> {
//		public JaxbElementFactory() {
//			super();
//		}
//	}
}
