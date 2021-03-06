package eu.ydp.empiria.player.client.util.dom.redraw;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Singleton;

import javax.annotation.PostConstruct;

@Singleton
public class ForceRedrawHackImpl implements ForceRedrawHack {

    private final FlowPanel widgetToAdd = new FlowPanel();

    @PostConstruct
    public void postConstruct() {
        widgetToAdd.setVisible(false);
        RootPanel.get().add(widgetToAdd);
    }

    @Override
    public void redraw() {
        widgetToAdd.setVisible(!widgetToAdd.isVisible());
    }
}
