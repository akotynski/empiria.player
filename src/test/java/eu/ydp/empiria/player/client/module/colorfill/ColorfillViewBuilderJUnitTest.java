package eu.ydp.empiria.player.client.module.colorfill;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.presenter.handlers.ColorButtonClickListener;
import eu.ydp.empiria.player.client.module.colorfill.structure.*;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillAreaClickListener;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ColorfillViewBuilderJUnitTest {

    private ColorfillViewBuilder colorfillViewBuilder;

    @Mock
    private ColorfillInteractionView interactionView;
    @Mock
    private ColorfillInteractionPresenter interactionPresenter;

    @Before
    public void setUp() throws Exception {
        colorfillViewBuilder = new ColorfillViewBuilder(interactionView);
    }

    @Test
    public void shouldBuildImageAndButtons() throws Exception {
        // given
        ColorfillInteractionBean bean = new ColorfillInteractionBean();
        ButtonsContainer buttonsContainer = new ButtonsContainer();
        ColorButton colorButton = new ColorButton();
        colorButton.setRgb("00ff00");
        colorButton.setDescription("colorButtonDescription");

        List<ColorButton> buttons = Lists.newArrayList(colorButton);
        buttonsContainer.setButtons(buttons);

        EraserButton eraserButton = new EraserButton();
        eraserButton.setDescription("eraserDescription");

        buttonsContainer.setEraserButton(eraserButton);
        bean.setButtons(buttonsContainer);
        Image image = new Image();
        bean.setImage(image);
        Image correctImage = new Image();
        bean.setCorrectImage(correctImage);

        // when
        colorfillViewBuilder.buildView(bean, interactionPresenter);

        // then
        verify(interactionView).createButton(ColorModel.createFromRgbString("00ff00"), colorButton.getDescription());
        verify(interactionView).createButton(ColorfillViewBuilder.ERASING_COLOR, eraserButton.getDescription());
        verify(interactionView).setAreaClickListener(any(ColorfillAreaClickListener.class));
        verify(interactionView).setButtonClickListener(any(ColorButtonClickListener.class));
        verify(interactionView).setImage(image);
        verify(interactionView).setCorrectImage(correctImage);
    }

}
