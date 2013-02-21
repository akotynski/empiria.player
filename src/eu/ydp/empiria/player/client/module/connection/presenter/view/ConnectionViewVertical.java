package eu.ydp.empiria.player.client.module.connection.presenter.view;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.ObjectArrays;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.util.events.Event.Type;
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchTypes;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class ConnectionViewVertical extends AbstractConnectionView {

	private static ConnectionViewUiBinder uiBinder = GWT.create(ConnectionViewUiBinder.class);

	interface ConnectionViewUiBinder extends UiBinder<Widget, ConnectionViewVertical> {
	}

	@UiField
	protected FlowPanel leftColumn;

	@UiField
	protected FlowPanel centerColumn;

	@UiField
	protected FlowPanel rightColumn;

	@UiField
	protected FlowPanel view;

	Map<String, String> errorStyles = null, correctStyles = null;

	protected HasTouchHandlers touchRecognition;

	@PostConstruct
	public void createAndBindUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void initTouchRecognition() {
		if (touchRecognition == null) {
			touchRecognition = touchRecognitionFactory.getTouchRecognition(this, !UserAgentChecker.isMobileUserAgent(), true);
			touchRecognition.addTouchHandlers(this, getTouchTypes());
		}
	}

	private Type<TouchHandler, TouchTypes>[] getTouchTypes() {
		Type<TouchHandler, TouchTypes>[] types = TouchEvent.getTypes(TouchTypes.TOUCH_START, TouchTypes.TOUCH_END);
		if (isDrawFollowTouch()) {
			types = ObjectArrays.concat(types, TouchEvent.getType(TouchTypes.TOUCH_MOVE));
		}
		return types;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView
	 * #addFirstColumnItem(eu.ydp.empiria.player.client.module.connection.item.
	 * ConnectionItem)
	 */

	@Override
	public void addFirstColumnItem(ConnectionItem item) {
		leftColumn.add(item);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView
	 * #addSecondColumnItem(eu.ydp.empiria.player.client.module.connection.item.
	 * ConnectionItem)
	 */

	@Override
	public void addSecondColumnItem(ConnectionItem item) {
		rightColumn.add(item);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView
	 * #addElementToMainView(com.google.gwt.user.client.ui.Widget)
	 */

	@Override
	public void addElementToMainView(Widget widget) {
		view.insert(widget, 0);
	}

	@Override
	public void onTouchStart(NativeEvent event) {
		ConnectionMoveStartEvent connectionEvent = new ConnectionMoveStartEvent(getPositionX(event), getPositionY(event), event, null);
		callOnMoveStartHandlers(connectionEvent);

	}

	@Override
	public void onTouchEnd(NativeEvent event) {
		ConnectionMoveEndEvent connectionMoveEndEvent = new ConnectionMoveEndEvent(getPositionX(event), getPositionY(event), event);
		callOnMoveEndHandlers(connectionMoveEndEvent);
	}

	@Override
	protected FlowPanel getView() {
		return view;
	}

	@Override
	public Widget asWidget() {
		initTouchRecognition();
		return this;
	}
}
