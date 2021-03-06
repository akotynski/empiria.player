package eu.ydp.empiria.player.client.module.img.explorable.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.img.explorable.ExplorableImgWindow;
import eu.ydp.empiria.player.client.module.img.explorable.ExplorableImgWindowProvider;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ExplorableImgContentViewImpl extends Composite implements ExplorableImgContentView {

    private static ExplorableImgContentUiBinder uiBinder = GWT.create(ExplorableImgContentUiBinder.class);
    @UiField(provided = true)
    ExplorableImgWindow window;
    @UiField
    FlowPanel mainPanel;
    @UiField
    FlowPanel windowPanel;
    @UiField
    PushButton zoominButton;
    @UiField
    PushButton zoomoutButton;
    @UiField
    FlowPanel toolbox;

    @Inject
    public ExplorableImgContentViewImpl(ExplorableImgWindowProvider explorableImgWindowProvider) {
        window = explorableImgWindowProvider.get();
        initUiBinder();
    }

    private void initUiBinder() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void init(Element element, ModuleSocket moduleSocket, ImageProperties properties) {
        Element titleNodes = XMLUtils.getFirstElementWithTagName(element, EmpiriaTagConstants.ATTR_TITLE);
        final String title = XMLUtils.getTextFromChilds(titleNodes);
        window.init(element.getAttribute(EmpiriaTagConstants.ATTR_SRC), properties, title);
    }

    @Override
    public void zoomIn() {
        window.zoomIn();
    }

    @Override
    public void zoomOut() {
        window.zoomOut();
    }

    @Override
    public void registerCommandOnToolbox(EventHandlerProxy handler) {
        handler.apply(toolbox);
    }

    @Override
    public void registerZoomInButtonCommands(EventHandlerProxy handler) {
        handler.apply(zoominButton);
    }

    @Override
    public void registerZoomOutButtonCommands(EventHandlerProxy handler) {
        handler.apply(zoomoutButton);
    }

    interface ExplorableImgContentUiBinder extends UiBinder<Widget, ExplorableImgContentViewImpl> {
    }

}
