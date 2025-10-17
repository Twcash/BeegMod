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
    shootSmokeSquareSmall = new Effect(20f, e -> {
        color(Color.white, e.color, e.fin());

        rand.setSeed(e.id);
        for(int i = 0; i < 4; i++){
            float rot = e.rotation + rand.range(10f);
            v.trns(rot, rand.random(e.finpow() * 15f));
            Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 1.5f + 0.2f, rand.random(360f));
        }
    });
}
