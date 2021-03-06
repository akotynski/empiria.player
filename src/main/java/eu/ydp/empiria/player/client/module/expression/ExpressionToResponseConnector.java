package eu.ydp.empiria.player.client.module.expression;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;

import java.util.List;

public class ExpressionToResponseConnector {

    private static final Logger LOGGER = new Logger();
    private IdentifiersFromExpressionExtractor identifiersFromExpressionExtractor;

    @Inject
    public ExpressionToResponseConnector(IdentifiersFromExpressionExtractor identifiersFromExpressionExtractor) {
        this.identifiersFromExpressionExtractor = identifiersFromExpressionExtractor;
    }

    public void connectResponsesToExpression(ExpressionBean expressionBean, ItemResponseManager responseManager) {
        String template = expressionBean.getTemplate();
        List<String> identifiers = identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(template);

        for (String responseId : identifiers) {
            Response response = responseManager.getVariable(responseId);

            if (response == null) {
                String message = "Expression: " + template + " is using identifier: " + responseId + " that is not existing in responsesMap!";
                LOGGER.info(message);
            } else {
                expressionBean.getResponses().add(response);
                response.setExpression(expressionBean);
            }
        }
    }

}
