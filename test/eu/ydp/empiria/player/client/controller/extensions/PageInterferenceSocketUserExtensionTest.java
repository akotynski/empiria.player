package eu.ydp.empiria.player.client.controller.extensions;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket;
import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ModuleExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PageInterferenceSocketUserExtension;
import eu.ydp.empiria.player.client.module.HasParent;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

//Do przerobienia caly test na innej zasadzie dzialaja teraz PageControlery.
public abstract class PageInterferenceSocketUserExtensionTest extends ExtensionTestBase {

	protected DeliveryEngine de;
	protected PageInterferenceSocket pis;
	protected String lastAction = "";

	protected void testGetJsSocket(){
		ModuleInterferenceSocket mis = initTests();
		mis.getJsSocket();
		assertEquals("getJsSocket", lastAction);
	}
	protected void testGetState(){
		ModuleInterferenceSocket mis = initTests();

		mis.getState();
		assertEquals("getState", lastAction);

	}
	protected void testLock(){
		ModuleInterferenceSocket mis = initTests();

		mis.lock(false);
		assertEquals("lock", lastAction);

	}
	protected void testMarkAnswers(){
		ModuleInterferenceSocket mis = initTests();

		mis.markAnswers(false);
		assertEquals("markAnswers", lastAction);

	}
	protected void testReset(){
		ModuleInterferenceSocket mis = initTests();

		mis.reset();
		assertEquals("reset", lastAction);

	}
	protected void testSetState(){
		ModuleInterferenceSocket mis = initTests();

		mis.setState(new JSONArray());
		assertEquals("setState", lastAction);

	}
	protected void testShowCorrectAnswers(){
		ModuleInterferenceSocket mis = initTests();

		mis.showCorrectAnswers(false);
		assertEquals("showCorrectAnswers", lastAction);
	}

	protected ModuleInterferenceSocket initTests(){
		List<Extension> exts = new ArrayList<Extension>();
		exts.add(new MockPageInterferenceSocketUserExtension());
		exts.add(new MockModuleExtension());
		de = initDeliveryEngine(exts, false);

		assertNotNull(pis.getItemSockets());
		assertEquals(1, pis.getItemSockets().length);
		assertNotNull(pis.getItemSockets()[0].getModuleSockets());
		assertEquals(1, pis.getItemSockets()[0].getModuleSockets().length);

		ModuleInterferenceSocket mis = pis.getItemSockets()[0].getModuleSockets()[0];
		return mis;
	}


	@Override
	protected XmlData getAssessmentXMLData(){

		String assessmentXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><assessmentTest xmlns=\"http://www.ydp.eu/empiria\" identifier=\"RTEST-13\" title=\"Show player supported functionality\"><testPart><assessmentSection identifier=\"sectionA\" title=\"Section A\" visible=\"true\"><assessmentItemRef identifier=\"inline_choice\" href=\"demo/inline_choice.xml\"/></assessmentSection></testPart></assessmentTest>";
		Document assessmentDoc = XMLParser.parse(assessmentXml);
		return new XmlData(assessmentDoc, "");
	}
	@Override
	protected XmlData[] getItemXMLDatas(){

		Document itemDoc = XMLParser.parse("<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody><testModule8273629297347 responseIdentifier=\"RESPONSE1\"/></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
		XmlData itemData = new XmlData(itemDoc, "");

		XmlData[] itemDatas = new XmlData[1];
		itemDatas[0] = itemData;

		return itemDatas;
	}

	protected class MockPageInterferenceSocketUserExtension extends InternalExtension implements PageInterferenceSocketUserExtension{
		@Override
		public void init() {
		}

		@Override
		public void setPageInterferenceSocket(PageInterferenceSocket socket) {
			pis = socket;
		}
	}

	protected class MockModuleExtension extends ModuleExtension implements ModuleConnectorExtension{

		@Override
		public ModuleCreator getModuleCreator() {
			return new ModuleCreator() {

				@Override
				public boolean isMultiViewModule() {
					return true;
				}

				@Override
				public boolean isInlineModule() {
					return false;
				}

				@Override
				public IModule createModule() {
					return new MockModule();
				}
			};
		}

		@Override
		public String getModuleNodeName() {
			return "testModule8273629297347";
		}

	}

	protected class MockModule implements IInteractionModule{

		@Override
		public void markAnswers(boolean mark) {
			lastAction = "markAnswers";
		}

		@Override
		public void showCorrectAnswers(boolean show) {
			lastAction = "showCorrectAnswers";
		}

		@Override
		public void lock(boolean l) {
			lastAction = "lock";
		}

		@Override
		public void reset() {
			lastAction = "reset";
		}

		@Override
		public JSONArray getState() {
			lastAction = "getState";
			return new JSONArray();
		}

		@Override
		public void setState(JSONArray newState) {
			lastAction = "setState";
		}

		@Override
		public JavaScriptObject getJsSocket() {
			lastAction = "getJsSocket";
			return JavaScriptObject.createObject();
		}

		@Override
		public String getIdentifier() {
			return "RESPONSE1";
		}

		@Override
		public void initModule(ModuleSocket moduleSocket, InteractionEventsListener moduleInteractionListener) {
		}

		@Override
		public void addElement(Element element) {

		}

		@Override
		public void installViews(List<HasWidgets> placeholders) {

		}

		@Override
		public void onBodyLoad() {

		}

		@Override
		public void onBodyUnload() {

		}

		@Override
		public void onSetUp() {

		}

		@Override
		public void onStart() {
		}

		@Override
		public void onClose() {
		}

		@Override
		public HasParent getParentModule() {
			return null;
		}

	}
}
