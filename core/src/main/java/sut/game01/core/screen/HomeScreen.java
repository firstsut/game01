package sut.game01.core.screen;


import playn.core.Font;
import static playn.core.PlayN.*;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;

public class HomeScreen extends UIScreen {

    private ScreenStack ss;
    private Root root;

    public static final Font TITLE_FONT =
            graphics().createFont(
                    "Helvetica",
                    Font.Style.PLAIN,
                    24);

    public HomeScreen(ScreenStack ss){
        this.ss = ss;
    }
    @Override
    public void wasShown() {
        super.wasShown();
        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), layer);

        root.setSize(width(),height());



    }
    @Override
    public void wasAdded(){
        Image bgImage = assets().getImage("images/TheDarkMatter_1920x1080.jpg");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        Image btnstart = assets().getImage("images/start.png");
        ImageLayer btnlayer = graphics().createImageLayer(btnstart);
        Image btnoption = assets().getImage("images/option.png");
        ImageLayer btnlayerop = graphics().createImageLayer(btnoption);
        Image btnexit = assets().getImage("images/exit.png");
        ImageLayer btnlayerexit = graphics().createImageLayer(btnexit);
        btnlayer.setTranslation(380f,10f);
        btnlayerop.setTranslation(380f,70f);
        btnlayerexit.setTranslation(380f,130f);
        layer.add(bgLayer);
        layer.add(btnlayer);
        layer.add(btnlayerop);
        layer.add(btnlayerexit);

        btnlayer.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.push(new TestScreen(ss));
            }
        });


    }
}
