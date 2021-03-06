package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;

public abstract class SimpleContainerModuleBase extends AbstractActivityContainerModuleBase {

    private Panel panel;

    public SimpleContainerModuleBase() {
        this.panel = new FlowPanel();
    }

    public SimpleContainerModuleBase(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void initModule(Element element) {
        readAttributes(element);
        applyIdAndClassToView(getView());

        getBodyGenerator().generateBody(element, panel);
    }

    @Override
    public Widget getView() {
        return panel;
    }

    protected Panel getContainer() {
        return panel;
    }

    protected void setContainerStyleName(String styleName) {
        panel.setStyleName(styleName);
    }

}
