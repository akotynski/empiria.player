package eu.ydp.empiria.player.client.module.accordion.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.accordion.view.section.AccordionSectionView;

public interface AccordionView extends IsWidget{
    void addSection(AccordionSectionView view);
}
