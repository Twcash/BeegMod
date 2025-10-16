package big.content;

import arc.graphics.Color;
import big.entities.bullets.StandardBulletType;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Env;

import static mindustry.type.ItemStack.with;

public class BigTurrets {
    public static Block pin, drill;

    public static void load(){
        pin = new ItemTurret("pin"){{
            requirements(Category.turret, with(Items.beryllium, 35, Items.silicon, 15));
            Effect shtFx = new MultiEffect(Fx.shootSmallColor, Fx.colorSpark);
            ammo(
                    Items.beryllium, new StandardBulletType(5.5f, 25){{
                        width = 4f;
                        hitSize = 4f;
                        height = 9f;
                        deflectChance = 0.15f;
                        deflectLifetimeExtend = 10;
                        shootEffect = shtFx;
                        smokeEffect = Fx.shootSmallSmoke;
                        ammoMultiplier = 2;
                        pierceCap = 2;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Pal.berylShot;
                        frontColor = Color.white;
                        trailWidth = 1f;
                        trailLength = 5;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.3f;
                    }}
            );
            coolantMultiplier = 15f;
            shootSound = Sounds.shootSnap;
            targetUnderBlocks = false;
            ammoPerShot = 1;
            drawer = new DrawTurret("reinforced-");
            shootY = -1;
            outlineColor = Pal.darkOutline;
            size = 1;
            squareSprite = false;
            envEnabled |= Env.space;
            reload = 30f;
            recoil = 1f;
            range = 120;
            shootCone = 3f;
            scaledHealth = 60;
            rotateSpeed = 2.5f;
            researchCostMultiplier = 0.15f;
            buildTime = 60f * 2f;

            coolant = consume(new ConsumeLiquid(Liquids.water, 4f / 60f));
            limitRange(12f);
        }};
        drill = new ItemTurret("drill"){{
            requirements(Category.turret, with(Items.beryllium, 70, Items.silicon, 60, Items.graphite, 25));
            Effect shtFx = new MultiEffect(Fx.shootSmallColor, Fx.colorSpark);
            ammo(
                    Items.beryllium, new StandardBulletType(6.5f, 45){{
                        width = 6f;
                        hitSize = 4f;
                        height = 10f;
                        deflectChance = 0.05f;
                        deflectLifetimeExtend = 10;
                        shootEffect = shtFx;
                        smokeEffect = Fx.shootSmallSmoke;
                        ammoMultiplier = 1;
                        pierceCap = 2;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Pal.berylShot;
                        frontColor = Color.white;
                        trailWidth = 1.5f;
                        trailLength = 7;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.3f;
                    }}
            );
            coolantMultiplier = 15f;
            shootSound = Sounds.shootSnap;
            targetUnderBlocks = false;
            ammoPerShot = 2;
            drawer = new DrawTurret("reinforced-");
            shootY = -1;
            outlineColor = Pal.darkOutline;
            size = 2;
            squareSprite = false;
            envEnabled |= Env.space;
            reload = 30f;
            recoil = 1f;
            range = 120;
            shootCone = 3f;
            scaledHealth = 60;
            rotateSpeed = 2.5f;
            researchCostMultiplier = 0.15f;
            buildTime = 60f * 2.5f;

            coolant = consume(new ConsumeLiquid(Liquids.water, 8f / 60f));
            limitRange(12f);
        }};
    }
}
