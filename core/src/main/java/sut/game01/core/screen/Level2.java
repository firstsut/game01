package sut.game01.core.screen;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Clock;
import react.UnitSlot;
import sut.game01.core.debug.DebugDrawBox2D;
import sut.game01.core.sprite.Basketball;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Button;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by first on 22/3/2557.
 */
public class Level2 extends UIScreen {
    public static float M_PER_PIXEL = 1/26.666667f;
    private DebugDrawBox2D debugDraw;
    private static boolean showDebugDraw=false;
    private static int width=24;
    public static int height=18;
    private World world;
    private Image imageBack;
    public static boolean rootOpenScore=true,rootOpenTime=true,rootOpenLevel=true;
    private Image bgImage,ci,bgwin,bgover,btnleft,btnright,btnup;
    private ImageLayer imageLayer1,bgLayer,bgci,bgLayerwin,bgLayerover,btnlayerleft,btnlayerright,btnlayerup;
    private	float x = 0f;
    private	float y = 0f;
    private Root root,root1,root2,root3;
    private final ScreenStack ss;
    private PolygonShape groundShape,groundShape1,groundShape2,groundShape3,groundShape4,groundShape5,groundShape6,groundShape7,groundShape8;
    private Basketball basketball;
    private Body ground,ground1,ground2,ground3,ground4,ground5;
    private Sound sound,win,over,shoot,bottom,edgeleft,edge;

    public Level2(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasAdded() {
        Vec2 Gavity = new Vec2(0.0f,4.5f);
        world=new World(Gavity,true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        super.wasAdded();
        bgImage = assets().getImage("images/1916x1080-gameBG_3PS (Copy).jpg");
        bgLayer = graphics().createImageLayer(bgImage);
        ci = assets().getImage("images/Basketball_Hoop.png");
        bgci = graphics().createImageLayer(ci);
        bgci.setTranslation(-15f,130f);
        layer.add(bgLayer);
        layer.add(bgci);
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
        ground3 = world.createBody(new BodyDef());
        ground4 = world.createBody(new BodyDef());
        ground5 = world.createBody(new BodyDef());
        groundShape = new PolygonShape();
        groundShape1 = new PolygonShape();
        groundShape2 = new PolygonShape();
        groundShape3 = new PolygonShape();
        groundShape4 = new PolygonShape();
        groundShape5 = new PolygonShape();
        groundShape6 = new PolygonShape();
        groundShape7 = new PolygonShape();
        groundShape8 = new PolygonShape();
        groundShape.setAsEdge(new Vec2(0f, height-0),
                new Vec2(width-0f, height-0));
        ground.createFixture(groundShape, 0.0f);

        groundShape1.setAsEdge(new Vec2(0f, height-18),
                new Vec2(width-0f, height-18));
        ground.createFixture(groundShape1, 0.0f);
        //ซ้ายสุด
        groundShape2.setAsEdge(new Vec2(0f, 0f),
                new Vec2(-0f, height));
        ground1.createFixture(groundShape2, 0.0f);
        groundShape3.setAsEdge(new Vec2(width-0f,0f),
                new Vec2(width, height));
        ground.createFixture(groundShape3, 0.0f);
        //ซ้ายขวาห่วง
        groundShape4.setAsEdge(new Vec2(0.4f, 6.1f),
                new Vec2(1.2f, 10f));
        ground2.createFixture(groundShape4, 0.0f);
        groundShape5.setAsEdge(new Vec2(3.2f, 6.3f),
                new Vec2(2.8f, 10f));
        ground2.createFixture(groundShape5, 0.0f);
        //ลงห่วง
        groundShape6.setAsEdge(new Vec2(1f, height-8.5f),
                new Vec2(width-21f, height-8.5f));
        ground3.createFixture(groundShape6, 0.0f);
        //พื้นลองเมื่อไม่ลงห่วง
        groundShape7.setAsEdge(new Vec2(1f, height-0.2f),
                new Vec2(width-12f, height-0.2f));
        ground4.createFixture(groundShape7, 0.0f);
        //ขอบเขตการโยน
        groundShape8.setAsEdge(new Vec2(12.8f,20f),
                new Vec2(12.8f, 17f));
        ground5.createFixture(groundShape8, 0.0f);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody() == ground3){
                    sound=assets().getSound("sound/Clap");
                    sound.play();
                    basketball.layer().destroy();
                    basketball.body.setActive(false);
                    basketball = new Basketball(world,450f,450f);
                    layer.add(basketball.layer());
                    root.removeAll();
                    rootOpenScore=true;
                    basketball.score+=1;
                }else if (contact.getFixtureA().getBody() == ground1){
                    edgeleft=assets().getSound("sound/edgeleft");
                    edgeleft.play();
                    basketball.body.applyLinearImpulse(new Vec2(2.7f,-2.2f),basketball.body.getPosition());

                }else if(contact.getFixtureA().getBody() == ground2){
                    //edge=assets().getSound("sound/edge");
                    //edge.play();
                }else if(contact.getFixtureA().getBody() == ground4){
                    bottom=assets().getSound("sound/bottom1");
                    bottom.play();
                    basketball.layer().destroy();
                    basketball.body.setActive(false);
                    basketball = new Basketball(world,450f,450f);
                    layer.add(basketball.layer());
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
        ActionListener ac=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                root3.setVisible(false);
                basketball.time-=0;
                rootOpenTime=true;
            }


        };
        Timer timer = new Timer(700, ac);
        timer.start();
        basketball = new Basketball(world,450, 450f);
        layer.add(basketball.layer());
    }

    @Override
    public void update(int delta){
        basketball.update(delta);
        world.step(0.033f, 10, 10);
        super.update(delta);
        if (rootOpenScore){
            root = iface.createRoot(
                    AxisLayout.vertical().gap(15),
                    SimpleStyles.newSheet(), layer);
            root.setSize(1100, 50);
            root.add(new tripleplay.ui.Label("Score:"+basketball.score).addStyles(Style.FONT.is(basketball.TITLE_FONT),Style.COLOR.is(0xFFFFFFFF)));
            rootOpenScore = false;
        }
        if (rootOpenLevel){
            root1 = iface.createRoot(
                    AxisLayout.vertical().gap(15),
                    SimpleStyles.newSheet(), layer);
            root1.setSize(800, 50);
            root1.add(new tripleplay.ui.Label("Level:"+basketball.level).addStyles(Style.FONT.is(basketball.TITLE_FONT),Style.COLOR.is(0xFFFFFFFF)));
            rootOpenLevel = false;
        }
        if (rootOpenTime){
            root3 = iface.createRoot(
                    AxisLayout.vertical().gap(15),
                    SimpleStyles.newSheet(), layer);
            root3.setSize(500, 50);
            if(basketball.time>=0){
                root3.add(new tripleplay.ui.Label("Time:"+basketball.time).addStyles(Style.FONT.is(basketball.TITLE_FONT),Style.COLOR.is(0xFFFFFFFF)));
                rootOpenTime = false;
            }else{
                root3.add(new tripleplay.ui.Label("Time:0").addStyles(Style.FONT.is(basketball.TITLE_FONT),Style.COLOR.is(0xFFFFFFFF)));
                rootOpenTime = false;
            }


        }
        if(basketball.time==0){
            if(basketball.score<=5){
                bgover = assets().getImage("images/over.png");
                bgLayerover = graphics().createImageLayer(bgover);
                bgLayerover.setTranslation(0f,0f);
                layer.add(bgLayerover);
                over=assets().getSound("sound/over");
                over.play();
            } else if(basketball.score>5){
                bgwin = assets().getImage("images/winner1.png");
                bgLayerwin = graphics().createImageLayer(bgwin);
                bgLayerwin.setTranslation(0f,0f);
                layer.add(bgLayerwin);
                win=assets().getSound("sound/win");
                win.play();
            }
         }
    }

    @Override
    public void paint(Clock clock){
        super.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
        basketball.paint(clock);
    }

    @Override
    public void wasShown() {
        super.wasShown();
        final Screen home = new HomeScreen(ss);
        imageBack = assets().getImage("images/menu.png");
        btnleft = assets().getImage("images/left.png");
        btnright = assets().getImage("images/right.png");
        btnup = assets().getImage("images/up.png");
        imageLayer1 = graphics().createImageLayer(imageBack);
        btnlayerleft = graphics().createImageLayer(btnleft);
        btnlayerright = graphics().createImageLayer(btnright);
        btnlayerup = graphics().createImageLayer(btnup);
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
                ss.remove(Level2.this);
            }
        }
        );
        btnlayerleft.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                basketball.body.applyForce(new Vec2(-10f, 0f), basketball.body.getPosition());
            }
        });
        btnlayerright.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                basketball.body.applyForce(new Vec2(10f,0f),basketball.body.getPosition());
            }
        });
        btnlayerup.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                shoot=assets().getSound("sound/shoot");
                shoot.play();
                basketball.body.applyForce(new Vec2(-60f, -140), basketball.body.getPosition());
                basketball.body.applyAngularImpulse(-4f);
            }
        });
    }
}
