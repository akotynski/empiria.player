package eu.ydp.empiria.player.client.module.connection.presenter.view;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartHandler;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchHandler;
import eu.ydp.empiria.player.client.util.position.PositionHelper;

public abstract class AbstractConnectionView extends Composite implements ConnectionView, TouchHandler {
	private final Set<ConnectionMoveHandler> handlers = new HashSet<ConnectionMoveHandler>();
	private final Set<ConnectionMoveEndHandler> endMoveHandlers = new HashSet<ConnectionMoveEndHandler>();
	private final Set<ConnectionMoveStartHandler> startMoveHandlers = new HashSet<ConnectionMoveStartHandler>();
	protected final EventsBus eventsBus;
	private final PositionHelper positionHelper;

	protected final TouchRecognitionFactory touchRecognitionFactory;

	public AbstractConnectionView(EventsBus eventsBus, PositionHelper positionHelper, TouchRecognitionFactory touchRecognitionFactory) {
		this.eventsBus = eventsBus;
		this.positionHelper = positionHelper;
		this.touchRecognitionFactory = touchRecognitionFactory;
		createAndBindUi();
	}

	@Override
	public void addElementToMainView(Widget widget) {
		getView().add(widget);
	}

	@Override
	public void addConnectionMoveHandler(ConnectionMoveHandler handler) {
		handlers.add(handler);
	}

	@Override
	public void addConnectionMoveEndHandler(ConnectionMoveEndHandler handler) {
		endMoveHandlers.add(handler);
	}

	@Override
	public void addConnectionMoveStartHandler(ConnectionMoveStartHandler handler) {
		startMoveHandlers.add(handler);
	}

	protected void callOnMoveHandlers(ConnectionMoveEvent event) {
		for (ConnectionMoveHandler handler : handlers) {
			handler.onConnectionMove(event);
		}
	}

	protected void callOnMoveEndHandlers(ConnectionMoveEndEvent event) {
		for (ConnectionMoveEndHandler handler : endMoveHandlers) {
			handler.onConnectionMoveEnd(event);
		}
	}

	protected void callOnMoveStartHandlers(ConnectionMoveStartEvent event) {
		for (ConnectionMoveStartHandler handler : startMoveHandlers) {
			handler.onConnectionStart(event);
		}
	}

	public void onTouchMove(NativeEvent event) {
		if (getView() != null) {
			callOnMoveHandlers(new ConnectionMoveEvent(getPositionX(event), getPositionY(event), event));
		}
	}

	public void addFirstColumnItems(Collection<ConnectionItem> items) {
		for (ConnectionItem item : items) {
			addFirstColumnItem(item);
		}
	}

	public void addSecondColumnItems(Collection<ConnectionItem> items) {
		for (ConnectionItem item : items) {
			addSecondColumnItem(item);
		}
	}

	protected int getPositionX(NativeEvent event) {
		return positionHelper.getPositionX(event, getView().getElement());
	}

	protected int getPositionY(NativeEvent event) {
		return positionHelper.getPositionY(event, getView().getElement());
	}

	@Override
	public void onTouchEvent(TouchEvent event) {
		switch (event.getType()) {
		case TOUCH_START:
			onTouchStart(event.getNativeEvent());
			break;
		case TOUCH_END:
			onTouchEnd(event.getNativeEvent());
			break;
		case TOUCH_MOVE:
			onTouchMove(event.getNativeEvent());
			break;
		default:
			break;
		}

	}

	public abstract void createAndBindUi();

	public abstract FlowPanel getView();

	@Override
	public abstract void addFirstColumnItem(ConnectionItem item);

	@Override
	public abstract void addSecondColumnItem(ConnectionItem item);

	public abstract void onTouchStart(NativeEvent event);

	public abstract void onTouchEnd(NativeEvent event);
}