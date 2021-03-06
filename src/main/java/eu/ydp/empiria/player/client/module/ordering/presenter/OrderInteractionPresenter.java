package eu.ydp.empiria.player.client.module.ordering.presenter;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;

import java.util.List;

public interface OrderInteractionPresenter extends ActivityPresenter<OrderInteractionModuleModel, OrderInteractionBean> {

    void updateItemsOrder(List<String> itemsOrder);

    OrderInteractionOrientation getOrientation();
}
