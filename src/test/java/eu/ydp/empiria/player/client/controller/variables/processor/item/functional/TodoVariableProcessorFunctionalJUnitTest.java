package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import org.junit.Test;

import java.util.Map;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.TODO;

public class TodoVariableProcessorFunctionalJUnitTest extends VariableProcessorFunctionalTestBase {

    @Test
    public void shouldCorrectlyCountTodoInCorrectAnswerCountMode() throws Exception {
        // given
        Response responseWithCorrectAnswersCountMode = new ResponseBuilder().withCardinality(Cardinality.MULTIPLE)
                .withCorrectAnswers("firstAnswer", "secondAnswer", "thirdAnswer").withCountMode(CountMode.CORRECT_ANSWERS).build();

        Map<String, Response> responsesMap = convertToMap(responseWithCorrectAnswersCountMode);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // then
        Integer expectedTODOCount = responseWithCorrectAnswersCountMode.correctAnswers.getAnswersCount();
        String expectedTODOVariableValue = expectedTODOCount.toString();
        assertGlobalOutcomesHaveValue(Lists.newArrayList(expectedTODOVariableValue), Lists.newArrayList(TODO), outcomes);
        assertResponseRelatedOutcomesHaveValue(responseWithCorrectAnswersCountMode, Lists.newArrayList(expectedTODOVariableValue), Lists.newArrayList(TODO),
                outcomes);
    }

    @Test
    public void shouldCorrectlyCountTodoInSingleCountMode() throws Exception {
        // given
        Response responseWithSingleCountMode = new ResponseBuilder().withCardinality(Cardinality.SINGLE)
                .withCorrectAnswers("firstAnswer", "secondAnswer", "thirdAnswer").withCountMode(CountMode.SINGLE).build();

        Map<String, Response> responsesMap = convertToMap(responseWithSingleCountMode);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
        ProcessingMode processingMode = ProcessingMode.USER_INTERACT;

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // then
        String expectedTODOVariableValue = "1";
        assertGlobalOutcomesHaveValue(Lists.newArrayList(expectedTODOVariableValue), Lists.newArrayList(TODO), outcomes);
        assertResponseRelatedOutcomesHaveValue(responseWithSingleCountMode, Lists.newArrayList(expectedTODOVariableValue), Lists.newArrayList(TODO), outcomes);
    }

}
