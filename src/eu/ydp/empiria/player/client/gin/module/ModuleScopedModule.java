package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.xml.client.Element;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScopeStack;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.ResponseModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.XmlElementModuleScopedProvider;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModuleModel;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillModelProxy;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionViewColors;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseAnswerByViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.presenter.UserToResponseAreaMapper;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillBeanProxy;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionStructure;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;

public class ModuleScopedModule extends AbstractGinModule{

	@Override
	protected void configure() {
		bind(ModuleScopeStack.class).in(Singleton.class);
		
		bind(Element.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(XmlElementModuleScopedProvider.class);
		
		bind(Response.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(ResponseModuleScopedProvider.class);
	
		bindOrdering();
		bindColorfill();
	}
	
	private void bindOrdering() {
		bind(new TypeLiteral<ModuleScopedProvider<OrderingItemsDao>>(){}).in(Singleton.class);
		bind(OrderingItemsDao.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<OrderingItemsDao>>(){}));
		
		bind(new TypeLiteral<ModuleScopedProvider<OrderInteractionModuleModel>>(){}).in(Singleton.class);
		bind(OrderInteractionModuleModel.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<OrderInteractionModuleModel>>(){}));
		
		bind(new TypeLiteral<ModuleScopedProvider<OrderInteractionView>>(){}).in(Singleton.class);
		bind(OrderInteractionView.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<OrderInteractionView>>(){}));
	}

	private void bindColorfill() {
		bind(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionView>>(){}).in(Singleton.class);
		bind(ColorfillInteractionView.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionView>>(){}));
		
		bind(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionModuleModel>>(){}).in(Singleton.class);
		bind(ColorfillInteractionModuleModel.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionModuleModel>>(){}));
		
		bind(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionPresenter>>(){}).in(Singleton.class);
		bind(ColorfillInteractionPresenter.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionPresenter>>(){}));
		
		bindToModuleScopedProvider(new TypeLiteral<ModuleScopedProvider<ColorfillModelProxy>>(){}, ColorfillModelProxy.class);
		bindToModuleScopedProvider(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionViewColors>>(){}, ColorfillInteractionViewColors.class);
		bindToModuleScopedProvider(new TypeLiteral<ModuleScopedProvider<UserToResponseAreaMapper>>(){}, UserToResponseAreaMapper.class);
		bindToModuleScopedProvider(new TypeLiteral<ModuleScopedProvider<ColorfillBeanProxy>>(){}, ColorfillBeanProxy.class);
		bindToModuleScopedProvider(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionStructure>>(){}, ColorfillInteractionStructure.class);
		bindToModuleScopedProvider(new TypeLiteral<ModuleScopedProvider<ResponseAnswerByViewBuilder>>(){}, ResponseAnswerByViewBuilder.class);
	}
	
	private <T> void bindToModuleScopedProvider(TypeLiteral<ModuleScopedProvider<T>> typeLiteral, Class<T> clazz){
		bind(typeLiteral).in(Singleton.class);
		bind(clazz)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(typeLiteral));
	}

}
