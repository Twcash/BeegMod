package big.ui.fragments;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.scene.Action;
import arc.scene.Group;
import arc.scene.actions.Actions;
import arc.scene.event.Touchable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Image;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.WidgetGroup;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.*;
import big.content.BigFx;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.content.Fx;
import static mindustry.Vars.ui;



//Ultrakill styled score board UI.
//TODO BIG code cleanup. Make the thing work off an actual update loop...
//TODO Make points "streak based" and reset after a time with little point improvement.
//TODO Actual visuals rather than a blank grey box with some words.
public class ScoreBoardFragment extends Table {

    private final Table layout;
    private final Label totalLabel;
    private final Image titleImage;
    private final Image background;
    private final Image scoreBar;
    private final Seq<TextureRegionDrawable> titleTiers = new Seq<>();
    private final WidgetGroup popupLayer;
    private final Table rowTable;
    private TextureRegion one= new TextureRegion();
    private TextureRegion two= new TextureRegion();
    private TextureRegion three= new TextureRegion();
    private TextureRegion four= new TextureRegion();
    private TextureRegion five = new TextureRegion();
    private float rush = 0f;
    private int totalScore = 0;
    private int tier = 0;

    private final float width = 250f;
    private final float height = 180f;
    private final float margin = 10f;
    public void loadTiers(){
        titleTiers.clear();
        //TODO This needs a big cleanup
        one = Core.atlas.find("big-titlecard-lame");
        two = Core.atlas.find("big-titlecard-ok");
        three = Core.atlas.find("big-titlecard-interesting");
        four = Core.atlas.find("big-titlecard-amazing");
        five = Core.atlas.find("big-titlecard-big");
        titleTiers.addAll(new TextureRegionDrawable(one),new TextureRegionDrawable(two),new TextureRegionDrawable(three),new TextureRegionDrawable(four),new TextureRegionDrawable(five));
    }

    public ScoreBoardFragment() {
        loadTiers();

        setFillParent(true);
        visible(() -> ui.hudfrag.shown);
        touchable = Touchable.disabled;

        popupLayer = new WidgetGroup();
        addChild(popupLayer);

        // background
        background = new Image(Core.atlas.find("white"));
        background.setColor(Color.valueOf("222222cc"));
        background.setScaling(Scaling.stretch);
        background.setSize(width, height);
        background.x = Core.graphics.getWidth() - width - margin;
        background.y = Core.graphics.getHeight() - height - margin;
        addChild(background);

        // layout table
        layout = new Table();
        layout.setSize(width, height);
        layout.x = background.x;
        layout.y = background.y;
        addChild(layout);

        titleImage = new Image(titleTiers.get(0));
        layout.add(titleImage).size(width - 30f, 60f).padBottom(8f).row();

        // score bar
        scoreBar = new Image(Core.atlas.find("white"));
        scoreBar.setColor(Pal.accent);
        scoreBar.setOrigin(Align.left);
        scoreBar.setScale(0f);
        layout.add(scoreBar).width(width - 30f).height(12f).padBottom(10f).row();

        // total label
        totalLabel = new Label("0", Styles.outlineLabel);
        totalLabel.setFontScale(1.2f);
        layout.add(totalLabel).padBottom(8f).row();

        // stacked row table
        rowTable = new Table();
        rowTable.top().left();
        rowTable.x = layout.x + 5f;
        rowTable.y = layout.y - 25f;
        addChild(rowTable);

    }

    public void addScore(String reason, int points) {
        totalScore += points;
        totalLabel.setText("" + totalScore);

        float baseGain = 0.15f;
        float power = 2f;
        rush += baseGain * Mathf.pow(1f - rush/2f, power);
        rush = Mathf.clamp(rush, 0f, 1.1f);

        updateRushVisuals();

        ScoreRow row = new ScoreRow(reason, points);
        rowTable.add(row.label).row();

        row.label.actions(
                Actions.sequence(
                        Actions.delay(1f),
                        Actions.run(() -> spawnLetters(String.valueOf(row.label.getText()))),
                        Actions.remove()
                )
        );
    }
    private void updateRushVisuals() {
        float[] rushThresholds = {0.01f, 0.1f, 0.3f, 0.7f, 0.8f};
        int newTier = 0;
        for (int i = 0; i < rushThresholds.length; i++) {
            if (rush >= rushThresholds[i]) newTier = i;
        }

        newTier = Mathf.clamp(newTier, 0, titleTiers.size - 1);
        if (newTier != tier) {
            tier = newTier;
            titleImage.setDrawable(titleTiers.get(tier));
            titleImage.clearActions();
            titleImage.actions(
                    Actions.sequence(
                            Actions.rotateTo(12, 10),
                            Actions.scaleTo(1.6f, 1.6f, 0.15f, Interp.pow3Out),
                            Actions.scaleTo(1f, 1f, 0.3f, Interp.pow3In)
                    )
            );
        }

        // score bar reflects rush
        scoreBar.clearActions();
        scoreBar.actions(Actions.scaleTo(rush, 1f, 0.25f, Interp.sineOut));
    }
    //Spawn individual letters from a word then fly off.
    private void spawnLetters(String text) {
        float startX = layout.x + 5f;
        float startY = layout.y - 25f;

        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            Label letter = new Label(String.valueOf(c), new Label.LabelStyle(Fonts.outline, Pal.accent));

            float scale = 0.7f + rush * 0.6f;
            letter.setFontScale(scale);
            letter.x = startX + i * 14f;
            letter.y = startY + Mathf.range(5f);

            popupLayer.addChild(letter);

            float duration = 1.5f + Mathf.random(0.3f);
            float intensity = 1f + rush * 2f;
            float targetX = Core.graphics.getWidth() + 50f + Mathf.random(30f);

            letter.actions(
                    Actions.sequence(
                            Actions.parallel(
                                    Actions.moveTo(targetX, letter.y + Mathf.range(10f), duration, Interp.pow3Out),
                                    Actions.fadeOut(duration),
                                    Actions.rotateBy(Mathf.range(20f * intensity), duration),
                                    Actions.scaleTo(scale + 0.2f * intensity, scale + 0.2f * intensity, duration / 2f)
                            ),
                            Actions.run(() -> {
                                // simple spark VFX
                                Fx.blockCrash.at(letter.x, letter.y, 0, Pal.accent);
                                letter.remove();
                            })
                    )
            );
        }
    }
    //VERY VERY BAD
    //TODO Remove, move to update loop
    @Override
    public void act(float delta) {
        super.act(delta);

        float decayRate = 0.01f;
        rush = Mathf.lerpDelta(rush, 0f, decayRate);

        Color c = Color.valueOf("222222cc").cpy();
        c.shiftHue(rush * 50f);
        background.setColor(c);
        background.setScale(1f + rush * 0.08f);
    }
    //TODO. Impact visuals for bigger scores (possibly certain events have special fonts or colors?)
    private static class ScoreRow {
        Label label;
        public ScoreRow(String reason, int points) {
            label = new Label(reason + " +" + points, new Label.LabelStyle(Fonts.outline, Pal.accent));
            label.setFontScale(0.8f);
        }
    }
}
