package eu.ydp.empiria.player.client.module.expression.evaluate;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class CommutationEvaluatorJUnitTest extends AbstractTestWithMocksBase {

	private static int COUNTER;

	private CommutationEvaluator eval;

	@Override
	public void setUp() {
		super.setUp(CommutationEvaluator.class);
		eval = injector.getInstance(CommutationEvaluator.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateCorrect() {
		// given
		ExpressionBean bean = new ExpressionBean();

		List<Response> responses = Lists.newArrayList(correctResponse(), correctResponse(), correctResponse(), correctResponse(), correctResponse());
		bean.setTemplate("'0'+'2'+'3'='1'+'4'");
		bean.getResponses().addAll(responses);

		Multiset<Multiset<String>> correctAnswerMultiSet = HashMultiset.create(Lists.<Multiset<String>> newArrayList(
				HashMultiset.<String> create(Lists.newArrayList("answer_1", "answer_4")),
				HashMultiset.<String> create(Lists.newArrayList("answer_0", "answer_2", "answer_3")),
				HashMultiset.<String> create(Lists.newArrayList("answer_0", "answer_2", "answer_3", "answer_1", "answer_4"))));

		bean.setCorectResponses(correctAnswerMultiSet);

		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateCorrect_commutated() {
		// given
		ExpressionBean bean = new ExpressionBean();

		List<Response> responses = Lists.newArrayList(response(0, 3), response(1, 4), response(2, 0), response(3, 2), (response(4, 1)));
		bean.setTemplate("'0'+'2'+'3'='1'+'4'");
		bean.getResponses().addAll(responses);

		Multiset<Multiset<String>> correctAnswerMultiSet = HashMultiset.create(Lists.<Multiset<String>> newArrayList(
				HashMultiset.<String> create(Lists.newArrayList("answer_1", "answer_4")),
				HashMultiset.<String> create(Lists.newArrayList("answer_0", "answer_2", "answer_3")),
				HashMultiset.<String> create(Lists.newArrayList("answer_0", "answer_2", "answer_3", "answer_1", "answer_4"))));
		bean.setCorectResponses(correctAnswerMultiSet);
		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateWrong_someWrongs() {
		// given
		ExpressionBean bean = new ExpressionBean();
		List<Response> responses = Lists.newArrayList(correctResponse(), correctResponse(), correctResponse(), correctResponse(), wrongResponse());
		bean.setTemplate("'0'+'2'+'3'='1'+'4'");
		bean.getResponses().addAll(responses);

		Multiset<Multiset<String>> correctAnswerMultiSet = HashMultiset.create(Lists.<Multiset<String>> newArrayList(
				HashMultiset.<String> create(Lists.newArrayList("answer_1", "answer_4")),
				HashMultiset.<String> create(Lists.newArrayList("answer_0", "answer_2", "answer_3")),
				HashMultiset.<String> create(Lists.newArrayList("answer_0", "answer_2", "answer_3", "answer_1", "answer_4"))));
		bean.setCorectResponses(correctAnswerMultiSet);

		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateWrong_commutated() {
		// given
		ExpressionBean bean = new ExpressionBean();
		List<Response> responses = Lists.newArrayList(response(0, 4), response(1, 3), response(2, 0), response(3, 2), (response(4, 1)));
		bean.setTemplate("'0'+'2'+'3'='1'+'4'");
		bean.getResponses().addAll(responses);

		Multiset<Multiset<String>> correctAnswerMultiSet = HashMultiset.create(Lists.<Multiset<String>> newArrayList(
				HashMultiset.<String> create(Lists.newArrayList("answer_1", "answer_4")),
				HashMultiset.<String> create(Lists.newArrayList("answer_0", "answer_2", "answer_3")),
				HashMultiset.<String> create(Lists.newArrayList("answer_0", "answer_2", "answer_3", "answer_1", "answer_4"))));
		bean.setCorectResponses(correctAnswerMultiSet);

		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(false));
	}

	private Response response(int correct, int user) {
		CorrectAnswers correctAnswers = new CorrectAnswers();
		correctAnswers.add(new ResponseValue("answer_" + correct));
		List<String> values = Lists.newArrayList("answer_" + user);
		Response response = new Response(correctAnswers, values, Lists.<String> newArrayList(), String.valueOf(correct), Evaluate.DEFAULT, BaseType.STRING,
				Cardinality.SINGLE, CountMode.SINGLE, new ExpressionBean(), CheckMode.EXPRESSION);
		return response;
	}

	private Response correctResponse() {
		Response response = response(COUNTER, COUNTER);
		COUNTER++;
		return response;
	}

	private Response wrongResponse() {
		Response response = response(COUNTER++, COUNTER++);
		return response;
	}

}
