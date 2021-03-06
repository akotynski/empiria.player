package eu.ydp.empiria.player.client.module.draggap;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DragGapModuleModel extends AbstractResponseModel<String> {

    @Inject
    public DragGapModuleModel(@ModuleScoped Response response) {
        super(response);
    }

    public void initialize(ResponseModelChangeListener modelChangeListener) {
        this.setResponseModelChange(modelChangeListener);
    }

    @Override
    protected List<String> parseResponse(Collection<String> values) {
        return new ArrayList<String>(values);
    }

    public Response getResponse() {
        return super.getResponse();
    }
}
