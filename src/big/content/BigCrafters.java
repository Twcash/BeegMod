package big.content;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawArcSmelt;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import mindustry.world.meta.Env;

import static mindustry.type.ItemStack.with;

public class BigCrafters {
    public static Block tinySiliconArcFurnace;
    public static void load(){
        //Blocks smaller than the original will blatantly be worse versions of the original
        //More fun stuff can happen when they get bigger than the original (Payload crafting?)
        tinySiliconArcFurnace = new GenericCrafter("tiny-silicon-arc-furnace"){{
            requirements(Category.crafting, with(Items.beryllium, 15, Items.graphite, 10));
            size = 1;
            squareSprite = false;
            craftEffect = Fx.mineSmall;
            craftTime = 120;
            hasPower = true;
            hasLiquids = false;
            envEnabled |= Env.space | Env.underwater;
            envDisabled = Env.none;
            itemCapacity = 30;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawArcSmelt(){{
                circleStroke = 0.75f;
                particleLen = 1.5f;
                circleSpace = 1;
                flameRad = 0.5f;
                flameRadiusMag = 0.15f;
                particleRad = 3;
            }}, new DrawDefault());
            fogRadius = 2;
            researchCost = with(Items.beryllium, 5, Items.graphite, 5);
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;
            consumeItems(with(Items.sand, 3));
            outputItem = new ItemStack(Items.silicon, 2);
            consumePower(1.5f);
        }};
    }
}
