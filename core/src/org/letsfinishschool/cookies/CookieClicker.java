package org.letsfinishschool.cookies;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

// GWT doesn't support String#format
public class CookieClicker extends Game {
    private Stage stage;
    private BitmapFont buttonFont;
    private TextButton upgrade1Button;
    private Label farmerUpgradesLabel;
    private TextButton upgrade2Button;
    private Label clickerUpgradesLabel;
    private Label cookieLabel;
    private float pressedTimes = 0;
    private int clickerUpgrades = 1;
    private int clickerPrice = 10;
    private int farmerUpgrades = 0;
    private int farmerPrice = 10;

    @Override
    public void create() {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin buttonSkin = new Skin(Gdx.files.internal("metal-ui.json"));
        TextureRegion upRegion = new TextureRegion(buttonSkin.getRegion("button"));
        TextureRegion downRegion = new TextureRegion(buttonSkin.getRegion("button-over"));
        buttonFont = buttonSkin.getFont("font");
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.left();

        Label.LabelStyle cookieLabelStyle = new Label.LabelStyle(buttonFont, new Color(0, 0, 0, 1));
        cookieLabelStyle.background = new BaseDrawable();
        cookieLabel = new Label("", cookieLabelStyle);

        table.add(cookieLabel).expandX().fillX().minHeight(Value.percentHeight(0.1f, table)).colspan(2).right();
        table.row();

        Table cookieTable = new Table();
        table.add(cookieTable).left().expand().fill().pad(5);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = buttonFont;
        style.fontColor = new Color(0, 0, 0, 1);

        Sprite cookieSprite = new Sprite(new Texture(Gdx.files.internal("cookie.png")));
        ImageButton cookieButton = new ImageButton(new SpriteDrawable(cookieSprite));
        cookieButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CookieClicker.this.updateButton(1.0f * clickerUpgrades);
            }
        });
        cookieTable.add(cookieButton).expand().fill();

        Table upgradeTable = new Table();
        table.add(upgradeTable).right().top().pad(5);

        Label.LabelStyle labelStyle = new Label.LabelStyle(buttonFont, new Color(1, 0, 0, 1));
        clickerUpgradesLabel = new Label("", labelStyle);
        upgrade1Button = new TextButton("Buy Clicker Upgrade (13 Cookies)", style);
        upgrade1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickerUpgrades++;
                pressedTimes -= clickerPrice;
            }
        });
        upgrade1Button.setDisabled(true);
        upgradeTable.add(upgrade1Button).expand().fill().minHeight(Value.percentHeight(0.1f, table));
        upgradeTable.row();
        upgradeTable.add(clickerUpgradesLabel).expand().fill().minHeight(Value.percentHeight(0.05f, table));
        upgradeTable.row();

        farmerUpgradesLabel = new Label("", labelStyle);
        upgrade2Button = new TextButton("Buy Farmer Upgrade (100 Cookies)", style);
        upgrade2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                farmerUpgrades++;
                pressedTimes -= farmerPrice;
            }
        });
        upgrade2Button.setDisabled(true);
        upgradeTable.add(upgrade2Button).expand().fill().minHeight(Value.percentHeight(0.1f, table));
        upgradeTable.row();
        upgradeTable.add(farmerUpgradesLabel).expand().fill().minHeight(Value.percentHeight(0.05f, table));
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        this.updateButton(Gdx.graphics.getDeltaTime() * farmerUpgrades);
        this.updateUpgrades();
    }

    @Override
    public void dispose() {
        stage.dispose();
        buttonFont.dispose();
    }

    private void updateButton(float margin) {
        pressedTimes += margin;
    }

    private void updateUpgrades() {
        clickerPrice = (int) Math.max(Math.pow(clickerUpgrades + 1, 2) + 9, 0);
        farmerPrice = (int) Math.max(Math.pow(farmerUpgrades + 1, 3) + 99, 0);
        upgrade1Button.setText("Buy Clicker Upgrade (" + clickerPrice + " Cookies)");
        upgrade2Button.setText("Buy Farmer Upgrade (" + farmerPrice + " Cookies)");
        upgrade1Button.setDisabled(!(pressedTimes >= clickerPrice));
        upgrade2Button.setDisabled(!(pressedTimes >= farmerPrice));
        clickerUpgradesLabel.setText("Earning " + clickerUpgrades + "/click");
        farmerUpgradesLabel.setText("Earning " + farmerUpgrades + "/second");
        cookieLabel.setText("You have earned " + (int) Math.floor(pressedTimes) + " Cookie(s)");
    }
}
