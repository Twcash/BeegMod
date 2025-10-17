package big;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.TextureRegion;
import big.content.BigCrafters;
import big.content.BigDefense;
import big.content.BigTurrets;
import big.ui.fragments.ScoreBoardFragment;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Player;
import mindustry.mod.*;
import mindustry.type.ItemStack;

import static mindustry.Vars.player;
import static mindustry.Vars.ui;

public class BigMod extends Mod {
    public BigMod() {
    }

    @Override
    public void loadContent() {
        BigCrafters.load();
        BigTurrets.load();
        BigDefense.load();
    }

    @Override
    public void init() {
            ScoreBoardFragment scoreboard = new ScoreBoardFragment();
            scoreboard.loadTiers();

        Events.on(EventType.WorldLoadEvent.class, g -> {ui.hudGroup.addChild(scoreboard);});
            Events.on(EventType.UnitDestroyEvent.class, g -> {
                if(g.unit != null && g.unit.team != Vars.player.team()){
                    int value = (int)(g.unit.type().armor + g.unit.type.health);
                    scoreboard.addScore("Unit Killed ---", value);
                }
            });

            Events.on(EventType.BlockBuildEndEvent.class, b -> {
                if(b.unit != null && b.unit.isPlayer()){
                    if(!b.breaking){
                        float totalScore = 0f;
                        for(ItemStack c : b.tile.block().requirements){
                            totalScore += c.amount * c.item.cost;
                        }
                        scoreboard.addScore("Block Built ---", (int)totalScore);
                    }
                }
            });

            Events.on(EventType.BlockDestroyEvent.class, f -> {
                if(f.tile != null && f.tile.team() != Vars.player.team()){
                    float destroyScore = f.tile.block().health;
                    scoreboard.addScore("Block Destroyed ---", (int)destroyScore);
                }
            });
    }
}
