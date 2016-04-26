package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.Collection;
import java.util.List;

public class ExternalInteractionResponseModel extends AbstractResponseModel<String> {

    @Inject
    public ExternalInteractionResponseModel(@ModuleScoped Response response) {
        super(response);
    }

    @Override
    protected List<String> parseResponse(Collection<String> values) {
        return Lists.newArrayList(values);
    }
}