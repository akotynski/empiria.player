package eu.ydp.empiria.player.client.module.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionNullObject;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionObject;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleBean;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleStructure;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionView;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionModulePresenter
		implements ActivityPresenter<ExternalInteractionResponseModel, ExternalInteractionModuleBean>, ExternalInteractionFrameLoadHandler {

	private ExternalInteractionModuleStructure structure;
	private final EmpiriaPaths empiriaPaths;
	private final ExternalInteractionView view;
	private final ExternalInteractionEmpiriaApi empiriaApi;
	private final ExternalStateEncoder stateEncoder;
	private ExternalInteractionObject externalObject;

	@Inject
	public ExternalInteractionModulePresenter(@ModuleScoped ExternalInteractionView view, @ModuleScoped ExternalInteractionModuleStructure structure,
			ExternalInteractionEmpiriaApi empiriaApi, EmpiriaPaths empiriaPaths, ExternalStateEncoder stateEncoder) {
		this.structure = structure;
		this.empiriaPaths = empiriaPaths;
		this.view = view;
		this.empiriaApi = empiriaApi;
		this.stateEncoder = stateEncoder;
		this.externalObject = new ExternalInteractionNullObject();
	}

	@Override
	public void bindView() {
		String src = structure.getBean().getSrc();
		String externalModuleFilePath = empiriaPaths.getMediaFilePath(src);
		view.init(empiriaApi, this, externalModuleFilePath);
	}

	@Override
	public void reset() {
		externalObject.reset();
	}

	@Override
	public void setModel(ExternalInteractionResponseModel model) {
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
	}

	@Override
	public void setBean(ExternalInteractionModuleBean externalInteractionModuleBean) {
	}

	@Override
	public void setLocked(boolean locked) {
		if (locked) {
			lock();
		} else {
			unlock();
		}
	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		switch (mode) {
		case MARK:
			markAnswers(type);
			break;
		case UNMARK:
			unmarkAnswers(type);
			break;
		}
	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		switch (mode) {
		case CORRECT:
			externalObject.showCorrectAnswers();
			break;
		case USER:
			externalObject.hideCorrectAnswers();
			break;
		}
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void onExternalModuleLoaded(ExternalInteractionObject externalObject) {
		this.externalObject = externalObject;
	}

	public JSONArray getState() {
		JavaScriptObject state = externalObject.getStateFromExternal();
		return stateEncoder.encodeState(state);
	}

	public void setState(JSONArray stateAndStructure) {
		JavaScriptObject state = stateEncoder.decodeState(stateAndStructure);
		externalObject.setStateFromEmpiriaOnExternal(state);
	}

	private void lock() {
		externalObject.lock();
	}

	private void unlock() {
		externalObject.unlock();
	}

	private void markAnswers(MarkAnswersType type) {
		switch (type) {
		case CORRECT:
			externalObject.markCorrectAnswers();
			break;
		case WRONG:
			externalObject.markWrongAnswers();
			break;
		}
	}

	private void unmarkAnswers(MarkAnswersType type) {
		switch (type) {
		case CORRECT:
			externalObject.unmarkCorrectAnswers();
			break;
		case WRONG:
			externalObject.unmarkWrongAnswers();
			break;
		}
	}
}
