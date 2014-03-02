package sut.game01.core.screen;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.util.Clock;
import react.UnitSlot;
import playn.core.ImageLayer;
import sut.game01.core.debug.DebugDrawBox2D;
import sut.game01.core.sprite.Mario;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Button;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.layout.AxisLayout;

import static playn.core.PlayN.*;

import static playn.core.PlayN.graphics;

public class TestScreen extends UIScreen {
    public static float M_PER_PIXEL = 1/26.666667f;
    private DebugDrawBox2D debugDraw;
    private static boolean showDebugDraw=false;
    private static int width=24;
    public static int height=18;
    private World world;
    private Image imageBack;
    private ImageLayer imageLayer1;
    private	float x = 0f;
    private	float y = 0f;
    private final ScreenStack ss;
    public TestScreen(ScreenStack ss){
        this.ss = ss;
    }
    private Root root;
    private Mario mario;
    
    @Override
    public void wasAdded() {
       Vec2 Gavity = new Vec2(0.0f,10.0f);
        world=new World(Gavity,true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        super.wasAdded();
       Image bgImage = assets().getImage("images/1916x1080-gameBG_3PS (Copy).jpg");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
         Image ci = assets().getImage("images/Basketball_Hoop.png");
        ImageLayer bgci = graphics().createImageLayer(ci);
        bgci.setTranslation(-10f,130f);
        graphics().rootLayer().add(bgLayer);
        graphics().rootLayer().add(bgci);

        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int)(width/TestScreen.M_PER_PIXEL),
                    (int)(height/TestScreen.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw=new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                               DebugDraw.e_jointBit |
                               DebugDraw.e_aabbBit
            );
            debugDraw.setCamera(0,0,1f/TestScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }
        Body ground = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        PolygonShape groundShape1 = new PolygonShape();
        PolygonShape groundShape2 = new PolygonShape();
        PolygonShape groundShape3 = new PolygonShape();
        PolygonShape groundShape4 = new PolygonShape();
        PolygonShape groundShape5 = new PolygonShape();
        groundShape.setAsEdge(new Vec2(0f, height-0),
                new Vec2(width-0f, height-0));
        ground.createFixture(groundShape, 0.0f);

        groundShape1.setAsEdge(new Vec2(0f, height-18),
                new Vec2(width-0f, height-18));
        ground.createFixture(groundShape1, 0.0f);
        groundShape2.setAsEdge(new Vec2(0f, 0f),
                new Vec2(-0f, height));
        ground.createFixture(groundShape2, 0.0f);
        groundShape3.setAsEdge(new Vec2(width-0f,0f),
                new Vec2(width, height));
        ground.createFixture(groundShape3, 0.0f);
        groundShape4.setAsEdge(new Vec2(0.5f, 8f),
                new Vec2(0.5f, 6f));
        ground.createFixture(groundShape4, 0.0f);
          groundShape5.setAsEdge(new Vec2(3.6f, 6.5f),
                new Vec2(3.6f, 6.3f));
        ground.createFixture(groundShape5, 0.0f);
       

        mario = new Mario(world,500f, 450f);

       

        layer.add(mario.layer());
       
       // graphics().rootLayer().add(mario.layer());

    }





   @Override
   public void update(int delta){
        mario.update(delta);
        world.step(0.033f,10,10);
       super.update(delta);


    }

    @Override
    public void paint(Clock clock){
      super.paint(clock);
       if(showDebugDraw){
           debugDraw.getCanvas().clear();
           world.drawDebugData();
       }
        mario.paint(clock);

    }

    @Override
    public void wasShown() {
        super.wasShown();
        final Screen home = new HomeScreen(ss);
        /*
        imageBack = assets().getImage("images/back.png");
        imageLayer1 = graphics().createImageLayer(imageBack);
        graphics().rootLayer().add(imageLayer1);
        imageLayer1.setTranslation(x, y);
        */
        /*
        imageBack.addListener(new Pointer.Adapter(){
            public void onEmit(){
                ss.remove(TestScreen.this);
            }
        });*/


        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), layer
        );
        root.setSize(width(), height());
        root.add(
                new Button("Back").onClick(new UnitSlot(){
                    public void onEmit(){
                        ss.remove(TestScreen.this);
                    }
                }
                ));

    }

}
