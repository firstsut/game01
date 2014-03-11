package sut.game01.core.screen;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.PolygonContact;
import playn.core.*;
import playn.core.Font;
import playn.core.Image;
import playn.core.util.Clock;
import react.UnitSlot;
import sut.game01.core.debug.DebugDrawBox2D;
import sut.game01.core.sprite.Mario;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.*;
import tripleplay.ui.Button;
import tripleplay.ui.layout.AxisLayout;

import java.awt.*;
import java.awt.Label;

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

    private Body ground;
    private Body ground1;
    private Body ground2;
    private Sound sound;
    private TextLayout a;
    public static final Font TITLE_FONT =
            graphics().createFont(
                    "Helvetica",
                    Font.Style.PLAIN,
                    24);
    
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
        bgci.setTranslation(-15f,130f);
        layer.add(bgLayer);
        layer.add(bgci);
       // graphics().rootLayer().add(bgLayer);
        //graphics().rootLayer().add(bgci);

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

        ground = world.createBody(new BodyDef());
        ground1 = world.createBody(new BodyDef());
        ground2 = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        PolygonShape groundShape1 = new PolygonShape();
        PolygonShape groundShape2 = new PolygonShape();
        PolygonShape groundShape3 = new PolygonShape();
        PolygonShape groundShape4 = new PolygonShape();
        PolygonShape groundShape5 = new PolygonShape();
        PolygonShape groundShape6 = new PolygonShape();
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
        groundShape4.setAsEdge(new Vec2(0.3f, 6.1f),
                new Vec2(0.3f, 17f));
        ground2.createFixture(groundShape4, 0.0f);
        groundShape5.setAsEdge(new Vec2(3.2f, 6.3f),
                new Vec2(3.2f, 17f));
        ground2.createFixture(groundShape5, 0.0f);
        groundShape6.setAsEdge(new Vec2(1f, height-0.2f),
                new Vec2(width-21f, height-0.2f));
        ground1.createFixture(groundShape6, 0.0f);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody() == ground1){
                    mario.layer().destroy();
                    mario.body.setActive(false);
                    mario = new Mario(world,450f,450f);
                    layer.add(mario.layer());
                    int score=0;
                    score+=1;



                }else if (contact.getFixtureA().getBody() == ground2){
                    //  sound=assets().getSound("sound/Hit_Hurt8");
                    //  sound.play();
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }

        });
        mario = new Mario(world,450, 450f);
        layer.add(mario.layer());


    }

   @Override
   public void update(int delta){
        mario.update(delta);
        world.step(0.033f, 10, 10);
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
        imageBack = assets().getImage("images/menu.png");
        Image btnleft = assets().getImage("images/left.png");
        Image btnright = assets().getImage("images/right.png");
        Image btnup = assets().getImage("images/up.png");
        imageLayer1 = graphics().createImageLayer(imageBack);
        ImageLayer btnlayerleft = graphics().createImageLayer(btnleft);
        ImageLayer btnlayerright = graphics().createImageLayer(btnright);
        ImageLayer btnlayerup = graphics().createImageLayer(btnup);
        btnlayerleft.setTranslation(530f,450f);
        btnlayerright.setTranslation(585f,450f);
        btnlayerup.setTranslation(568f, 410f);
        imageLayer1.setTranslation(15f,10f);
        layer.add(imageLayer1);
        layer.add(btnlayerleft);
        layer.add(btnlayerright);
        layer.add(btnlayerup);
        imageLayer1.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.remove(TestScreen.this);
            }
        }
        );
        btnlayerleft.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                mario.body.applyForce(new Vec2(-20f, 0f), mario.body.getPosition());
            }
        });
        btnlayerright.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                mario.body.applyForce(new Vec2(20f,0f),mario.body.getPosition());
            }
        });
        btnlayerup.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                mario.body.applyForce(new Vec2(-95f, -230), mario.body.getPosition());
            }
        });
        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), layer)
        ;
        root.setSize(width(),height());
            root.add(new tripleplay.ui.Label("SCORE:").addStyles(Style.FONT.is(TestScreen.TITLE_FONT)));


    }

}
