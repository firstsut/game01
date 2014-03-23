package sut.game01.core;

import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.screen.HomeScreen;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class MyGame extends Game.Default {

    private final ScreenStack ss = new ScreenStack();
    private Clock.Source clock = new Clock.Source(20);


    public MyGame() {
        super(20); // call update every 33ms (30 times per second)
    }

    @Override
    public void init() {
        //final Screen home = new HomeScreen(ss);
        ss.push(new HomeScreen(ss));

    }

    @Override
    public void update(int delta) {
        ss.update(delta);
    }

    @Override
    public void paint(float alpha) {
        clock.paint(alpha);
        ss.paint(clock);
    }
}
