package eu.ydp.empiria.player.client.module.choice.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.SimpleChoicePresenterFactory;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListenerImpl;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.choice.view.ChoiceModuleView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ChoiceModulePresenterImpl implements ChoiceModulePresenter {

	private List<SimpleChoicePresenter> choices;

	private InlineBodyGeneratorSocket bodyGenerator;

	private ChoiceInteractionBean bean;

	private final ChoiceModuleModel model;

	private final ChoiceModuleView view;

	private final SimpleChoicePresenterFactory choiceModuleFactory;

	private final ChoiceModuleListener listener;

	@Inject
	public ChoiceModulePresenterImpl(SimpleChoicePresenterFactory choiceModuleFactory, @ModuleScoped ChoiceModuleModel model,
			@ModuleScoped ChoiceModuleView view) {
		this.choiceModuleFactory = choiceModuleFactory;
		this.model = model;
		this.view = view;
		listener = new ChoiceModuleListenerImpl(model, this);
	}

	@Override
	public void bindView() {
		initializePrompt();
		initializeChoices();
	}

	private void initializePrompt() {
		bodyGenerator.generateInlineBody(bean.getPrompt(), view.getPrompt());
	}

	private void initializeChoices() {
		choices = new ArrayList<SimpleChoicePresenter>();

		view.clear();

		for (SimpleChoiceBean choice : bean.getSimpleChoices()) {
			SimpleChoicePresenter choicePresenter = createSimpleChoicePresenter(choice, bodyGenerator);
			choices.add(choicePresenter);
			view.addChoice(choicePresenter.asWidget());
			choicePresenter.setListener(listener);
		}
	}

	private SimpleChoicePresenter createSimpleChoicePresenter(SimpleChoiceBean choice, InlineBodyGeneratorSocket bodyGenerator) {

		return choiceModuleFactory.getSimpleChoicePresenter(choice, bodyGenerator);
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void setInlineBodyGenerator(InlineBodyGeneratorSocket bodyGenerator) {
		this.bodyGenerator = bodyGenerator;
	}

	@Override
	public void setLocked(boolean locked) {
		for (SimpleChoicePresenter choice : choices) {
			choice.setLocked(locked);
		}
	}

	@Override
	public void reset() {
		for (SimpleChoicePresenter choice : choices) {
			choice.reset();
		}
	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		for (SimpleChoicePresenter choice : choices) {
			String choiceIdentifier = choice.getIdentifier();
			boolean mark = isChoiceMarkType(type, choiceIdentifier);

			if (choice.isSelected() && mark) {
				choice.markAnswer(type, mode);
			}
		}
	}

	private boolean isChoiceMarkType(MarkAnswersType type, String choiceIdentifier) {
		boolean is = false;

		switch (type) {
		case CORRECT:
			is = model.isCorrectAnswer(choiceIdentifier);
			break;
		case WRONG:
			is = model.isWrongAnswer(choiceIdentifier);
			break;
		}

		return is;
	}

	@Override
	public void showAnswers(ShowAnswersType type) {
		for (SimpleChoicePresenter choice : choices) {
			String choiceIdentifier = choice.getIdentifier();
			boolean select = isChoiceAnswerType(type, choiceIdentifier);
			choice.setSelected(select);
		}
	}

	private boolean isChoiceAnswerType(ShowAnswersType type, String choiceIdentifier) {
		boolean select = false;

		switch (type) {
		case CORRECT:
			select = model.isCorrectAnswer(choiceIdentifier);
			break;
		case USER:
			select = model.isUserAnswer(choiceIdentifier);
			break;
		}

		return select;
	}

	@Override
	public void setBean(ChoiceInteractionBean bean) {
		this.bean = bean;
	}

	@Override
	public void setModel(ChoiceModuleModel model) {
		// this.model = model;
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		// TODO Auto-generated method stub
	}

	@Override
	public void enableTestSubmittedMode() {
		view.enableTestSubmittedMode();
	}

	@Override
	public void disableTestSubmittedMode() {
		view.disableTestSubmittedMode();
	}
}
