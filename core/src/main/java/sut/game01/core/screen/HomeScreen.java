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
        Image bgImage = assets().getImage("images/nba-slam-dunk-165899 (Copy).jpg");
        Image btnstart = assets().getImage("images/start1.png");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        ImageLayer btnlayer = graphics().createImageLayer(btnstart);
        btnlayer.setTranslation(40f,200f);
        layer.add(bgLayer);
        layer.add(btnlayer);



        btnlayer.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.push(new TestScreen(ss));
            }
        });


    }
}
