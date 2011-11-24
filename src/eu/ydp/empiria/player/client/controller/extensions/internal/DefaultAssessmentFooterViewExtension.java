package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.communication.sockets.ItemInterferenceSocket;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.AssessmentFooterViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PageInterferenceSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ViewExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequestInvoker;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class DefaultAssessmentFooterViewExtension extends InternalExtension
		implements AssessmentFooterViewExtension, FlowRequestSocketUserExtension,
		DataSourceDataSocketUserExtension, FlowDataSocketUserExtension, DeliveryEventsListenerExtension, PageInterferenceSocketUserExtension {

	protected DataSourceDataSupplier dataSourceDataSupplier; 
	protected FlowDataSupplier flowDataSupplier;
	protected IFlowRequestInvoker flowRequestInvoker;
	protected PageInterferenceSocket pageInterferenceSocket;

	private Panel menuPanel;
	private PushButton checkButton;
	private PushButton continueItemButton;
	private PushButton prevButton; 
	private PushButton nextButton;
	private PushButton finishButton; 
	private PushButton summaryButton;
	private PushButton continueAssessmentButton;
	private PushButton previewAssessmentButton;
	
	private int integrationCounter;
	
	public DefaultAssessmentFooterViewExtension(){
		integrationCounter = 0;
	}
	
	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_VIEW_ASSESSMENT_FOOTER;
	}

	@Override
	public void init() {
		createView();
	}
	
	@Override
	public void setFlowDataSupplier(FlowDataSupplier supplier) {
		flowDataSupplier = supplier;
		integrationCounter++;
	}

	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
		dataSourceDataSupplier = supplier;
		integrationCounter++;
	}

	@Override
	public void setFlowRequestsInvoker(IFlowRequestInvoker fri) {
		flowRequestInvoker = fri;
		integrationCounter++;
	}

	@Override
	public void setPageInterferenceSocket(PageInterferenceSocket acs) {
		pageInterferenceSocket = acs;
		integrationCounter++;
	}

	@Override
	public ViewSocket getAssessmentFooterViewSocket() {
		return new ViewSocket() {
			
			@Override
			public Widget getView() {
				return menuPanel;
			}
		};
	}
	
	protected void createView(){

		// BUTTONS MENU
		
		menuPanel = new FlowPanel();
		menuPanel.setStyleName("qp-footer-buttons");

	    checkButton = new PushButton();
	    checkButton.setStylePrimaryName("qp-check-button");
	    checkButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				flowRequestInvoker.invokeRequest(new FlowRequest.Check());
			}
		});
	    menuPanel.add(checkButton);
	    
	    continueItemButton = new PushButton();
	    continueItemButton.setStylePrimaryName("qp-reset-button");
	    continueItemButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				flowRequestInvoker.invokeRequest(new FlowRequest.Continue());
			}
		});
	    menuPanel.add(continueItemButton);

	    prevButton = new PushButton();
	    prevButton.setStylePrimaryName("qp-prev-button");
	    prevButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				flowRequestInvoker.invokeRequest(new FlowRequest.NavigatePreviousItem());
			}
		});
	    menuPanel.add(prevButton);
	    
	    nextButton = new PushButton();
	    nextButton.setStylePrimaryName("qp-next-button");
	    nextButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				flowRequestInvoker.invokeRequest(new FlowRequest.NavigateNextItem());
			}
		});
	    menuPanel.add(nextButton);
	    
	    finishButton = new PushButton();
	    finishButton.setStylePrimaryName("qp-finish-button");
	    finishButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				flowRequestInvoker.invokeRequest(new FlowRequest.NavigateSummary());
			}
		});
	    menuPanel.add(finishButton);
	    
	    summaryButton = new PushButton();
	    summaryButton.setStylePrimaryName("qp-summary-button");
	    summaryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				flowRequestInvoker.invokeRequest(new FlowRequest.NavigateSummary());
			}
		});
	    menuPanel.add(summaryButton);
	    
	    continueAssessmentButton = new PushButton();
	    continueAssessmentButton.setStylePrimaryName("qp-resultpage-continue");
	    continueAssessmentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				flowRequestInvoker.invokeRequest(new FlowRequest.NavigateFirstItem());
			}
		});
	    menuPanel.add(continueAssessmentButton);
	    
	    previewAssessmentButton = new PushButton();
	    previewAssessmentButton.setStylePrimaryName("qp-resultpage-preview");
	    previewAssessmentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				flowRequestInvoker.invokeRequest(new FlowRequest.NavigatePreviewItem(0));
			}
		});
	    menuPanel.add(previewAssessmentButton);

	}

	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if (deliveryEvent.getType() == DeliveryEventType.TEST_PAGE_LOADED  ||  
			deliveryEvent.getType() == DeliveryEventType.TOC_PAGE_LOADED  ||
			deliveryEvent.getType() == DeliveryEventType.SUMMARY_PAGE_LOADED  ||
			deliveryEvent.getType() == DeliveryEventType.CHECK  ||
			deliveryEvent.getType() == DeliveryEventType.CONTINUE ||
			deliveryEvent.getType() == DeliveryEventType.SHOW_ANSWERS ||
			deliveryEvent.getType() == DeliveryEventType.HIDE_ANSWERS){
			updateButtons();
		}
			
	}
	
	public void updateButtons(){
		
		boolean isPreview = flowDataSupplier.getFlowOptions().activityMode == ActivityMode.CHECK;
		
		boolean isCheck = flowDataSupplier.getFlowFlagCheck();
		boolean isAnswers = flowDataSupplier.getFlowFlagMarkAnswers();
		PageType currPageType = flowDataSupplier.getCurrentPageType();
		PageItemsDisplayMode currItemsDisplayMode = flowDataSupplier.getFlowOptions().itemsDisplayMode;
		
		int modulesCount = 0;
		ItemInterferenceSocket[] itemSockets = pageInterferenceSocket.getItemSockets();
		for (int i = 0 ; i < itemSockets.length ; i ++){
			if (itemSockets[i] != null){
				modulesCount += itemSockets[i].getModuleSockets().length;
			}
		}
		
		checkButton.setVisible(!isCheck  &&  !isAnswers  &&  currPageType == PageType.TEST  &&  !isPreview  &&  modulesCount > 0);
		continueItemButton.setVisible((isCheck || isAnswers)  &&  currPageType == PageType.TEST  &&  !isPreview);
		prevButton.setVisible(currPageType == PageType.TEST  &&  currItemsDisplayMode == PageItemsDisplayMode.ONE);
		prevButton.setEnabled(flowDataSupplier.getFlowOptions().showToC  ||  flowDataSupplier.getCurrentPageIndex() > 0);
		nextButton.setVisible((currPageType == PageType.TEST  &&  currItemsDisplayMode == PageItemsDisplayMode.ONE)  ||  currPageType == PageType.TOC);
		nextButton.setEnabled(flowDataSupplier.getCurrentPageIndex() < dataSourceDataSupplier.getItemsCount() - 1);
		finishButton.setVisible(currPageType == PageType.TEST  &&  flowDataSupplier.getFlowOptions().showSummary  &&  !isPreview);
		finishButton.setEnabled(flowDataSupplier.getCurrentPageIndex() == dataSourceDataSupplier.getItemsCount() - 1);
		summaryButton.setVisible(isPreview  &&  currPageType == PageType.TEST);
		continueAssessmentButton.setVisible(currPageType == PageType.SUMMARY);
		previewAssessmentButton.setVisible(currPageType == PageType.SUMMARY);
		
	}

}
