package eu.ydp.empiria.player.client.controller;

import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.controller.session.sockets.ItemSessionSocket;
import eu.ydp.empiria.player.client.controller.session.sockets.PageSessionSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.view.item.ItemViewSocket;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;

import java.util.Map;

public interface AssessmentControllerFactory {

    PageController getPageController(PageViewSocket pvs, IFlowSocket fs, PageSessionSocket pss);

    ItemController getItemController(ItemViewSocket ivs, ItemSessionSocket iss);

    Item getItem(DisplayContentOptions options, ItemOutcomeStorageImpl outcomeStorage, JSONArray stateArray);

    ItemModuleSocket getItemModuleSocket(Item item);

    ItemBody getItemBody(DisplayContentOptions options, ModuleSocket moduleSocket);
}
