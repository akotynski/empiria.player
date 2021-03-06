package eu.ydp.empiria.player.client.module.connection.structure;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.structure.InteractionModuleBean;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "matchInteraction")
@XmlAccessorType(XmlAccessType.FIELD)
public class MatchInteractionBean extends InteractionModuleBean implements MultiplePairBean<SimpleAssociableChoiceBean> {

    @XmlAttribute
    private int maxAssociations;

    @XmlAttribute
    private boolean shuffle;

    @XmlElement(name = "simpleMatchSet")
    private List<SimpleMatchSetBean> simpleMatchSets;

    private final Map<String, SimpleAssociableChoiceBean> flatChoicesMap;

    public MatchInteractionBean() {
        simpleMatchSets = new ArrayList<SimpleMatchSetBean>();
        flatChoicesMap = new HashMap<String, SimpleAssociableChoiceBean>();
    }

    private Map<String, SimpleAssociableChoiceBean> getFlatChoicesMap() {
        if (flatChoicesMap.size() <= 0) {
            for (SimpleAssociableChoiceBean choice : getSourceChoicesSet()) {
                flatChoicesMap.put(choice.getIdentifier(), choice);
            }
            for (SimpleAssociableChoiceBean choice : getTargetChoicesSet()) {
                flatChoicesMap.put(choice.getIdentifier(), choice);
            }
        }
        return flatChoicesMap;
    }

    @Override
    public int getMaxAssociations() {
        return maxAssociations;
    }

    public void setMaxAssociations(int maxAssociations) {
        this.maxAssociations = maxAssociations;
    }

    @Override
    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public List<SimpleMatchSetBean> getSimpleMatchSets() {
        return simpleMatchSets;
    }

    public void setSimpleMatchSets(List<SimpleMatchSetBean> simpleMatchSets) {
        this.simpleMatchSets = simpleMatchSets;
    }

    @Override
    public List<SimpleAssociableChoiceBean> getSourceChoicesSet() {
        return simpleMatchSets.get(0).getSimpleAssociableChoices();
    }

    public List<String> getSourceChoicesIdentifiersSet() {
        return getChoicesIdentifiersSet(getSourceChoicesSet());
    }

    @Override
    public List<SimpleAssociableChoiceBean> getTargetChoicesSet() {
        return simpleMatchSets.get(1).getSimpleAssociableChoices();
    }

    public List<String> getTargetChoicesIdentifiersSet() {
        return getChoicesIdentifiersSet(getTargetChoicesSet());
    }

    private List<String> getChoicesIdentifiersSet(List<SimpleAssociableChoiceBean> choices) {
        ArrayList<String> identifiersSet = new ArrayList<String>();

        for (SimpleAssociableChoiceBean simpleAssociableChoiceBean : choices) {
            identifiersSet.add(simpleAssociableChoiceBean.getIdentifier());
        }

        return identifiersSet;
    }

    @Override
    public SimpleAssociableChoiceBean getChoiceByIdentifier(String sourceItem) {
        return getFlatChoicesMap().get(sourceItem);
    }

    @Override
    public int getRightItemIndex(PairChoiceBean bean) {
        int index = getTargetChoicesSet().indexOf(bean);
        return index;
    }

    @Override
    public int getLeftItemIndex(PairChoiceBean bean) {
        int index = getSourceChoicesSet().indexOf(bean);
        return index;
    }

    @Override
    public boolean isLeftItem(PairChoiceBean bean) {
        int leftIndex = getLeftItemIndex(bean);
        boolean isLeft = (leftIndex != -1);
        return isLeft;
    }
}
