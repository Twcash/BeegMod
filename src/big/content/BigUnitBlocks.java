package big.content;

import arc.func.Cons;
import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.ctype.UnlockableContent;
import mindustry.world.blocks.payloads.Constructor;

public class BigUnitBlocks {
    public static <T extends UnlockableContent> void overwrite(UnlockableContent target, Cons<T> setter) {
        setter.get((T) target);
    }
    public static void load(){
        overwrite(Blocks.constructor, (Constructor r) -> {{
            r.filter.clear();
            r.filter = Seq.with(BigTurrets.pin, BigTurrets.lob,BigDefense.oxideWall,BigDefense.oxideWallLarge,Blocks.tungstenWall, Blocks.tungstenWallLarge,Blocks.berylliumWall, Blocks.berylliumWallLarge,Blocks.carbideWall, Blocks.carbideWallLarge,Blocks.reinforcedSurgeWall, Blocks.reinforcedSurgeWallLarge, Blocks.reinforcedLiquidContainer, Blocks.reinforcedContainer, Blocks.beamNode);
                }}
        );
    }
}
