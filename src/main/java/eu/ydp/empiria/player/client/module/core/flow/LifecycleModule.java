package eu.ydp.empiria.player.client.module.core.flow;

public interface LifecycleModule {

    public void onBodyLoad();

    public void onBodyUnload();

    public void onSetUp();

    public void onStart();

    public void onClose();

}
