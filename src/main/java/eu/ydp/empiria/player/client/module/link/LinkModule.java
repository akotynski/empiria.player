package eu.ydp.empiria.player.client.module.link;

import javax.annotation.PostConstruct;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.SimpleContainerModuleBase;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeTestClient;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.command.SetStyleNameCommand;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class LinkModule extends SimpleContainerModuleBase<LinkModule> implements WorkModeTestClient {

	protected FlowRequestInvoker flowRequestInvoker;
	protected Panel mainPanel;
	protected int itemIndex = -1;
	protected String url;
	private boolean locked;

	@Inject
	private StyleNameConstants styleNames;
	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	@Inject
	private EventsBus eventsBus;

	@Inject
	public LinkModule(@Assisted FlowRequestInvoker flowRequestInvoker) {
		this.flowRequestInvoker = flowRequestInvoker;
	}

	@PostConstruct
	public void postConstruct() {
		setLinkContentStyle();
		prepareMainPanel();
		addClickHandler();
		addMouseOverHandler();
		addMouseOutHandler();
		addLinkResetStyleNameOnPageChangeHandler();
	}

	private void addLinkResetStyleNameOnPageChangeHandler() {
		final CurrentPageScope modulePage = new CurrentPageScope();
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), new PlayerEventHandler() {

			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if (modulePage.equals(new CurrentPageScope())) {
					setPrimaryStyleName();
				}
			}
		});
	}

	private void setLinkContentStyle() {
		setContainerStyleName(styleNames.QP_LINK_CONTENT());
	}

	private void prepareMainPanel() {
		mainPanel = new FlowPanel();
		setPrimaryStyleName();
		mainPanel.add(getContainer());
	}

	private void addMouseOutHandler() {
		userInteractionHandlerFactory.createUserOutHandler(createOutCommand()).apply(mainPanel);
	}

	private Command createOutCommand() {
		return new SetStyleNameCommand(mainPanel, styleNames.QP_LINK());
	}

	private void addMouseOverHandler() {
		userInteractionHandlerFactory.createUserOverHandler(createOverCommand()).apply(mainPanel);
	}

	private Command createOverCommand() {
		return new SetStyleNameCommand(mainPanel, styleNames.QP_LINK_OVER());
	}

	private void addClickHandler() {
		userInteractionHandlerFactory.createUserClickHandler(createClickCommand()).apply(mainPanel);
	}

	private Command createClickCommand() {
		return new Command() {
			@Override
			public void execute(NativeEvent event) {
				processLink();
			}
		};
	}

	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener mil, BodyGeneratorSocket bodyGeneratorSocket) {
		super.initModule(element, moduleSocket, mil, bodyGeneratorSocket);

		if (element.hasAttribute("itemIndex")) {
			itemIndex = NumberUtils.tryParseInt(element.getAttribute("itemIndex"), -1);
		}
		if (itemIndex == -1 && element.hasAttribute("url")) {
			url = element.getAttribute("url");
		}
	}

	@Override
	public Widget getView() {
		return mainPanel;
	}

	protected void processLink() {
		if (locked) {
			return;
		}
		if (itemIndex != -1) {
			flowRequestInvoker.invokeRequest(new FlowRequest.NavigateGotoItem(itemIndex));
		} else if (url != null) {
			Window.open(url, "_blank", "");
		}

	}

	private void setPrimaryStyleName() {
		mainPanel.setStyleName(styleNames.QP_LINK());
	}

	@Override
	public LinkModule getNewInstance() {
		return null;
	}

	@Override
	public void lock(boolean state) {
		super.lock(state);
		this.locked = state;
	}

	@Override
	public void enableTestMode() {
		lock(true);
	}

	@Override
	public void disableTestMode() {
		lock(false);
	}
}
