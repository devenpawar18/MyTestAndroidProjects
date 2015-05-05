
package com.sharing.externalutils.linkedinapi.schema;



/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}id"/>
 *         &lt;element ref="{}recommendation-type"/>
 *         &lt;element ref="{}recommendation-snippet"/>
 *         &lt;choice>
 *           &lt;element ref="{}recommender"/>
 *           &lt;element ref="{}recommendee"/>
 *         &lt;/choice>
 *         &lt;element ref="{}web-url" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public interface Recommendation
    extends SchemaEntity
{


    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getId();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setId(String value);

    /**
     * Gets the value of the recommendationType property.
     * 
     * @return
     *     possible object is
     *     {@link RecommendationType }
     *     
     */
    RecommendationType getRecommendationType();

    /**
     * Sets the value of the recommendationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecommendationType }
     *     
     */
    void setRecommendationType(RecommendationType value);

    /**
     * Gets the value of the recommendationSnippet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getRecommendationSnippet();

    /**
     * Sets the value of the recommendationSnippet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setRecommendationSnippet(String value);

    /**
     * Gets the value of the recommender property.
     * 
     * @return
     *     possible object is
     *     {@link Recommender }
     *     
     */
    Recommender getRecommender();

    /**
     * Sets the value of the recommender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Recommender }
     *     
     */
    void setRecommender(Recommender value);

    /**
     * Gets the value of the recommendee property.
     * 
     * @return
     *     possible object is
     *     {@link Recommendee }
     *     
     */
    Recommendee getRecommendee();

    /**
     * Sets the value of the recommendee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Recommendee }
     *     
     */
    void setRecommendee(Recommendee value);

    /**
     * Gets the value of the webUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getWebUrl();

    /**
     * Sets the value of the webUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setWebUrl(String value);

}
