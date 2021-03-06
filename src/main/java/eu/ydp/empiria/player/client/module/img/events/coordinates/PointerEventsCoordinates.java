package eu.ydp.empiria.player.client.module.img.events.coordinates;

import com.google.gwt.event.shared.EventHandler;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerEvent;
import eu.ydp.empiria.player.client.util.position.Point;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class PointerEventsCoordinates {

    private final Map<Integer, Point> pointersPoints = new LinkedHashMap<>();

    public void addEvent(PointerEvent<? extends EventHandler> pointerEvent) {
        if (isFirstTouch(pointerEvent)) {
            pointersPoints.clear();
        }
        Point coordinates = new Point(pointerEvent.getClientX(), pointerEvent.getClientY());
        pointersPoints.put(pointerEvent.getPointerId(), coordinates);
    }

    public Point getPoint(int index) {
        return valuesAsList().get(index);
    }

    public void removeEvent(PointerEvent<?> pointerEvent) {
        pointersPoints.remove(pointerEvent.getPointerId());
    }

    public int getLength() {
        return pointersPoints.size();
    }

    public TouchOnImageEvent getTouchOnImageEvent() {
        return new TouchOnImageEvent(valuesAsList());
    }

    public boolean isOnePointer() {
        return pointersPoints.size() == 1;
    }

    public boolean isEmpty() {
        return pointersPoints.isEmpty();
    }

    private boolean isFirstTouch(PointerEvent<? extends EventHandler> pointerEvent) {
        return pointerEvent.isPrimary() && !pointersPoints.containsKey(pointerEvent.getPointerId());
    }

    private List<Point> valuesAsList() {
        return new ArrayList<>(pointersPoints.values());
    }
}
