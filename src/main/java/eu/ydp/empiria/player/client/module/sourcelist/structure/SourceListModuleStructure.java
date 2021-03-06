package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

import java.util.List;

public class SourceListModuleStructure extends AbstractModuleStructure<SourceListBean, SourceListJAXBParser> {

    @Inject
    SourceListJAXBParser parser;

    @Inject
    XMLParser xmlParser;
    @Inject
    private IJSONService ijsonService;

    @Override
    protected SourceListJAXBParser getParserFactory() {
        return parser;
    }

    @Override
    protected void prepareStructure(YJsonArray structure) {
        if (getBean().isShuffle()) {
            shuffle();
        }
    }

    protected void shuffle() {
        ShuffleHelper shuffleHelper = new ShuffleHelper();
        List<SimpleSourceListItemBean> randomizeList = shuffleHelper.randomizeIfShould(bean, bean.getSimpleSourceListItemBeans());
        bean.setSimpleSourceListItemBeans(randomizeList);
    }

    @Override
    protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
        return null;
    }

    @Override
    protected XMLParser getXMLParser() {
        return xmlParser;
    }

    @Override
    public YJsonArray getSavedStructure() {
        return ijsonService.createArray();
    }

}
