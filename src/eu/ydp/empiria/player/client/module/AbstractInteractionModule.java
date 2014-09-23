package eu.ydp.empiria.player.client.module;

import static eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode.*;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.controller.style.StyleSocketAttributeHelper;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.StateController;
import eu.ydp.empiria.player.client.structure.ModuleBean;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.YJsonValue;
import eu.ydp.gwtutil.client.util.BooleanUtils;

/**
 * 
 * @author MKaldonek
 * 
 * @param <T>
 *            typ modułu
 * @param <H>
 *            typ modelu
 * @param <U>
 *            typ beana
 */
public abstract class AbstractInteractionModule<T extends AbstractInteractionModule<?, ?, ?>, H extends AbstractResponseModel<?>, U extends ModuleBean> extends
		OneViewInteractionModuleBase implements ResponseModelChangeListener {

	protected boolean locked = false;

	protected boolean showingAnswers = false;

	protected boolean markingAnswers = false;

	private ActivityPresenter<H, U> presenter;
	@Inject
	private StyleSocketAttributeHelper styleAttributeHelper;

	@Inject
	private BooleanUtils booleanUtils;

	@Inject
	private StateController stateController;

	@Inject
	private StyleSocket styleSocket;

	@Override
	public void installViews(List<HasWidgets> placeholders) {

		YJsonArray structure = getStructureModuleFromState();

		getStructure().createFromXml(getModuleElement().toString(), structure);
		getPresenter().setModuleSocket(getModuleSocket());

		initalizeModule();
		initializePresenter();
		applyIdAndClassToView(getView());
		placeholders.get(0).add(getView());

	}

	private YJsonArray getStructureModuleFromState() {
		YJsonArray stateAndStructure = getModuleSocket().getStateById(getIdentifier());
		YJsonValue convertedStateAndStructure = stateController.updateStateAndStructureVersion(stateAndStructure);

		YJsonArray structure = stateController.getStructure(convertedStateAndStructure);
		return structure;
	}

	private void initializePresenter() {
		presenter = getPresenter();
		presenter.setBean(getStructure().getBean());
		presenter.setModel(getResponseModel());
		presenter.bindView();
	}

	protected abstract ActivityPresenter<H, U> getPresenter();

	protected abstract void initalizeModule();

	protected abstract H getResponseModel();

	protected abstract AbstractModuleStructure<U, ? extends JAXBParserFactory<U>> getStructure();

	protected Widget getView() {
		return presenter.asWidget();
	}

	protected void generateInlineBody(Node node, Widget parentWidget) {
		getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(node, parentWidget.getElement());
	}

	@Override
	public void markAnswers(boolean mark) {
		markingAnswers = mark;
		MarkAnswersMode markAnswerMode = (mark) ? MarkAnswersMode.MARK : MarkAnswersMode.UNMARK;

		presenter.markAnswers(MarkAnswersType.CORRECT, markAnswerMode);
		presenter.markAnswers(MarkAnswersType.WRONG, markAnswerMode);
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		ShowAnswersType showAnswersType = (show) ? ShowAnswersType.CORRECT : ShowAnswersType.USER;
		presenter.showAnswers(showAnswersType);
	}

	@Override
	public void lock(boolean lock) {
		locked = lock;
		presenter.setLocked(lock);
	}

	@Override
	public void enableTestSubmittedMode() {
		lock(true);
		presenter.enableTestSubmittedMode();
	}

	@Override
	public void disableTestSubmittedMode() {
		lock(false);
		presenter.disableTestSubmittedMode();
	}

	@Override
	public void reset() {
		presenter.reset();
		clearModel();
		fireStateChanged(false, true);
	}

	@Override
	public JSONArray getState() {
		YJsonArray savedStructure = getStructure().getSavedStructure();
		JSONArray state = getResponseModel().getState();
		return stateController.getResponseWithStructure(state, savedStructure);
	}

	@Override
	public void setState(JSONArray stateAndStructure) {
		YJsonValue convertedStateAndStructure = stateController.updateStateAndStructureVersion(stateAndStructure);

		JSONArray response = stateController.getResponse(convertedStateAndStructure);

		clearModel();
		getResponseModel().setState(response);
		presenter.showAnswers(ShowAnswersType.USER);
		fireStateChanged(false, false);
	}

	protected void clearModel() {
		H responseModel = getResponseModel();
		responseModel.reset();
	}

	@Override
	public void onResponseModelChange() {
		fireStateChanged(true, false);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}

	/**
	 * Zwraca typ liczenia dla modulu. 1 punkt za cwiczenie lub ilosc poprawnych
	 * odpowiedzi w module
	 * 
	 * @return
	 */
	protected CountMode getCountMode() {
		CountMode mode = SINGLE;
		boolean isMultiCount = styleAttributeHelper.getBoolean(CORRECT_ANSWERS.getGlobalCssClassName(), CORRECT_ANSWERS.getAttributeName());
		if (!isMultiCount) {
			String attrValue = styleSocket.getStyles(getModuleElement()).get(CORRECT_ANSWERS.getAttributeName());
			isMultiCount = booleanUtils.getBoolean(attrValue);
		}

		if (isMultiCount) {
			mode = CORRECT_ANSWERS;
		}
		return mode;
	}

	@Override
	public void onBodyLoad() { // NOPMD by MKaldonek on 15.10.12 08:30
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onBodyLoad
	}

	@Override
	public void onBodyUnload() { // NOPMD by MKaldonek on 15.10.12 08:30
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onBodyUnload
	}

	@Override
	public void onSetUp() { // NOPMD by MKaldonek on 15.10.12 08:30
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onSetUp
	}

	@Override
	public void onStart() { // NOPMD by MKaldonek on 15.10.12 08:31
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onStart
	}

	@Override
	public void onClose() { // NOPMD by MKaldonek on 15.10.12 08:31
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onClose
	}
}
