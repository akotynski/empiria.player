package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class PlayerContainersAccessor implements IPlayerContainersAccessor {

    private Map<Integer, HasWidgets> itemContainers = new HashMap<Integer, HasWidgets>();
    private HasWidgets playerContainer;

    @Override
    public void registerItemBodyContainer(int itemIndex, HasWidgets container) {
        this.itemContainers.put(itemIndex, container);
    }

    @Override
    public HasWidgets getItemBodyContainer(int itemIndex) {
        return itemContainers.get(itemIndex);
    }

    @Override
    public void setPlayerContainer(HasWidgets playerContainer) {
        this.playerContainer = playerContainer;
    }

    @Override
    public HasWidgets getPlayerContainer() {
        return playerContainer;
    }

}
