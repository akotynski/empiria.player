package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.Panel;
import eu.ydp.empiria.player.client.controller.Assessment;
import eu.ydp.empiria.player.client.controller.AssessmentBody;
import eu.ydp.empiria.player.client.controller.AssessmentController;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.view.assessment.AssessmentBodyView;
import eu.ydp.empiria.player.client.view.assessment.AssessmentContentView;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewSocket;

public interface AssessmentFactory {
    AssessmentContentView geAssessmentContentView(Panel panel);

    AssessmentController createAssessmentController(AssessmentViewSocket avs, IFlowSocket fsocket);

    Assessment createAssessment(AssessmentData data, DisplayContentOptions options, ModulesRegistrySocket modulesRegistrySocket);

    AssessmentBody createAssessmentBody(DisplayContentOptions options, ModuleSocket moduleSocket, ModulesRegistrySocket modulesRegistrySocket);

    AssessmentBodyView createAssessmentBodyView(WidgetWorkflowListener wwl);
}
