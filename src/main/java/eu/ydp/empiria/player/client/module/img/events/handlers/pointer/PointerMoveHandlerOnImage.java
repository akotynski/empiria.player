package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer.PointerMoveHandler;

public class PointerMoveHandlerOnImage implements PointerMoveHandler {

    private final PointerEventsCoordinates pointerEventsCoordinates;

    private final TouchOnImageMoveHandler touchOnMoveHandler;

    @Inject
    public PointerMoveHandlerOnImage(@Assisted final TouchOnImageMoveHandler touchOnMoveHandler, final PointerEventsCoordinates pointerEventsCoordinates) {
        this.touchOnMoveHandler = touchOnMoveHandler;
        this.pointerEventsCoordinates = pointerEventsCoordinates;
    }

    @Override
    public void onPointerMove(PointerMoveEvent event) {
        if (event.isTouchEvent()) {
            event.preventDefault();
            pointerEventsCoordinates.addEvent(event);

            touchOnMoveHandler.onMove(pointerEventsCoordinates.getTouchOnImageEvent());
        }
    }

}
