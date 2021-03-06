package eu.ydp.empiria.player.client.module.ordering.drag;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;

@Singleton
public class Sortable {

    @Inject
    private SortableNative sortableNative;

    public void init(String id, OrderInteractionOrientation orderInteractionOrientation, SortCallback callback) {
        sortableNative.init(id, orderInteractionOrientation.getAxis(), callback);
    }

    public void enable(String id) {
        sortableNative.enable(id);
    }

    public void disable(String id) {
        sortableNative.disable(id);
    }
}
