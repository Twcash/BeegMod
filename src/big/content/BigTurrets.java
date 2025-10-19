package big.content;

import arc.graphics.Color;
import arc.math.Interp;
import arc.util.Time;
import big.entities.bullets.DeflectBulletType;
import big.entities.bullets.GambleBulletType;
import big.world.meta.blocks.turrets.BigItemTurret;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootSpread;
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
    //Breach
    public static Block pin, drill, puncture;
    //Diffuse
    public static Block lob;
    public static void load(){
        pin = new ItemTurret("pin"){{
            requirements(Category.turret, with(Items.beryllium, 35, Items.silicon, 15));
            Effect shtFx = new MultiEffect(Fx.shootSmallColor, Fx.colorSpark);
            ammo(
                    Items.beryllium, new DeflectBulletType(5.5f, 25){{
                        width = 4f;
                        hitSize = 4f;
                        height = 9f;
                        deflectChance = 0.5f;
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
                    Items.beryllium, new DeflectBulletType(6.5f, 45){{
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
            squareSprite = false;
            ammoPerShot = 2;
            drawer = new DrawTurret("reinforced-");
            shootY = -1;
            outlineColor = Pal.darkOutline;
            size = 2;
            squareSprite = false;
            envEnabled |= Env.space;
            reload = 40f;
            recoil = 1.5f;
            range = 150;
            shootCone = 3f;
            scaledHealth = 90;
            rotateSpeed = 2.5f;
            researchCostMultiplier = 0.15f;
            buildTime = 60f * 2.5f;

            coolant = consume(new ConsumeLiquid(Liquids.water, 8f / 60f));
            limitRange(12f);
        }};
        puncture = new ItemTurret("puncture"){{
            requirements(Category.turret, with(Items.beryllium, 350, Items.silicon, 400, Items.graphite, 300, Items.tungsten, 150));
            Effect shtFx = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
            ammoUseEffect = BigFx.casingWhite1;
            ammo(
                    Items.beryllium, new BasicBulletType(12f, 250, "big-chunky-bullet"){{
                        width = 18f;
                        hitSize = 4f;
                        height = 25f;
                        shootEffect = shtFx;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 1;
                        pierceCap = 4;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Pal.berylShot;
                        frontColor = Color.white;
                        trailWidth = 5f;
                        trailLength = 18;
                        trailInterp = Interp.sineOut;
                        trailEffect = BigFx.punctureTrail;
                        trailInterval = 3;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.5f;
                    }},
                    Items.tungsten, new BasicBulletType(11f, 300, "big-chunky-bullet"){{
                        width = 22f;
                        status = BigStatusEffects.shattered;
                        statusDuration = 15 * Time.toSeconds;
                        height = 30f;
                        hitSize = 9f;
                        shootEffect = shtFx;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 1.5f;
                        reloadMultiplier = 1f;
                        pierceCap = 6;
                        pierce = true;
                        trailEffect = BigFx.punctureTrail;
                        trailInterval = 3;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Pal.tungstenShot;
                        frontColor = Color.white;
                        trailWidth = 2.2f;
                        trailLength = 20;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        rangeChange = 48f;
                        buildingDamageMultiplier = 0.6f;
                    }},
                    Items.surgeAlloy, new DeflectBulletType(7, 150, "circle"){{
                        width = height = 10;
                        hitSize = 9;
                        deflectLifetimeExtend = 50;
                        deflectChance = 0.99f;
                        pierceCap = 10;
                        shrinkY = 0;
                        shrinkX = 0;
                        pierceBuilding = true;
                        homingPower = 0.11f;
                        rangeChange = 50;
                        reloadMultiplier = 0.4f;
                        shootEffect = shtFx;
                        smokeEffect = Fx.shootSmokeSmite;
                        loopSound = Sounds.techloop;
                        loopSoundVolume = 0.05f;
                        trailLength = 14;
                        trailWidth = 5;
                        trailInterp = Interp.pow2In;
                    }},
                    Items.carbide, new BasicBulletType(14f, 400, "aquarion-chunky-bullet"){{
                        width = 26f;
                        height = 35;
                        hitSize = 7f;
                        shootEffect = shtFx;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 3;
                        reloadMultiplier = 0.6f;
                        hitColor = backColor = trailColor = Color.valueOf("ab8ec5");
                        frontColor = Color.white;
                        trailWidth = 2.2f;
                        trailLength = 11;
                        trailEffect = Fx.disperseTrail;
                        trailInterval = 2f;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        rangeChange = 7f*8f;
                        buildingDamageMultiplier = 0.3f;
                        trailRotation = true;

                        fragBullets = 3;
                        fragRandomSpread = 0f;
                        fragSpread = 90f;
                        fragVelocityMin = 1f;

                        fragBullet = new BasicBulletType(8.1f, 200){{
                            lifetime = 14f;
                            width = 15;
                            height = 18f;
                            hitSize = 7f;
                            shootEffect = shtFx;
                            ammoMultiplier = 1;
                            reloadMultiplier = 1f;
                            fragBullets = 2;
                            fragRandomSpread = 0f;
                            fragSpread = 180f;
                            fragVelocityMin = 1f;
                            pierceCap = 3;
                            pierce = true;
                            pierceBuilding = true;
                            hitColor = backColor = trailColor = Color.valueOf("ab8ec5");
                            frontColor = Color.white;
                            trailWidth = 2.2f;
                            trailLength = 11;
                            hitEffect = despawnEffect = Fx.hitBulletColor;
                            buildingDamageMultiplier = 0.2f;
                            fragBullet = new BasicBulletType(8.1f, 100){{
                                lifetime = 8f;
                                width = 8;
                                height = 10;
                                hitSize = 5f;
                                shootEffect = shtFx;
                                ammoMultiplier = 1;
                                reloadMultiplier = 1f;
                                pierceCap = 2;
                                pierce = true;
                                pierceBuilding = true;
                                hitColor = backColor = trailColor = Color.valueOf("ab8ec5");
                                frontColor = Color.white;
                                trailWidth = 1f;
                                trailLength = 6;
                                hitEffect = despawnEffect = Fx.hitBulletColor;
                                buildingDamageMultiplier = 0.2f;
                            }};
                        }};
                    }}
            );
            coolantMultiplier = 10f;
            shootSound = Sounds.shootBig;
            targetUnderBlocks = false;
            ammoPerShot = 5;
            maxAmmo = 20;
            drawer = new DrawTurret("reinforced-"){{
                parts.addAll(new RegionPart("-side"){{
                    mirror = true;
                    progress = PartProgress.warmup.curve(Interp.pow2);
                    moveX = -1;
                    moveY = -1.5f;
                    moveRot = 4;
                    moves.add(new PartMove(PartProgress.recoil, 1.25f, -0.5f, -7));
                }});
            }};
            shootY = -4;
            outlineColor = Pal.darkOutline;
            size = 4;
            squareSprite = false;
            envEnabled |= Env.space;
            reload = 90;
            recoil = 3f;
            range = 200;
            shootCone = 3f;
            scaledHealth = 250;
            rotateSpeed = 1.5f;
            researchCostMultiplier = 0.15f;
            buildTime = 60f * 6f;
            coolant = consume(new ConsumeLiquid(Liquids.water, 40f / 60f));
            limitRange(18f);
        }};

        lob = new BigItemTurret("lob"){{
            requirements(Category.turret, with(Items.beryllium, 25, Items.silicon, 40, Items.graphite, 20));
            ammo(
                    Items.graphite, new GambleBulletType(new float[]{0.5f, 0.5f}, new BasicBulletType(8f, 30){{
                        knockback = 4f;
                        width = 5f;
                        hitSize = 5f;
                        height = 6f;
                        collidesAir = false;
                        shootEffect = Fx.shootSmallColor;
                        smokeEffect = BigFx.shootSmokeSquareSmall;
                        ammoMultiplier = 1;
                        hitColor = backColor = trailColor = Color.valueOf("ea8878");
                        frontColor = Pal.redLight;
                        trailWidth = 1f;
                        lifetime = 15;
                        trailLength = 3;
                        hitEffect = despawnEffect = Fx.hitSquaresColor;
                        buildingDamageMultiplier = 0.2f;
                    }},new BasicBulletType(8f, 30){{
                        knockback = 4f;
                        width = 5f;
                        collidesGround = false;
                        hitSize = 5f;
                        height = 6f;
                        shootEffect = Fx.shootSmallColor;
                        smokeEffect = BigFx.shootSmokeSquareSmall;
                        ammoMultiplier = 1;
                        hitColor = backColor = trailColor = Color.valueOf("ea8878");
                        frontColor = Pal.redLight;
                        trailWidth = 1f;
                        lifetime = 15;
                        trailLength = 3;
                        hitEffect = despawnEffect = Fx.hitSquaresColor;
                        buildingDamageMultiplier = 0.2f;
                    }}),
                    Items.silicon, new GambleBulletType(new float[]{0.5f, 0.5f},
                        new BasicBulletType(8f, 25){{
                            knockback = 3f;
                            width = 6;
                            hitSize = 7f;
                            height = 8;
                            homingPower = 0.08f;
                            shootEffect = Fx.shootSmallColor;
                            smokeEffect = BigFx.shootSmokeSquareSmall;
                            ammoMultiplier = 1;
                            lifetime = 18;
                            hitColor = backColor = trailColor = Pal.graphiteAmmoBack;
                            frontColor = Pal.graphiteAmmoFront;
                            trailWidth = 1.5f;
                            trailLength = 3;
                            hitEffect = despawnEffect = Fx.hitSquaresColor;
                            buildingDamageMultiplier = 0.2f;
                    }},new BasicBulletType(8f, 25){{
                        knockback = 3f;
                        width = 6;
                        hitSize = 7f;
                        height = 8;
                        homingPower = 0.08f;
                        shootEffect = Fx.shootSmallColor;
                        smokeEffect = BigFx.shootSmokeSquareSmall;
                        ammoMultiplier = 1;
                        hitColor = backColor = trailColor = Pal.graphiteAmmoBack;
                        frontColor = Pal.graphiteAmmoFront;
                        trailWidth = 1.5f;
                        trailLength = 3;
                        lifetime = 18;
                        hitEffect = despawnEffect = Fx.hitSquaresColor;
                        buildingDamageMultiplier = 0.2f;
                    }})
            );
            shoot = new ShootSpread(3, 4f);

            coolantMultiplier = 15f;

            inaccuracy = 2f;
            velocityRnd = 0.2f;
            shake = 0.5f;
            ammoPerShot = 2;
            maxAmmo = 30;
            consumeAmmoOnce = true;
            targetUnderBlocks = false;

            drawer = new DrawTurret("reinforced-"){{
                shootSound = Sounds.shootAlt;
                parts.add(new RegionPart("-front"){{
                    progress = PartProgress.warmup;
                    moveRot = -10f;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.recoil, 0f, 0.5f, 3f));
                    heatColor = Color.red;
                }});
            }};
            shootY = 2f;
            outlineColor = Pal.darkOutline;
            size = 1;
            squareSprite = false;
            envEnabled |= Env.space;
            reload = 40f;
            recoil = 1f;
            range = 100;
            shootCone = 35f;
            scaledHealth = 60;
            rotateSpeed = 4f;

            coolant = consume(new ConsumeLiquid(Liquids.water, 4f / 60f));
            limitRange(2f);
        }};
    }
}
