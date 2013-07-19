package eu.ydp.empiria.player.client.module.choice.presenter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.SimpleChoicePresenterFactory;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
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
import eu.ydp.gwtutil.client.StringUtils;

public class ChoiceModulePresenterImpl implements ChoiceModulePresenter {

	private Map<String, SimpleChoicePresenter> id2choices;

	private InlineBodyGeneratorSocket bodyGenerator;

	private ChoiceInteractionBean bean;

	private ChoiceModuleModel model;

	private ChoiceModuleView view;

	private SimpleChoicePresenterFactory choiceModuleFactory;

	private ChoiceModuleListener listener;

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
		id2choices = new HashMap<String, SimpleChoicePresenter>();

		view.clear();

		for (SimpleChoiceBean choice : bean.getSimpleChoices()) {
			SimpleChoicePresenter choicePresenter = createSimpleChoicePresenter(choice, bodyGenerator);
			id2choices.put(choice.getIdentifier(), choicePresenter);
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
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			choice.setLocked(locked);
		}
	}

	private Collection<SimpleChoicePresenter> getSimpleChoices() {
		return id2choices.values();
	}

	@Override
	public void reset() {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			model.removeAnswer(choiceIdentifier);

			choice.reset();
		}
	}

	@Override
	public IsWidget getFeedbackPlaceholderByIdentifier(final String identifier) {
		IsWidget widget = null;
		Collection<SimpleChoicePresenter> simpleChoices = getSimpleChoices();
		Predicate<SimpleChoicePresenter> placeHolderPredicate = getPlaceHolderPredicate(identifier);
		Optional<SimpleChoicePresenter> optionalPlaceholder = Iterables.tryFind(simpleChoices, placeHolderPredicate);

		if (optionalPlaceholder.isPresent()) {
			widget = optionalPlaceholder.get().getFeedbackPlaceHolder();
		}

		return widget;
	}

	private Predicate<SimpleChoicePresenter> getPlaceHolderPredicate(final String identifier) {
		Predicate<SimpleChoicePresenter> predicate = new Predicate<SimpleChoicePresenter>() {

			@Override
			public boolean apply(SimpleChoicePresenter simpleChoicePresenter) {
				String choiceIdentifier = getChoiceIdentifier(simpleChoicePresenter);
				return identifier.equals(choiceIdentifier);
			}
		};
		return predicate;
	}

	@Override
	public String getChoiceIdentifier(final SimpleChoicePresenter choice) {
		String searchedIdentifier = StringUtils.EMPTY_STRING;
		Set<Entry<String, SimpleChoicePresenter>> choices = id2choices.entrySet();
		Predicate<Entry<String, SimpleChoicePresenter>> predicate = getIdentifierPredicate(choice);
		Optional<Entry<String, SimpleChoicePresenter>> optionalIdentifier = Iterables.tryFind(choices, predicate);

		if (optionalIdentifier.isPresent()) {
			searchedIdentifier = optionalIdentifier.get().getKey();
		}

		return searchedIdentifier;
	}

	private Predicate<Entry<String, SimpleChoicePresenter>> getIdentifierPredicate(final SimpleChoicePresenter choice) {
		Predicate<Entry<String, SimpleChoicePresenter>> predicate = new Predicate<Entry<String, SimpleChoicePresenter>>() {

			@Override
			public boolean apply(Entry<String, SimpleChoicePresenter> entry) {
				return choice.equals(entry.getValue());
			}
		};
		return predicate;
	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
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
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
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
}
