package eu.ydp.empiria.player.client.module.abstractmodule.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.structure.ModuleBean;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.xml.XMLParser;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractModuleStructure<M extends ModuleBean, P extends JAXBParserFactory<M>> {

    protected M bean;

    protected Map<String, Element> feedbacks;

    public void createFromXml(String xml, YJsonArray structure) {
        bean = parse(xml);
        prepareStructure(structure);
        prepareFeedbackNodes(xml);
    }

    public abstract YJsonArray getSavedStructure();

    public M getBean() {
        return bean;
    }

    private M parse(String xml) {
        return getParser().parse(xml);
    }

    protected JAXBParser<M> getParser() {
        return getParserFactory().create();
    }

    protected abstract P getParserFactory();

    /**
     * Operates on interaction field ie. for randomize elements, etc.
     */
    protected abstract void prepareStructure(YJsonArray structure);

    protected abstract XMLParser getXMLParser();

    /**
     * Prepares feedbacks map. Needs to be extended in inheriting module
     *
     * @param xml
     */
    protected final void prepareFeedbackNodes(String xml) {
        feedbacks = new HashMap<String, Element>();
        Document xmlDocument = getXMLParser().parse(xml);
        NodeList nodes = getParentNodesForFeedbacks(xmlDocument);

        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {
                Element choiceNode = (Element) nodes.item(i);
                Element feedbackNode = getInlineFeedbackNode(choiceNode);

                addFeedbackNode(feedbackNode);
            }
        }
    }

    protected abstract NodeList getParentNodesForFeedbacks(Document xmlDocument);

    protected final void addFeedbackNode(Element feedbackNode) {
        if (feedbackNode != null) {
            String indentifier = feedbackNode.getAttribute(EmpiriaTagConstants.ATTR_IDENTIFIER);
            feedbacks.put(indentifier, feedbackNode);
        }
    }

    protected final Element getInlineFeedbackNode(Element parentNode) {
        Element feedbackElement = null;
        NodeList feedbackElements = parentNode.getElementsByTagName(EmpiriaTagConstants.NAME_FEEDBACK_INLINE);

        if (feedbackElements != null && feedbackElements.getLength() > 0) {
            feedbackElement = (Element) feedbackElements.item(0);
        }

        return feedbackElement;
    }

    public Element getFeedbackElement(String identifer) {
        return feedbacks.get(identifer);
    }
}
