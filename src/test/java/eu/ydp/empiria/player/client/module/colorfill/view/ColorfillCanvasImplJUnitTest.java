package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.module.colorfill.fill.CanvasImageData;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ColorfillCanvasImplJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {
    private static final int POSITION_Y = 20;
    private static final int POSITION_X = 10;

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(CanvasImageView.class)
                    .toInstance(canvasStubView);
            binder.bind(UserInteractionHandlerFactory.class)
                    .toInstance(userInteractionHandlerFactory);
            binder.bind(PositionHelper.class)
                    .toInstance(positionHelper);
            binder.bind(CanvasImageDataProvider.class).toInstance(canvasImageDataProvider);
        }
    }

    private final CanvasImageView canvasStubView = mock(CanvasImageView.class);
    private final UserInteractionHandlerFactory userInteractionHandlerFactory = mock(UserInteractionHandlerFactory.class);
    private final PositionHelper positionHelper = mock(PositionHelper.class);
    private final CanvasImageDataProvider canvasImageDataProvider = mock(CanvasImageDataProvider.class);
    private ColorfillCanvasImpl testObj;
    private Context2d context2d;
    private Canvas canvas;
    private ImageData imageData;

    @Before
    public void before() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());

        testObj = injector.getInstance(ColorfillCanvasImpl.class);
        canvas = mock(Canvas.class);
        context2d = mock(Context2d.class);
        imageData = mock(ImageData.class);
        doReturn(imageData).when(context2d)
                .getImageData(anyDouble(), anyDouble(), anyDouble(), anyDouble());
        doReturn(context2d).when(canvas)
                .getContext2d();
        doReturn(canvas).when(canvasStubView)
                .getCanvas();

        doReturn(POSITION_X).when(positionHelper)
                .getXPositionRelativeToTarget(any(NativeEvent.class), Matchers.any(Element.class));
        doReturn(POSITION_Y).when(positionHelper)
                .getYPositionRelativeToTarget(any(NativeEvent.class), Matchers.any(Element.class));

    }

    @Test
    public void postConstruct() throws Exception {
        ArgumentCaptor<LoadHandler> argumentCaptor = ArgumentCaptor.forClass(LoadHandler.class);
        verify(canvasStubView).setPanelStyle("qp-colorfill-img");
        verify(canvasStubView).setImageLoadHandler(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isNotNull();
    }

    @Test
    public void bindView() throws Exception {
        // before
        ColorfillAreaClickListener colorfillAreaClickListener = mock(ColorfillAreaClickListener.class);
        testObj.setAreaClickListener(colorfillAreaClickListener);

        ArgumentCaptor<LoadHandler> argumentCaptor = ArgumentCaptor.forClass(LoadHandler.class);
        verify(canvasStubView).setImageLoadHandler(argumentCaptor.capture());
        argumentCaptor.getValue()
                .onLoad(null);
        verify(userInteractionHandlerFactory).applyUserClickHandler(any(Command.class), eq(canvas));
    }

    @Test
    public void onAreaClick() throws Exception {
        // before
        ColorfillAreaClickListener colorfillAreaClickListener = mock(ColorfillAreaClickListener.class);
        testObj.setAreaClickListener(colorfillAreaClickListener);

        ArgumentCaptor<LoadHandler> loadHandlerCaptor = ArgumentCaptor.forClass(LoadHandler.class);
        verify(canvasStubView).setImageLoadHandler(loadHandlerCaptor.capture());
        loadHandlerCaptor.getValue()
                .onLoad(null);

        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(userInteractionHandlerFactory).applyUserClickHandler(commandCaptor.capture(), eq(canvas));

        NativeEvent nativeEvent = mock(NativeEvent.class);
        commandCaptor.getValue()
                .execute(nativeEvent);

        ArgumentCaptor<Area> areaCaptor = ArgumentCaptor.forClass(Area.class);
        verify(colorfillAreaClickListener).onAreaClick(areaCaptor.capture());
        verify(nativeEvent).preventDefault();
        Area area = areaCaptor.getValue();
        assertThat(area.getX()).isEqualTo(POSITION_X);
        assertThat(area.getY()).isEqualTo(POSITION_Y);

    }

    @Test
    public void onAreaClickWrongCoordinates() throws Exception {
        ColorfillAreaClickListener colorfillAreaClickListener = mock(ColorfillAreaClickListener.class);
        testObj.setAreaClickListener(colorfillAreaClickListener);

        doReturn(-10).when(positionHelper)
                .getXPositionRelativeToTarget(any(NativeEvent.class), Matchers.any(Element.class));
        doReturn(-15).when(positionHelper)
                .getYPositionRelativeToTarget(any(NativeEvent.class), Matchers.any(Element.class));

        ArgumentCaptor<LoadHandler> loadHandlerCaptor = ArgumentCaptor.forClass(LoadHandler.class);
        verify(canvasStubView).setImageLoadHandler(loadHandlerCaptor.capture());
        loadHandlerCaptor.getValue()
                .onLoad(null);

        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(userInteractionHandlerFactory).applyUserClickHandler(commandCaptor.capture(), eq(canvas));

        NativeEvent nativeEvent = mock(NativeEvent.class);
        commandCaptor.getValue()
                .execute(nativeEvent);

        verify(colorfillAreaClickListener, times(0)).onAreaClick(any(Area.class));

    }

    @Test
    public void setImage() throws Exception {
        // given
        Image image = new Image();
        image.setSrc("src");
        image.setHeight(20);
        image.setWidth(32);

        // when
        testObj.setImage(image);

        // then
        verify(canvasStubView).setImageUrl(eq("src"), eq(32), eq(20));
    }

    @Test
    public void reset() throws Exception {
        // when
        testObj.reset();

        // then
        verify(canvasStubView).reload();
        verify(canvasImageDataProvider).getCanvasImageData(canvasStubView);
    }

    @Test
    public void flushImageToCanvas() throws Exception {
        ReflectionsUtils util = new ReflectionsUtils();
        CanvasImageData imageData = mock(CanvasImageData.class);
        util.setValueInObjectOnField("imageData", testObj, imageData);
        testObj.flushImageToCanvas();
        verify(imageData).flush();
    }

    @Test
    public void asWidget() throws Exception {
        // when
        testObj.asWidget();

        // then
        verify(canvasStubView).asWidget();
    }

}
