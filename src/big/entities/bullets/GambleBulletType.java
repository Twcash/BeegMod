package big.entities.bullets;

import arc.math.Mathf;
import mindustry.entities.bullet.BasicBulletType;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.BulletType;
import mindustry.game.*;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.gen.Teamc;

import java.util.Arrays;
//LETS GO GAMBLING
public class GambleBulletType extends BasicBulletType {
    public BulletType[] bullets = {};
    public float[] bias = {};

    public GambleBulletType(BulletType... bullets){
        this.bullets = bullets;
        this.bias = new float[bullets.length];
        Arrays.fill(this.bias, 1f);
    }

    public GambleBulletType(float[] bias, BulletType... bullets){
        if(bias.length != bullets.length) throw new IllegalArgumentException("Bias length must match bullets length.");
        this.bullets = bullets;
        this.bias = bias;
    }


    public GambleBulletType(){
    }
    //This doesn't work. using pickBullet() won't return the same bullet either.
    @Override
    public float estimateDPS(){
        float sum = 0f;
        for(var b : bullets){
            sum += b.estimateDPS();
        }
        return sum / Math.max(bullets.length, 1);
    }
    //LimitRange() does not work with this class. Driving me insane. Tweak lifetime instead...
    @Override
    protected float calculateRange(){
        float max = 0f;
        for(var b : bullets){
            max = Math.max(max, b.range);
        }
        return max;
    }
    private BulletType pickBullet(){
        if(bullets.length == 0) return this; // fallback

        float total = 0f;
        for(float f : bias) total += f;
        float rand = Mathf.random(total);

        float cumulative = 0f;
        for(int i = 0; i < bullets.length; i++){
            cumulative += bias[i];
            if(rand <= cumulative){
                return bullets[i];
            }
        }
        return bullets[bullets.length - 1];//Should not ever happen.
    }

    @Override
    public @Nullable Bullet create(
            @Nullable Entityc owner, @Nullable Entityc shooter, Team team, float x, float y, float angle, float damage, float velocityScl,
            float lifetimeScl, Object data, @Nullable Mover mover, float aimX, float aimY, @Nullable Teamc target
    ){
        angle += angleOffset;

        Bullet last = null;

            BulletType chosen = pickBullet();
            last = chosen.create(owner, shooter, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, aimX, aimY, target);

        return last;
    }
}

