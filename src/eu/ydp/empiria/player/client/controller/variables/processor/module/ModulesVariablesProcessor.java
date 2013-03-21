package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

public class ModulesVariablesProcessor {

	private final ResponseChangesFinder responseChangesFinder;

	private final ModulesConstantVariablesInitializer constantVariablesInitializer;
	private final ModulesProcessingResults processingResults;
	private final ResponseVariablesProcessor responseVariablesProcessor;

	@Inject
	public ModulesVariablesProcessor(
			ResponseChangesFinder responseChangesFinder, 
			ModulesConstantVariablesInitializer constantVariablesInitializer,
			ResponseVariablesProcessor responseVariablesProcessor,
			ModulesProcessingResults processingResults) {
		this.responseChangesFinder = responseChangesFinder;
		this.constantVariablesInitializer = constantVariablesInitializer;
		this.responseVariablesProcessor = responseVariablesProcessor;
		this.processingResults = processingResults;
	}

	public void initialize(Map<String, Response> responses){
		constantVariablesInitializer.initializeTodoVariables(responses, processingResults);
	}
	
	public ModulesProcessingResults processVariablesForResponses(Map<String, Response> responses, ProcessingMode processingMode) {
		List<DtoProcessedResponse> processedResponses = responseChangesFinder.findChangesOfAnswers(processingResults, responses);
		processVariablesForResponses(processedResponses, processingMode);
		return processingResults;
	}

	private void processVariablesForResponses(List<DtoProcessedResponse> changedResponses, ProcessingMode processingMode) {
		for (DtoProcessedResponse processedResponse : changedResponses) {
			processVariablesForResponse(processingMode, processedResponse);
		}
	}

	private void processVariablesForResponse(ProcessingMode processingMode, DtoProcessedResponse processedResponse) {
		if(processedResponse.containChanges())
			responseVariablesProcessor.processChangedResponse(processedResponse, processingMode);
		else
			resetVariablesOfLastInteraction(processedResponse);
	}

	private void resetVariablesOfLastInteraction(DtoProcessedResponse processedResponse) {
		DtoModuleProcessingResult processingResult = processedResponse.getPreviousProcessingResult();
		UserInteractionVariables userInteractionVariables = processingResult.getUserInteractionVariables();
		responseVariablesProcessor.resetLastUserInteractionVariables(userInteractionVariables);
	}
}
