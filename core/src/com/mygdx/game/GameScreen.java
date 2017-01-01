package com.mygdx.game;

/**
 * Created by luissancar on 1/1/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.entities.FloorEntiy;
import com.mygdx.game.entities.PlayerEntity;
import com.mygdx.game.entities.SkipeEntity;

import java.util.ArrayList;

/**
 * Created by luissancar on 30/12/16.
 */

public class GameScreen extends BaseScreen{
    private Stage stage;
    private World world;
    private PlayerEntity player;
    private java.util.List<FloorEntiy> floorlist= new ArrayList<FloorEntiy>();

    private java.util.List<SkipeEntity> spikelist=new ArrayList<SkipeEntity>();

    public GameScreen(MyGdxGame game) {
        super(game);
        stage =new Stage(new FitViewport(640,360));
        world=new World(new Vector2(0,-10), true);

        world.setContactListener(new ContactListener() {
            private boolean areCollided(Contact contact, Object userA, Object userB){
                return   (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
                        (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));

            }
            @Override
            public void beginContact(Contact contact) {
                if (areCollided(contact,"player","floor")){
                    player.setJumping(false);
                }
                if (Gdx.input.isTouched()){
                    player.setMustJump(true);
                }

                if (areCollided(contact,"player","spike")) {
                    player.setAlive(false);
                }

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    @Override
    public void show() {
        Texture playerTexture=game.getManager().get("player.png");
        Texture overfloorTexture=game.getManager().get("overfloor1.png");
        Texture floorTexture=game.getManager().get("floor.png");
        Texture spikeTexture=game.getManager().get("spike.png");
        player=new PlayerEntity(world,playerTexture,new Vector2(1.5f,1.5f));
        floorlist.add(new FloorEntiy(world,floorTexture,overfloorTexture,0,1000,1));
        floorlist.add(new FloorEntiy(world,floorTexture,overfloorTexture,12,10,2));  //añadimos escalones
        floorlist.add(new FloorEntiy(world,floorTexture,overfloorTexture,30,10,2));

        spikelist.add(new SkipeEntity(world,spikeTexture,6,1));
        spikelist.add(new SkipeEntity(world,spikeTexture,18,2));
        spikelist.add(new SkipeEntity(world,spikeTexture,35,2));
        spikelist.add(new SkipeEntity(world,spikeTexture,50,1));


        stage.addActor(player);
        for (FloorEntiy floor: floorlist){
            stage.addActor(floor);
        }
        for (SkipeEntity spike: spikelist){
            stage.addActor(spike);
        }
    }

    @Override
    public void hide() {
        player.detach();
        player.remove();

        for (FloorEntiy floor: floorlist){
            floor.detach();
            floor.remove();
        }
        for (SkipeEntity spike: spikelist){
            spike.detach();
            spike.remove();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player.getX() > 150 && player.isAlive()){ //si se ha movido
            /// transladamos la cámara  a la direccion del player
            stage.getCamera().translate(Constans.SPEED_PLAYER * delta * Constans.PIXELS_IN_METER, 0, 0);
    }
        stage.act();
        world.step(delta,6,2);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
