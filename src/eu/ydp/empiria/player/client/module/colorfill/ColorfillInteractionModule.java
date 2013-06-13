package eu.ydp.empiria.player.client.module.colorfill;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionStructure;

public class ColorfillInteractionModule extends AbstractInteractionModule<ColorfillInteractionModule, ColorfillInteractionModuleModel, ColorfillInteractionBean> {

	@Inject
	private ColorfillInteractionPresenter presenter;
	@Inject @ModuleScoped
	private ColorfillInteractionModuleModel moduleModel;

	@Inject @ModuleScoped
	private ColorfillInteractionStructure colorfillInteractionStructure;

	@Override
	protected ActivityPresenter<ColorfillInteractionModuleModel, ColorfillInteractionBean> getPresenter() {
		return presenter;
	}

	@Override
	protected void initalizeModule() { 
		moduleModel.initialize(this);
	}

	@Override
	protected ColorfillInteractionModuleModel getResponseModel() {
		return moduleModel;
	}

	@Override
	protected AbstractModuleStructure<ColorfillInteractionBean, ? extends JAXBParserFactory<ColorfillInteractionBean>> getStructure() {
		return colorfillInteractionStructure;
	}

}
