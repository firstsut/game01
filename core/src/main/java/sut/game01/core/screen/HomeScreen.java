package sut.game01.core.screen;



import org.jbox2d.dynamics.World;
import playn.core.*;

import static playn.core.PlayN.*;

import playn.core.util.Clock;
import react.UnitSlot;
import sut.game01.core.sprite.Basketball;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.game.trans.SlideTransition;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;

public class HomeScreen extends UIScreen {

    private static ScreenStack ss;
    private Root root;
    private int x=200,x1=490,w=0,q=0;
    private Image  bgbas,bgleft,bgImage,btnstart;
    private ImageLayer bgLayerbas,bgLayerleft,bgLayer,btnlayer;
    private Basketball basketball;
    private World world;
    private TestScreen testScreen;

    public HomeScreen(ScreenStack ss){
        this.ss = ss;
    }
    @Override
    public void wasShown() {
        super.wasShown();
        bgImage = assets().getImage("images/Basketball Shots 3D.jpg");
        bgbas = assets().getImage("images/arrowright.png");
        bgleft=assets().getImage("images/arrowleft.png");
        btnstart = assets().getImage("images/button-startgame.jpg");
        bgLayer = graphics().createImageLayer(bgImage);
        btnlayer = graphics().createImageLayer(btnstart);
        bgLayerbas = graphics().createImageLayer(bgbas);
        bgLayerleft=graphics().createImageLayer(bgleft);
        btnlayer.setTranslation(295f,430f);
        layer.add(bgLayer);
        layer.add(btnlayer);
        layer.add(bgLayerbas);
        layer.add(bgLayerleft);
        btnlayer.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.push(new TestScreen(ss));
            }
        });

    }
    @Override
    public void wasAdded(){
        //.wasAdded();
    }

    @Override
    public void update(int delta){
        super.update(delta);

        if (q == 0) {
            x += 0.13f * delta;
            bgLayerbas.setTranslation(x, 430f);
            if (x >= 230) {
                q = 1;
            }
        }
        else {
            x -= 0.13f * delta;
            bgLayerbas.setTranslation(x, 430f);
            if (x <= 200) {
                q = 0;
            }
        }
        if (w == 0) {
            x1-= 0.13f * delta;
            bgLayerleft.setTranslation(x1, 430f);
            if (x1<=460) {
                w = 1;
            }
        }
        else {
            x1 += 0.13f * delta;
            bgLayerleft.setTranslation(x1, 430f);
            if (x1>=490 ) {
                w = 0;
            }
        }
    }

    @Override
    public void paint(Clock clock){
        super.paint(clock);
    }

}
