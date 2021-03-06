package eu.ydp.empiria.player.client.module.core.base;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public abstract class SimpleModuleBase extends ModuleBase implements ISimpleModule {

    @Override
    public final void initModule(Element element, ModuleSocket ms, EventsBus eventsBus) {
        initModule(ms, eventsBus);
        readAttributes(element);
        initModule(element);
        applyIdAndClassToView(getView());
    }

    protected abstract void initModule(Element element);
}
