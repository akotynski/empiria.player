package eu.ydp.empiria.player.client.module.mediator.powerfeedback;

import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.EventScope;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventHandler;
import eu.ydp.empiria.player.client.util.events.internal.EventType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes.OUTCOME_STATE_CHANGED;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class PowerFeedbackMediatorTest {

    private PowerFeedbackMediator mediator;
    private EventsBus eventsBus;
    private PageScopeFactory pageScopeFactory;
    private PowerFeedbackTutorClient tutor;
    private PowerFeedbackBonusClient bonus;
    private StateChangeEventHandler stateChangedHandler;
    private PlayerEventHandler testPageLoadedHandler;
    private PlayerEventHandler pageUnloadedhandler;

    @Before
    public void setUp() {
        initMocks();
        initEventHandlersInterception();
        mediator = new PowerFeedbackMediator(eventsBus, pageScopeFactory, new NullPowerFeedbackTutorClient(), new NullPowerFeedbackBonusClient());
    }

    @Test
    public void shouldInitTutor() {
        // given
        PlayerEvent event = mock(PlayerEvent.class);
        mediator.registerTutor(tutor);

        // when
        testPageLoadedHandler.onPlayerEvent(event);

        // then
        verify(tutor).initPowerFeedbackClient();
    }

    @Test
    public void shouldResetClients() {
        // given
        StateChangeEvent event = mockStateChangeEvent(false, true);
        mediator.registerBonus(bonus);
        mediator.registerTutor(tutor);

        // when
        stateChangedHandler.onStateChange(event);

        // then
        verify(bonus).resetPowerFeedback();
        verify(tutor).resetPowerFeedback();
    }

    @Test
    public void shouldNotifyClientsOnStateChanged_onlyTutor() {
        // given
        StateChangeEvent event = mockStateChangeEvent(true, false);
        mediator.registerTutor(tutor);

        // when
        stateChangedHandler.onStateChange(event);

        // then
        verify(tutor).processUserInteraction(any(EndHandler.class));
    }

    @Test
    public void shouldNotifyClientsOnStateChanged_onlyBonus() {
        // given
        StateChangeEvent event = mockStateChangeEvent(true, false);
        mediator.registerBonus(bonus);

        // when
        stateChangedHandler.onStateChange(event);

        // then
        verify(bonus).processUserInteraction();
    }

    @Test
    public void shouldNotifyClientsOnStateChanged_bothClients() {
        // given
        StateChangeEvent event = mockStateChangeEvent(true, false);
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                EndHandler handler = (EndHandler) invocation.getArguments()[0];
                handler.onEnd();
                return null;
            }
        }).when(tutor).processUserInteraction(any(EndHandler.class));
        mediator.registerTutor(tutor);
        mediator.registerBonus(bonus);

        // when
        stateChangedHandler.onStateChange(event);

        // then
        InOrder inOrder = Mockito.inOrder(tutor, bonus);
        inOrder.verify(tutor).processUserInteraction(any(EndHandler.class));
        inOrder.verify(bonus).processUserInteraction();
    }

    @Test
    public void shouldTerminateClientsOnPageChange() {
        // given
        PlayerEvent event = mock(PlayerEvent.class);
        mediator.registerTutor(tutor);
        mediator.registerBonus(bonus);

        // when
        pageUnloadedhandler.onPlayerEvent(event);

        // then
        verify(bonus).terminatePowerFeedback();
        verify(tutor).terminatePowerFeedback();
    }

    private void initMocks() {
        eventsBus = mock(EventsBus.class);
        pageScopeFactory = mock(PageScopeFactory.class);
        tutor = mock(PowerFeedbackTutorClient.class);
        bonus = mock(PowerFeedbackBonusClient.class);
    }

    private StateChangeEvent mockStateChangeEvent(boolean isUserInteract, boolean isReset) {
        StateChangeEvent event = mock(StateChangeEvent.class);
        IUniqueModule sender = mock(IUniqueModule.class);
        StateChangedInteractionEvent scie = new StateChangedInteractionEvent(isUserInteract, isReset, sender);
        when(event.getValue()).thenReturn(scie);
        return event;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void initEventHandlersInterception() {
        doAnswer(new Answer<Void>() {

            final EventType<StateChangeEventHandler, StateChangeEventTypes> STATE_CHANGED_TYPE = StateChangeEvent.getType(OUTCOME_STATE_CHANGED);
            final EventType<PlayerEventHandler, PlayerEventTypes> PAGE_UNLOADED_TYPE = PlayerEvent.getType(PlayerEventTypes.PAGE_UNLOADED);
            final EventType<PlayerEventHandler, PlayerEventTypes> TEST_PAGE_LOADED_TYPE = PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED);

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                EventType type = (EventType) invocation.getArguments()[0];
                Object handler = invocation.getArguments()[1];

                if (handler instanceof StateChangeEventHandler && type == STATE_CHANGED_TYPE) {
                    stateChangedHandler = (StateChangeEventHandler) handler;
                } else if (handler instanceof PlayerEventHandler && type == PAGE_UNLOADED_TYPE) {
                    pageUnloadedhandler = (PlayerEventHandler) handler;
                } else if (handler instanceof PlayerEventHandler && type == TEST_PAGE_LOADED_TYPE) {
                    testPageLoadedHandler = (PlayerEventHandler) handler;
                }

                return null;
            }
        }).when(eventsBus).addHandler(any(EventType.class), any(EventHandler.class), any(EventScope.class));
    }
}
