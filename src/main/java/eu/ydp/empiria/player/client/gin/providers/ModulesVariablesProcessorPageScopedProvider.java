package eu.ydp.empiria.player.client.gin.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.gin.scopes.page.CurrentPageScopeProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;

@Singleton
public class ModulesVariablesProcessorPageScopedProvider extends PageScopedProvider<ModulesVariablesProcessor> {

    @Inject
    public ModulesVariablesProcessorPageScopedProvider(Provider<ModulesVariablesProcessor> provider, CurrentPageScopeProvider currentScopeProvider) {
        super(provider, currentScopeProvider);
    }

}
