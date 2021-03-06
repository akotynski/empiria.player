package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import eu.ydp.empiria.player.client.util.position.Point;

public class TouchOnImageMoveHandler {

    private final CanvasMoveEvents canvasMoveEvents;

    public TouchOnImageMoveHandler(CanvasMoveEvents canvasMoveEvents) {
        this.canvasMoveEvents = canvasMoveEvents;
    }

    public void onMove(TouchOnImageEvent touchOnImageEvent) {
        if (touchOnImageEvent.getLength() == 1) {
            Point finger = touchOnImageEvent.getPoint(0);
            canvasMoveEvents.onMoveMove(finger);
        } else if (touchOnImageEvent.getLength() == 2) {
            Point firstFinger = touchOnImageEvent.getPoint(0);
            Point secondFinger = touchOnImageEvent.getPoint(1);
            canvasMoveEvents.onMoveScale(firstFinger, secondFinger);
        }
    }
}
