package eu.ydp.empiria.player.client.module.selection;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectionModuleModel extends AbstractResponseModel<String> {

    @Inject
    public SelectionModuleModel(@ModuleScoped Response response) {
        super(response);
    }

    public void initialize(ResponseModelChangeListener modelChangeListener) {
        super.setResponseModelChange(modelChangeListener);
    }

    @Override
    protected List<String> parseResponse(Collection<String> values) {
        return new ArrayList<String>(values);
    }

}
