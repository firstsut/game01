package sut.game01.core.sprite;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.screen.TestScreen;

/**
 * Created by first on 22/3/2557.
 */
public class Basketball {
    public Sprite sprite;
    private int SpriteIndex = 0;
    public boolean hasLoaded =false;
    private PolygonShape shape;
    private TestScreen testScreen;
    public static int score =0,time=15,level=1;
    private BodyDef bf;
    private int offset = 0;
    public Body body;
    public static final Font TITLE_FONT = PlayN.graphics().createFont(
            "Helvetica",
            Font.Style.BOLD,
            24
    );

    public Basketball(final World world,final float x, final  float y){
        sprite = SpriteLoader.getSprite("images/basketball.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(SpriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,
                        sprite.height()/2f);
                sprite.layer().setTranslation(x,y);
                body = initPhysicsBody(world,TestScreen.M_PER_PIXEL*x,TestScreen.M_PER_PIXEL*y);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error", cause);
            }
        });
    }
    public Layer layer(){
        return sprite.layer();
    }

    public Body initPhysicsBody(World world,float x,float y){
        bf=new BodyDef();
        bf.type= BodyType.DYNAMIC;
        bf.position=new Vec2(0,0);
        Body body =world.createBody(bf);
        shape=new PolygonShape();
        shape.setAsBox(50* TestScreen.M_PER_PIXEL/2,sprite.layer().height()*TestScreen.M_PER_PIXEL/2);
        FixtureDef fd =new FixtureDef();
        CircleShape a=new CircleShape();
        a.m_radius=1.0f;
        fd.shape=a;
        fd.density=0.119f;
        fd.friction=0.1f;
        fd.restitution=0.4f;
        body.createFixture(fd);
        body.setAngularDamping(0.6f);
        body.setTransform(new Vec2(x,y),100f);
        return body;
    }

    public void update(int delta){
        if(!hasLoaded) return;;
        SpriteIndex=offset+((SpriteIndex+1)%1);
        sprite.setSprite(SpriteIndex);

    }
    public void paint(Clock clock){
        if (!hasLoaded) return;
        sprite.layer().setTranslation((body.getPosition().x / TestScreen.M_PER_PIXEL),
                body.getPosition().y / TestScreen.M_PER_PIXEL);
        sprite.layer().setRotation(body.getAngle());
    }
}
