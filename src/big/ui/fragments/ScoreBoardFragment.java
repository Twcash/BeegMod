package big.ui.fragments;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.scene.event.Touchable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Image;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

//ULTRAKILL
public class ScoreBoardFragment extends Table {
    private final Table layout;
    private final Table wordLayout;
    private final Label totalLabel;
    private final Image titleImage;
    private final Image background;
    private final Image scoreBar;

    private final Seq<TextureRegionDrawable> titleTiers = new Seq<>();    private final Seq<ReasonStack> reasonStacks = new Seq<>();


    private final float width = 500f, height = 300f, margin = 0f;

    private float rush = 0f;private int totalScore = 0;

    private int tier = 0;

    private final float wordLifetime = 5 * 60f;
    private float titleAnimTime = 0f;
    public ScoreBoardFragment(){
        loadTiers();

        setFillParent(true);
        visible(() -> Vars.ui.hudfrag.shown);
        touchable = Touchable.disabled;
        // Background
        background = new Image(Core.atlas.find("white"));
        background.setColor(Color.valueOf("222222cc"));
        background.setScaling(Scaling.stretch);
        background.setSize(width, height);
        background.setPosition(Core.graphics.getWidth() - width - margin,
                Core.graphics.getHeight() - height - margin);
        addChild(background);

        // Main layout
        layout = new Table();
        layout.setSize(width, height);
        layout.setPosition(background.x, background.y + height - 300f);
        addChild(layout);

        titleImage = new Image(titleTiers.get(0));
        layout.add(titleImage).size(width - 110f, 60f).padBottom(8f).row();

        scoreBar = new Image(Core.atlas.find("white"));
        scoreBar.setColor(Pal.accent);
        scoreBar.setOrigin(Align.left);
        scoreBar.setScale(0f, 1);
        layout.add(scoreBar).width(width - 30f).height(12f).padBottom(10f).row();

        totalLabel = new Label("0", Styles.outlineLabel);
        totalLabel.setFontScale(1.2f);
        layout.add(totalLabel).padBottom(8f).row();

        wordLayout = new Table();
        wordLayout.setSize(width, height - 80f);
        wordLayout.setPosition(background.x - 10f, background.y - 30f);
        addChild(wordLayout);

        Events.run(EventType.Trigger.update, this::update);
    }

    private void loadTiers() {
        titleTiers.clear();
        titleTiers.addAll(
                new TextureRegionDrawable(Core.atlas.find("big-titlecard-lame")),
                new TextureRegionDrawable(Core.atlas.find("big-titlecard-ok")),
                new TextureRegionDrawable(Core.atlas.find("big-titlecard-interesting")),
                new TextureRegionDrawable(Core.atlas.find("big-titlecard-amazing")),
                new TextureRegionDrawable(Core.atlas.find("big-titlecard-big"))
        );
    }

    public void addScore(String reason, int points) {
        totalScore += points;
        totalLabel.setText(String.valueOf(totalScore));

        float gain = 0.0002f * points;
        rush += gain;
        rush = Mathf.clamp(rush, 0f, 1f);

        mergeReason(reason, points);
    }

    private void mergeReason(String reason, int points) {
        reason = reason.trim();
        ReasonStack stack = null;
        for (ReasonStack rs : reasonStacks) {
            if (rs.reason.equalsIgnoreCase(reason)) {
                stack = rs;
                break;
            }
        }

        if (stack == null) {
            ReasonStack rs = new ReasonStack(reason, points, 1);
            reasonStacks.add(rs);
            wordLayout.add(rs.label).padBottom(4f).row();
        } else {
            stack.merge(points);
        }
    }

    private Color getReasonColor(String reason) {
        reason = reason.toLowerCase();
        if (reason.contains("kill")) return Color.scarlet.cpy();
        if (reason.contains("built")) return Pal.accent.cpy();
        if (reason.contains("destroyed")) return Color.scarlet.cpy();
        return Pal.accent.cpy();
    }

    private void update() {
        if (!Vars.state.isPaused()) {
            rush -= Time.delta * (0.002f + rush * 0.006f);
            rush = Mathf.clamp(rush, 0f, 1f);

            if (rush <= 0f && !reasonStacks.isEmpty()) comboLost();

            // Update title tier
            updateTitle();

            // Update reason words
            for (int i = 0; i < reasonStacks.size; i++) {
                ReasonStack rs = reasonStacks.get(i);
                rs.update();
                if (rs.isExpired()) {
                    wordLayout.removeChild(rs.label, true);
                    reasonStacks.remove(i--);
                }
            }
            //TODO These don't do much.
            scoreBar.setScale(rush, 1);
            background.setScale(1f + rush * 0.08f);
            background.setColor(Color.valueOf("222222cc").cpy().shiftHue(rush * 60f));
        }
    }

    private void updateTitle() {
        float[] thresholds = {0f, 0.25f, 0.45f, 0.7f, 0.9f};
        int newTier = 0;
        for (int i = 0; i < thresholds.length; i++) {
            if (rush >= thresholds[i]) newTier = i;
        }
        newTier = Mathf.clamp(newTier, 0, titleTiers.size - 1);
        if (newTier != tier) {
            tier = newTier;
            titleAnimTime = 0f;
            titleImage.setDrawable(titleTiers.get(tier));
        }
    }
    //TODO Add visuals to losing combos.
    private void comboLost() {
        rush = 0f;
        totalScore = 0;
        totalLabel.setText("0");

        for (ReasonStack rs : reasonStacks) {
            wordLayout.removeChild(rs.label, true);
        }
        reasonStacks.clear();
    }

    private class ReasonStack {
        String reason;
        int totalPoints, count;
        Label label;
        float life;

        ReasonStack(String reason, int points, int count) {
            this.reason = reason;
            this.totalPoints = points;
            this.count = count;
            label = new Label(reason + " +" + points, Styles.outlineLabel);
            label.setColor(getReasonColor(reason));
            label.setFontScale(1f);
            life = wordLifetime;
        }

        void merge(int points) {
            totalPoints += points;
            count++;
            label.setText(reason + " +" + totalPoints + " x" + count);
            life = wordLifetime;
        }

        void update() {
            if(!Vars.state.isPaused()) life -= Time.delta;

        }
        //Funny
        boolean isExpired() {
            return life <= 0f;
        }
    }
}

