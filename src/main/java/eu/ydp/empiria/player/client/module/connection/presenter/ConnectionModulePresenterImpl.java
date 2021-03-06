package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventHandler;
import eu.ydp.gwtutil.client.collections.KeyValue;

import java.util.List;

import static eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType.*;

public class ConnectionModulePresenterImpl implements ConnectionModulePresenter, PairConnectEventHandler {

    private MatchInteractionBean bean;

    private ConnectionModuleModel model;

    private ShowAnswersType lastAnswerType = ShowAnswersType.USER;

    @Inject
    private MultiplePairModuleView moduleView;

    private ModuleSocket moduleSocket;

    @Override
    public void setModuleSocket(ModuleSocket moduleSocket) {
        this.moduleSocket = moduleSocket;
    }

    @Override
    public void bindView() {
        moduleView.addPairConnectEventHandler(this);
        moduleView.setBean(bean);
        moduleView.setModuleSocket(moduleSocket);
        moduleView.bindView();

        moduleView.reset();
    }

    @Override
    public void reset() {
        moduleView.reset();
    }

    @Override
    public void setModuleView(MultiplePairModuleView moduleView) {
        this.moduleView = moduleView;
    }

    @Override
    public void setModel(ConnectionModuleModel model) {
        this.model = model;
    }

    @Override
    public void setBean(MatchInteractionBean bean) {
        this.bean = bean;
    }

    @Override
    public void setLocked(boolean locked) {
        moduleView.setLocked(locked);
    }

    @Override
    public void showAnswers(ShowAnswersType mode) {
        List<KeyValue<String, String>> answers = (mode == ShowAnswersType.CORRECT) ? model.getCorrectAnswers() : model.getCurrentAnswers();
        showAnswers(answers, (mode == ShowAnswersType.CORRECT) ? NONE : NORMAL);
        lastAnswerType = mode;
    }

    @Override
    public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
        setAnswersMarked(mode == MarkAnswersMode.MARK, (type == MarkAnswersType.CORRECT) ? CORRECT : WRONG);

    }

    @Override
    public Widget asWidget() {
        return moduleView.asWidget();
    }

    @Override
    public void onConnectionEvent(PairConnectEvent event) {

        ConnectionDirectedPairDTO pair = getDirectedPair(event);

        switch (event.getType()) {
            case CONNECTED:
                addAnswerToResponseIfConnectionValidOnUserAction(event, pair);
                break;
            case DISCONNECTED:
                disconnectOnUserAction(event, pair);
                break;
            case REPAINT_VIEW:
                repaintViewFromResponseModel();
                break;
            case WRONG_CONNECTION:
            default:
                break;
        }
    }


    private void repaintViewFromResponseModel() {
        if (moduleView.isAttached()) {
            reset();
            showAnswers(lastAnswerType);
        }
    }

    private void disconnectOnUserAction(PairConnectEvent event, ConnectionDirectedPairDTO pair) {
        if (event.isUserAction() && pair.getSource() != null && pair.getTarget() != null) {
            model.removeAnswer(pair.toString());
        }
    }

    private void addAnswerToResponseIfConnectionValidOnUserAction(PairConnectEvent event, ConnectionDirectedPairDTO pair) {
        if (event.isUserAction()) {
            addAnswerToResponseIfConnectionValid(event, pair);
        }
    }

    private void addAnswerToResponseIfConnectionValid(PairConnectEvent event, ConnectionDirectedPairDTO pair) {
        boolean isResponseExists = model.checkUserResonseContainsAnswer(pair.toString());
        if (isConnectionValid(pair) && !isResponseExists) {
            model.addAnswer(pair.toString());
        } else {
            moduleView.disconnect(event.getSourceItem(), event.getTargetItem());
        }
    }

    private ConnectionDirectedPairDTO getDirectedPair(PairConnectEvent event) {
        String start = event.getSourceItem();
        String end = event.getTargetItem();

        return createDirectedPair(start, end);
    }

    private ConnectionDirectedPairDTO createDirectedPair(String start, String end) {
        ConnectionDirectedPairDTO pair = new ConnectionDirectedPairDTO();
        List<String> sourceChoicesIdentifiersSet = bean.getSourceChoicesIdentifiersSet();
        List<String> targetChoicesIdentifiersSet = bean.getTargetChoicesIdentifiersSet();

        setDirectedPairNodeByIdentyfier(pair, start, sourceChoicesIdentifiersSet, targetChoicesIdentifiersSet);
        setDirectedPairNodeByIdentyfier(pair, end, sourceChoicesIdentifiersSet, targetChoicesIdentifiersSet);

        return pair;
    }

    private void setDirectedPairNodeByIdentyfier(ConnectionDirectedPairDTO pair, String choosenIdentifier, List<String> sourceChoicesIdentifiersSet,
                                                 List<String> targetChoicesIdentifiersSet) {
        if (sourceChoicesIdentifiersSet.contains(choosenIdentifier)) {
            pair.setSource(choosenIdentifier);
        } else if (targetChoicesIdentifiersSet.contains(choosenIdentifier)) {
            pair.setTarget(choosenIdentifier);
        }
    }

    private boolean isConnectionValid(ConnectionDirectedPairDTO pair) {
        int errorsCount = 0;

        if (isPairValid(pair)) {
            errorsCount++;
        } else if (isMaxAssociationAchieved()) {
            errorsCount++;
        } else if (isSourceMatchMaxAchieved(pair)) {
            errorsCount++;
        } else if (isTargetMatchMaxAchieved(pair)) {
            errorsCount++;
        }

        return errorsCount == 0;
    }

    private boolean isPairValid(ConnectionDirectedPairDTO pair) {
        return pair.getSource() == null || pair.getTarget() == null;
    }

    /**
     * Individually, each choice has a matchMax attribute that controls how many
     * pairings it can be part of.
     */
    private boolean matchMaxCondition(String identifier) {
        SimpleAssociableChoiceBean beanChoiceIdentifier = bean.getChoiceByIdentifier(identifier);
        int matchMax = beanChoiceIdentifier.getMatchMax();
        int currentChoicePairingsNumber = model.getCurrentChoicePairingsNumber(identifier);

        return matchMax > 0 && currentChoicePairingsNumber >= matchMax;
    }

    private boolean isSourceMatchMaxAchieved(ConnectionDirectedPairDTO pair) {
        return matchMaxCondition(pair.getSource());
    }

    private boolean isTargetMatchMaxAchieved(ConnectionDirectedPairDTO pair) {
        return matchMaxCondition(pair.getTarget());
    }

    /**
     * The maxAssociations attribute controls the maximum number of pairings the
     * user is allowed to make overall.
     */
    private boolean isMaxAssociationAchieved() {
        int maxAssociations = bean.getMaxAssociations();
        int currentOverallPairingsNumber = model.getCurrentOverallPairingsNumber();

        return maxAssociations > 0 && currentOverallPairingsNumber >= maxAssociations;
    }

    /**
     * Sets connections in view using given {@link KeyValue} collection for
     * defined {@link MultiplePairModuleConnectType}
     *
     * @param answers
     * @param type
     */
    private void showAnswers(List<KeyValue<String, String>> answers, MultiplePairModuleConnectType type) {
        moduleView.reset();
        for (KeyValue<String, String> answer : answers) {
            moduleView.connect(answer.getKey(), answer.getValue(), type);
        }
    }

    /**
     * Marks / unmarks answers
     *
     * @param markMode    - {@link Boolean} mark/unmark
     * @param markingType - {@link MultiplePairModuleConnectType#CORRECT} or
     *                    {@link MultiplePairModuleConnectType#WRONG}
     */
    private void setAnswersMarked(boolean markMode, MultiplePairModuleConnectType markingType) {
        List<Boolean> responseEvaluated = model.evaluateResponse();
        List<KeyValue<String, String>> currentAnswers = model.getCurrentAnswers();

        int responseCnt = 0;
        for (Boolean isCorrect : responseEvaluated) {
            MultiplePairModuleConnectType type = (isCorrect) ? MultiplePairModuleConnectType.CORRECT : MultiplePairModuleConnectType.WRONG;
            KeyValue<String, String> answersPair = currentAnswers.get(responseCnt);
            if (markingType.equals(type)) {
                connectOrDisconnectByMarkMode(markMode, type, answersPair);
            }
            responseCnt++;
        }
    }

    private void connectOrDisconnectByMarkMode(boolean markMode, MultiplePairModuleConnectType type, KeyValue<String, String> answersPair) {

        moduleView.disconnect(answersPair.getKey(), answersPair.getValue());

        if (markMode) {
            moduleView.connect(answersPair.getKey(), answersPair.getValue(), type);
        } else {
            moduleView.connect(answersPair.getKey(), answersPair.getValue(), MultiplePairModuleConnectType.NORMAL);
        }
    }
}
