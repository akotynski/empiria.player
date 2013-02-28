package eu.ydp.empiria.player.client.controller.variables.processor.counting.predicates;

import com.google.gwt.thirdparty.guava.common.base.Predicate;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;

public class CorrectAnswerPredicate implements Predicate<String> {

	private CorrectAnswers correctAnswers;
	
	public CorrectAnswerPredicate(CorrectAnswers correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	@Override
	public boolean apply(String answer) {
		return correctAnswers.containsAnswer(answer);
	}

}
