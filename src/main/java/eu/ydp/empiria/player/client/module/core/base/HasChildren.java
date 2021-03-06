package eu.ydp.empiria.player.client.module.core.base;

import java.util.List;

public interface HasChildren extends IModule {
    public List<IModule> getChildrenModules();

    List<HasParent> getNestedChildren();
}
