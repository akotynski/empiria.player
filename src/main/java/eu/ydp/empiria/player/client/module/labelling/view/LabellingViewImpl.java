package eu.ydp.empiria.player.client.module.labelling.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import eu.ydp.empiria.player.client.module.labelling.structure.ImgBean;

public class LabellingViewImpl extends Composite implements LabellingView {

    private static LabellingViewImplUiBinder uiBinder = GWT.create(LabellingViewImplUiBinder.class);

    interface LabellingViewImplUiBinder extends UiBinder<Widget, LabellingViewImpl> {
    }

    @UiField
    AbsolutePanel inner;
    @UiField
    AbsolutePanel container;
    @UiField
    Image image;

    public LabellingViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setBackground(ImgBean imgBean) {
        image.setUrl(imgBean.getSrc());
        setSize(imgBean.getWidth(), imgBean.getHeight());
        inner.getElement().getStyle().setOverflow(Style.Overflow.VISIBLE);
        container.getElement().getStyle().setOverflow(Style.Overflow.VISIBLE);
    }

    private void setSize(int width, int height) {
        String px = Unit.PX.toString().toLowerCase();
        inner.setWidth(width + px);
        inner.setHeight(height + px);
        container.setWidth(width + px);
        container.setHeight(height + px);
    }

    @Override
    public void addChild(IsWidget widget, int left, int top) {
        container.add(widget, left, top);
    }

    @Override
    public IsWidget getView() {
        return this;
    }

    @Override
    public HasWidgets.ForIsWidget getContainer() {
        return container;
    }

    @Override
    public void setViewId(String id) {
        this.getElement().setId(id);
    }

}
