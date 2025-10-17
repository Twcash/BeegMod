package big.content;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.type.StatusEffect;

public class BigStatusEffects {
    public static StatusEffect shattered;
    public static void load(){
        shattered = new StatusEffect("shattered"){{
            effect = Fx.none;
            transitionDamage = 20f;
            speedMultiplier = 0.6f;
            outline = false;
            healthMultiplier = 0.8f;
            init(() -> {
                affinity(StatusEffects.blasted, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    Fx.blastsmoke.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(StatusEffects.blasted, Math.min(time + result.time, 150f));
                });
            });
        }};

    }
}
