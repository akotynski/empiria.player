package eu.ydp.empiria.player.client.module.accordion.controller;

import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionSectionPresenter;

public class AccordionHorizontalController implements AccordionHideController {

    @Override
    public void hide(AccordionSectionPresenter section) {
        section.hideHorizontally();
    }
}
