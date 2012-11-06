package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.gwtutil.xml.XMLParser;

public class ConnectionModulePresenterJUnitTest extends AbstractJAXBTestBase<MatchInteractionBean> {

	private static final String CONNECTION_RESPONSE_1_4 = "CONNECTION_RESPONSE_1_4";
	private static final String CONNECTION_RESPONSE_1_1 = "CONNECTION_RESPONSE_1_1";
	private static final String CONNECTION_RESPONSE_1_3 = "CONNECTION_RESPONSE_1_3";
	private static final String CONNECTION_RESPONSE_1_0 = "CONNECTION_RESPONSE_1_0";
	private static final String FALSE = "false";
	private static final String TRUE = "true";
	private static final Integer DEFAULT_MATCH_MAX = 2;

	private ConnectionModulePresenterImpl connectionModulePresenter;
	private MultiplePairModuleView<SimpleAssociableChoiceBean> moduleView;
	private ConnectionModuleModel connectionModuleModel;

	@Test
	public void shouldValidateConnectionRequestAsInvalid() {
		String source = CONNECTION_RESPONSE_1_0;
		String target = CONNECTION_RESPONSE_1_3;
		PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.CONNECTED, source, target, true);

		connectionModulePresenter.onConnectionEvent(event);

		Mockito.verify(moduleView).disconnect(source, target);
	}

	@Test
	public void shouldValidateConnectionRequestAsValid() {
		PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4, true);

		connectionModulePresenter.onConnectionEvent(event);
		
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4));
	}

	@Test
	public void shouldDisconnectPairOnDisconnectedEvent() {
		PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.DISCONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4, true);

		connectionModulePresenter.onConnectionEvent(event);

		Mockito.verify(connectionModuleModel).removeAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4));
	}

	@Test
	public void shouldReversedConnectionsOrderBeProcessedCorrectly() {
		connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(2, null)));

		PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_0, true);
		connectionModulePresenter.onConnectionEvent(event1);
		PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_3, true);
		connectionModulePresenter.onConnectionEvent(event2);

		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1));
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
	}

	@Test
	public void shouldPreventInvalidConnection() {
		connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(2, null)));

		PairConnectEvent bothLeftNodesEvent = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_0, true);
		connectionModulePresenter.onConnectionEvent(bothLeftNodesEvent);
		PairConnectEvent correctEvent = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_3, true);
		connectionModulePresenter.onConnectionEvent(correctEvent);

		Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_3));
		Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_0));
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
	}

	@Test
	public void shouldPreventDoubleCombination() {
		connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(2, null)));

		PairConnectEvent firstEvent = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_3, true);
		connectionModulePresenter.onConnectionEvent(firstEvent);
		PairConnectEvent reConnectEvent = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, true);
		connectionModulePresenter.onConnectionEvent(reConnectEvent);
		PairConnectEvent reConnectEvent2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_3, true);
		connectionModulePresenter.onConnectionEvent(reConnectEvent2);

		Mockito.verify(moduleView).disconnect(reConnectEvent.getSourceItem(), reConnectEvent.getTargetItem());
		Mockito.verify(moduleView).disconnect(reConnectEvent2.getSourceItem(), reConnectEvent2.getTargetItem());
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
	}

	@Test
	public void markCorrectAnswers() {
		connectionModulePresenter.onConnectionEvent(new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1,
				true));
		connectionModulePresenter.onConnectionEvent(new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1,
				true));
		connectionModulePresenter.onConnectionEvent(new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4,
				true));
		doReturn(Arrays.asList(true, false, true)).when(connectionModuleModel).evaluateResponse();

		connectionModulePresenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);
		connectionModulePresenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK);

		Mockito.verify(moduleView).connect(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1, MultiplePairModuleConnectType.CORRECT);
		Mockito.verify(moduleView).connect(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4, MultiplePairModuleConnectType.CORRECT);
		Mockito.verify(moduleView).connect(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, MultiplePairModuleConnectType.WRONG);
	}

	@Test
	public void shouldDetectMaximumOverallAssociationsNumberAchieved() {
		connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(2, null)));

		PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1, true);
		connectionModulePresenter.onConnectionEvent(event1);
		PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, true);
		connectionModulePresenter.onConnectionEvent(event2);
		PairConnectEvent event3 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4, true);
		connectionModulePresenter.onConnectionEvent(event3);

		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1));
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
		Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4));
	}

	@Test
	public void shouldIgnoreOverallAssociationsNumberLimits() {
		connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(null, null)));

		PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1, true);
		connectionModulePresenter.onConnectionEvent(event1);
		PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, true);
		connectionModulePresenter.onConnectionEvent(event2);
		PairConnectEvent event3 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4, true);
		connectionModulePresenter.onConnectionEvent(event3);
		PairConnectEvent event4 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4, true);
		connectionModulePresenter.onConnectionEvent(event4);

		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1));
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4));
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4));
	}

	@Test
	public void shouldDetectChoiceMaxMatchLimits() {
		Map<String, Integer> matchMaxMap = new HashMap<String, Integer>();
		matchMaxMap.put(CONNECTION_RESPONSE_1_0, 1);
		matchMaxMap.put(CONNECTION_RESPONSE_1_1, 1);
		matchMaxMap.put(CONNECTION_RESPONSE_1_4, 2);
		matchMaxMap.put(CONNECTION_RESPONSE_1_3, 2);
		connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(null, matchMaxMap)));

		PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4, true);
		connectionModulePresenter.onConnectionEvent(event1);
		PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, true);
		connectionModulePresenter.onConnectionEvent(event2);
		PairConnectEvent event3 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4, true);
		connectionModulePresenter.onConnectionEvent(event3);
		PairConnectEvent event4 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1, true);
		connectionModulePresenter.onConnectionEvent(event4);

		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4));
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
		Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4));
		Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1));
	}

	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		connectionModulePresenter = spy(new ConnectionModulePresenterImpl());
		connectionModuleModel = spy(new ConnectionModuleModel(new Response(mockResponseElement()), mock(ResponseModelChangeListener.class)));
		connectionModulePresenter.setModel(connectionModuleModel);
		MatchInteractionBean bean = createBeanFromXMLString(mockStructure(null, null));
		connectionModulePresenter.setBean(bean);
		moduleView = mock(MultiplePairModuleView.class);
		connectionModulePresenter.setModuleView(moduleView);
	}

	private Element mockResponseElement() {
		StringBuilder builder = new StringBuilder();
		builder.append("<responseDeclaration baseType=\"directedPair\" cardinality=\"multiple\" evaluate=\"user\" identifier=\"CONNECTION_RESPONSE_1\">");
		builder.append("		<correctResponse>");
		builder.append("			<value>" + CONNECTION_RESPONSE_1_0 + " " + CONNECTION_RESPONSE_1_1 + "</value>");
		builder.append("			<value>" + CONNECTION_RESPONSE_1_3 + " " + CONNECTION_RESPONSE_1_4 + "</value>");
		builder.append("		</correctResponse>");
		builder.append("</responseDeclaration>");
		return XMLParser.parse(builder.toString()).getDocumentElement();
	}

	private String mockStructure(Integer maxAssociations, Map<String, Integer> matchMaxMap) {
		String maxAssociationsXMLAttr = "";
		if (maxAssociations != null) {
			maxAssociationsXMLAttr = " maxAssociations=\"" + maxAssociations + "\"";
		}
		return "<matchInteraction id=\"dummy1\" responseIdentifier=\"CONNECTION_RESPONSE_1\" shuffle=\"" + TRUE + "\"" + maxAssociationsXMLAttr + ">"
				+ "<simpleMatchSet>" + "<simpleAssociableChoice fixed=\"" + FALSE
				+ "\" identifier=\""
				+ CONNECTION_RESPONSE_1_0
				+ "\" matchMax=\""
				+ getMatchMax(CONNECTION_RESPONSE_1_0, matchMaxMap)
				+ "\">"
				+ // NOPMD
				"a	</simpleAssociableChoice>" + "<simpleAssociableChoice fixed=\"" + FALSE + "\" identifier=\"" + CONNECTION_RESPONSE_1_3 + "\" matchMax=\""
				+ getMatchMax(CONNECTION_RESPONSE_1_3, matchMaxMap) + "\">" + "b	</simpleAssociableChoice>" + "</simpleMatchSet>" + "<simpleMatchSet>"
				+ "<simpleAssociableChoice fixed=\"" + FALSE + "\" identifier=\"" + CONNECTION_RESPONSE_1_4 + "\" matchMax=\""
				+ getMatchMax(CONNECTION_RESPONSE_1_4, matchMaxMap) + "\">" + "c	</simpleAssociableChoice>" + "<simpleAssociableChoice fixed=\"" + FALSE
				+ "\" identifier=\"" + CONNECTION_RESPONSE_1_1 + "\" matchMax=\"" + getMatchMax(CONNECTION_RESPONSE_1_1, matchMaxMap) + "\">"
				+ "d	</simpleAssociableChoice>" + "</simpleMatchSet>" + "</matchInteraction>";
	}

	private int getMatchMax(String identifier, Map<String, Integer> matchMaxMap) {
		Integer value = null;
		if (matchMaxMap != null) {
			value = matchMaxMap.get(identifier);
		}
		if (value == null) {
			value = DEFAULT_MATCH_MAX;
		}
		return value;
	}

	private String concatNodes(String source, String target) {
		return source + " " + target;
	}

}