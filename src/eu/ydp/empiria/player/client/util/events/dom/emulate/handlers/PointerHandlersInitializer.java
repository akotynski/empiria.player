package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.gin.factory.TouchHandlerFactory;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;

public class PointerHandlersInitializer implements ITouchHandlerInitializer {

	@Inject
	private TouchHandlerFactory touchHandlerFactory;

	@Override
	public void addTouchMoveHandler(final TouchOnMoveHandler touchOnMoveHandler, Widget listenOn) {
		listenOn.addDomHandler(touchHandlerFactory.createPointerMoveHandlerImpl(touchOnMoveHandler), PointerMoveEvent.getType());
	}

	@Override
	public void addTouchStartHandler(final TouchOnStartHandler touchStartHandler, Widget listenOn) {
		listenOn.addDomHandler(touchHandlerFactory.createPointerDownHandlerImpl(touchStartHandler), PointerDownEvent.getType());
	}

	@Override
	public void addTouchEndHandler(final TouchOnEndHandler touchEndHandler, Widget listenOn) {
		listenOn.addDomHandler(touchHandlerFactory.createPointerUpHandlerImpl(touchEndHandler), PointerUpEvent.getType());
	}

	@Override
	public void addTouchCancelHandler(final TouchOnCancelHandler touchCancelHandler, Widget listenOn) {
	}
}
