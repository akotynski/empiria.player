package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class SlideshowModuleStructure extends AbstractModuleStructure<SlideshowPlayerBean, SlideshowJAXBParser> {

    @Inject
    private SlideshowJAXBParser parser;

    @Inject
    private XMLParser xmlParser;

    @Inject
    private IJSONService ijsonService;

    @Override
    public YJsonArray getSavedStructure() {
        return ijsonService.createArray();
    }

    @Override
    protected SlideshowJAXBParser getParserFactory() {
        return parser;
    }

    @Override
    protected void prepareStructure(YJsonArray structure) {
    }

    @Override
    protected XMLParser getXMLParser() {
        return xmlParser;
    }

    @Override
    protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
        return null;
    }
}
