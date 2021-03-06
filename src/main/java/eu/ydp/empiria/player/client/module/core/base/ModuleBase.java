package eu.ydp.empiria.player.client.module.core.base;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public abstract class ModuleBase extends ParentedModuleBase {

    private String moduleId;
    private String moduleClass;
    private EventsBus eventsBus;

    @Override
    protected final void initModule(ModuleSocket moduleSocket) {
        super.initModule(moduleSocket);
    }

    public void initModule(ModuleSocket moduleSocket, EventsBus eventsBus) {
        this.eventsBus = eventsBus;
        initModule(moduleSocket);
    }

    protected final void readAttributes(Element element) {

        String className = element.getAttribute("class");
        if (className != null && !"".equals(className)) {
            moduleClass = className;
        } else {
            moduleClass = "";
        }
        String id = element.getAttribute("id");
        if (id != null && !"".equals(id)) {
            moduleId = id;
        } else {
            moduleId = "";
        }
    }

    protected String getModuleId() {
        return moduleId;
    }

    protected String getModuleClass() {
        return moduleClass;
    }

    protected final void applyIdAndClassToView(Widget widget) {
        if (widget != null) {
            if (getModuleId() != null && !"".equals(getModuleId().trim()))
                widget.getElement().setId(getModuleId());
            if (getModuleClass() != null && !"".equals(getModuleClass().trim()))
                widget.addStyleName(getModuleClass());
        }
    }

    protected EventsBus getEventsBus() {
        return eventsBus;
    }
}
