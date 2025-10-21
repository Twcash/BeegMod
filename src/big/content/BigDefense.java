package big.content;

import big.entities.bullets.AOEBulletType;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.EmptyBulletType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;

public class BigDefense {
    public static Block berylliumWallHuge, berylliumWallMassive,
    oxideWall, oxideWallLarge, oxideWallHuge;

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
        oxideWall = new Wall("oxide-wall"){{
            requirements(Category.defense,ItemStack.with(Items.oxide, 6));
            size = 1;
            buildCostMultiplier = 4;
            health = 100 * wallHealthMultiplier;
            destroyBulletSameTeam = true;
            destroyBullet = new EmptyBulletType(){{
                instantDisappear = true;
                fragBullets = 2;
                fragBullet = new AOEBulletType(100, 240, 4, Items.oxide.color){{
                    speed = 8;
                    drag = 0.16f;
                    createChance = 0.8f;
                    status = StatusEffects.corroded;
                    statusDuration = 45;
                }};
            }};
        }};
        oxideWallLarge = new Wall("oxide-wall-large"){{
            requirements(Category.defense, ItemStack.mult(oxideWall.requirements, 4));
            size = 2;
            buildCostMultiplier = 4;
            health = 100 * wallHealthMultiplier;
            destroyBulletSameTeam = true;
            destroyBullet = new EmptyBulletType(){{
                instantDisappear = true;
                fragBullets = 6;
                fragBullet = new AOEBulletType(160, 240, 6, Items.oxide.color){{
                    speed = 11;
                    drag = 0.24f;
                    createChance = 0.8f;
                    status = StatusEffects.corroded;
                    statusDuration = 45;
                }};
            }};
        }};
        oxideWallHuge = new Wall("oxide-wall-huge"){{
            requirements(Category.defense, ItemStack.mult(oxideWall.requirements, 9));
            size = 3;
            buildCostMultiplier = 4;
            destroyBulletSameTeam = true;
            destroyBullet = new EmptyBulletType(){{
                instantDisappear = true;
                fragBullets = 15;
                fragBullet = new AOEBulletType(240, 240, 8, Items.oxide.color){{
                    speed = 12;
                    drag = 0.28f;
                    createChance = 0.8f;
                    status = StatusEffects.corroded;
                    statusDuration = 45;
                }};
            }};
            health = 100 * wallHealthMultiplier;
        }};
    }
}
