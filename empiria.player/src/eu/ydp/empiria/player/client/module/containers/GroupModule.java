package eu.ydp.empiria.player.client.module.containers;


public class GroupModule extends ContainerModuleBase<GroupModule> {

	public GroupModule() {
		super();
		panel.setStyleName("qp-group");
	}

	@Override
	public GroupModule getNewInstance() {
		return new GroupModule();
	}
}
