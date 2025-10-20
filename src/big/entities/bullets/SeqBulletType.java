package big.entities.bullets;

import arc.util.Nullable;
import big.world.meta.blocks.turrets.BigItemTurret;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.units.WeaponMount;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.gen.Teamc;
import mindustry.world.blocks.defense.turrets.ItemTurret;

//TODO somehow this is screwing with targetting?????
public class SeqBulletType extends BulletType {
    public BulletType[] bullets;

    public SeqBulletType(BulletType... bullets) {
        this.bullets = bullets;
    }

    @Override
    protected float calculateRange() {
        float max = 0f;
        for (BulletType b : bullets) {
            try {
                java.lang.reflect.Method method = BulletType.class.getDeclaredMethod("calculateRange");
                method.setAccessible(true);
                float range = (float) method.invoke(b);
                max = Math.max(max, range);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return max;
    }

    public BulletType pickBullet(Entityc owner) {
        if (owner instanceof ItemTurret.ItemTurretBuild tur) {
            if (tur.block instanceof BigItemTurret big)
                return bullets[(tur.totalShots / big.shoot.shots) % bullets.length];
        } else if (owner instanceof WeaponMount u) {
            return bullets[(u.totalShots / u.weapon.shoot.shots) % bullets.length];
        }
        throw new IllegalArgumentException("something very bad happened");
    }

    @Override
    public @Nullable Bullet create(@Nullable Entityc owner, @Nullable Entityc shooter, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover, float aimX, float aimY, @Nullable Teamc target) {
        angle += angleOffset;
        Bullet last = null;
        BulletType chosen = pickBullet(owner);
        speed = chosen.speed;//Surely there will be no repercussions for this.
        last = chosen.create(owner, shooter, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, aimX, aimY, target);
        return last;
    }
}