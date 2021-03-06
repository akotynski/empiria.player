package eu.ydp.empiria.player.client.module.slideshow.view.slide;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class SlideViewImpl extends Composite implements SlideView {

    @UiTemplate("SlideView.ui.xml")
    interface SlideViewUiBinder extends UiBinder<Widget, SlideViewImpl> {
    }

    ;

    private static SlideViewUiBinder slideWidgetBinder = GWT.create(SlideViewUiBinder.class);

    @UiField
    public Image image;

    @UiField
    FlowPanel titlePanel;

    @UiField
    FlowPanel narrationPanel;

    public SlideViewImpl() {
        initWidget(slideWidgetBinder.createAndBindUi(this));
    }

    @Override
    public void setSlideTitle(Widget title) {
        titlePanel.add(title);
    }

    @Override
    public void setNarration(Widget narration) {
        narrationPanel.add(narration);
    }

    @Override
    public void setImage(String src) {
        image.setUrl(src);
    }

    @Override
    public void clearTitle() {
        titlePanel.clear();
    }

    @Override
    public void clearNarration() {
        narrationPanel.clear();
    }
}
