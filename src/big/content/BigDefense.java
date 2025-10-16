package big.content;

import mindustry.content.Blocks;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;

public class BigDefense {
    public static Block berylliumWallHuge, berylliumWallMassive;

    public static void load(){
        int wallHealthMultiplier = 4;

        berylliumWallHuge = new Wall("beryllium-wall-huge"){{
            requirements(Category.defense, ItemStack.mult(Blocks.berylliumWall.requirements, 9));
            health = 130 * wallHealthMultiplier * 9;
            armor = 2f;
            buildCostMultiplier = 5f;
            size = 3;
        }};
        berylliumWallMassive = new Wall("beryllium-wall-massive"){{
            requirements(Category.defense, ItemStack.mult(Blocks.berylliumWall.requirements, 16));
            health = 130 * wallHealthMultiplier * 16;
            armor = 2f;
            buildCostMultiplier = 5f;
            size = 4;
        }};
    }
}
