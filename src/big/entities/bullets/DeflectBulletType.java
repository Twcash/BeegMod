package big.entities.bullets;

import arc.audio.Sound;
import arc.math.Mathf;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
public class DeflectBulletType extends BasicBulletType {
    //Chance for this bullet to deflect off an enemy. -1 to disable
    public float deflectChance = -1;
    //How much lifetime is added to the bullet after deflection
    public float deflectLifetimeExtend = 0;
    //Sound made when deflected.
    public Sound deflectSound = Sounds.none;


    public DeflectBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.sprite = bulletSprite;
    }

    public DeflectBulletType(float speed, float damage){
        this(speed, damage, "bullet");
    }

    public DeflectBulletType(){
        super(1f, 1f, "bullet");
    }

    @Override
    public void init(){
        super.init();
        if(deflectChance > 0){
            despawnHit = false;
        }
    }
    @Override
    public void hit(Bullet b, float x, float y){
        super.hit(b, x, y);
        if(deflectChance > 0f && Mathf.chance(deflectChance)){

            deflectSound.at(x, y, Mathf.random(0.9f, 1.1f));

            b.trns(-b.vel.x, -b.vel.y);
            b.lifetime += deflectLifetimeExtend;
            float penX = Math.abs(x - b.x), penY = Math.abs(y - b.y);

            if(penX > penY){
                b.vel.x *= -1;
            }else{
                b.vel.y *= -1;
            }
            b.time += 1f;
        }
    }
}
