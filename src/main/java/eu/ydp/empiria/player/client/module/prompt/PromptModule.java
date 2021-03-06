package eu.ydp.empiria.player.client.module.prompt;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;

public class PromptModule extends SimpleModuleBase {

    protected Widget contents;

    @Override
    public void initModule(Element element) {
        contents = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(element);
        contents.setStyleName("qp-prompt");
    }

    @Override
    public Widget getView() {
        return contents;
    }
}
