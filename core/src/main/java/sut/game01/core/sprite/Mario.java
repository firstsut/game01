package sut.game01.core.sprite;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.screen.TestScreen;

public class Mario {
    private Sprite sprite;
    private int SpriteIndex = 0;
    private boolean hasLoaded = false;
    private int t;
    private int hp=100;



    public enum State{
        IDLE,DIEm,KICK
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 0;
    private Body body;

    public Mario(final World world,final float x, final  float y){
        sprite = SpriteLoader.getSprite("images/basketball.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(SpriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,
                                         sprite.height()/2f);
                sprite.layer().setTranslation(x,y + 13f);
                body = initPhysicsBody(world,TestScreen.M_PER_PIXEL*x,TestScreen.M_PER_PIXEL*y);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error", cause);
            }
        });
        PlayN.keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if (event.key()== Key.LEFT){
                    body.applyForce(new Vec2(-20f,0f),body.getPosition());
                }else if (event.key()== Key.UP){
                    body.applyForce(new Vec2(-100f, -260), body.getPosition());
                }else if (event.key()== Key.RIGHT){
                    body.applyForce(new Vec2(20f,0f),body.getPosition());
                }
            }

            @Override
            public void onKeyTyped(Keyboard.TypedEvent event) {

            }

            @Override
            public void onKeyUp(Keyboard.Event event) {


            }
        });
    }
    public Layer layer(){
        return sprite.layer();
    }

    

    private Body initPhysicsBody(World world,float x,float y){
        BodyDef bf=new BodyDef();
        bf.type= BodyType.DYNAMIC;
        bf.position=new Vec2(0,0);
        Body body =world.createBody(bf);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(56* TestScreen.M_PER_PIXEL/2,sprite.layer().height()*TestScreen.M_PER_PIXEL/2);
        FixtureDef fd =new FixtureDef();
        fd.shape=shape;
        fd.density=0.1f;
        fd.friction=0.1f;
        fd.restitution=0.4f;
        body.createFixture(fd);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
    }





    public void update(int delta){
        if(!hasLoaded) return;;
        SpriteIndex=offset+((SpriteIndex+1)%1);
        sprite.setSprite(SpriteIndex);
       


    }
    public void paint(Clock clock){
        if (!hasLoaded) return;
        sprite.layer().setTranslation((body.getPosition().x / TestScreen.M_PER_PIXEL) - 10,
                body.getPosition().y / TestScreen.M_PER_PIXEL);
        sprite.layer().setRotation(body.getAngle());

    }
}
