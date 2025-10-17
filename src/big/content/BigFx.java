package big.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.units.UnitAssembler.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class BigFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Effect
            punctureTrail = new Effect(15, e -> {
        color(Color.white, e.color, e.fin());
        stroke(0.7f + e.fout() * 1.8f);
        rand.setSeed(e.id);

        for (int i = 0; i < 3; i++) {
            float rot = e.rotation + rand.range(30f) + 180f;
            v.trns(rot, rand.random(e.fin() * 35f));
            lineAngle(e.x + v.x, e.y + v.y, rot, e.fout() * rand.random(2f, 9f) + 1.5f);
        }
    }),
            casingWhite1 = new Effect(30f, e -> {
                color(Color.white, Pal.lightOrange, Pal.lightishGray, e.fin());
                alpha(e.fout(0.2f));
                float rot = Math.abs(e.rotation) + 90f;
                int i = -Mathf.sign(e.rotation);

                float len = (2.5f + e.finpow() * 7f) * i;
                float lr = rot + e.fin() * 30f * i;
                Fill.rect(
                        e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 5f * e.fin()),
                        e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 5f * e.fin()),
                        2f, 4f, rot + e.fin() * 30f * i
                );

            }).layer(Layer.bullet),
            shootSmokeSquareSmall = new Effect(20f, e -> {
                color(Color.white, e.color, e.fin());

                rand.setSeed(e.id);
                for (int i = 0; i < 4; i++) {
                    float rot = e.rotation + rand.range(10f);
                    v.trns(rot, rand.random(e.finpow() * 15f));
                    Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 1.5f + 0.2f, rand.random(360f));
                }
            });
}
