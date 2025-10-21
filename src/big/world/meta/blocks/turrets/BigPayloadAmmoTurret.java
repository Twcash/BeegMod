package big.world.meta.blocks.turrets;
import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.geom.Vec2;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.Log;
import arc.util.Time;
import arc.util.io.*;
import big.world.meta.BigStatValues;
import mindustry.Vars;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.payloads.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class BigPayloadAmmoTurret extends Turret {
    public ObjectMap<UnlockableContent, BulletType> ammoTypes = new ObjectMap<>();

    protected UnlockableContent[] ammoKeys;

    public BigPayloadAmmoTurret(String name) {
        super(name);

        maxAmmo = 3;
        acceptsPayload = true;
    }

    public void ammo(Object... objects) {
        ammoTypes = ObjectMap.of(objects);
    }

    public void limitRange() {
        limitRange(1f);
    }

    public void limitRange(float margin) {
        ObjectMap<UnlockableContent, BulletType> newMap = new ObjectMap<>();
        for (var entry : ammoTypes.entries()) {
            BulletType copy = entry.value.copy();
            copy.lifetime = (range + margin) / copy.speed;
            newMap.put(entry.key, copy);
        }
        ammoTypes = newMap;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.input);
        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, BigStatValues.ammo(ammoTypes));
    }

    @Override
    public void init() {
        consume(new ConsumePayloadFilter(i -> ammoTypes.containsKey(i)) {
            @Override
            public float efficiency(Building build) {
                return build instanceof PayloadTurretBuild it && it.seq.any() ? 1f : 0f;
            }
        });

        ammoKeys = ammoTypes.keys().toSeq().toArray(UnlockableContent.class);

        super.init();
    }

    public class PayloadTurretBuild extends TurretBuild {
        public PayloadSeq seq = new PayloadSeq();


        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            return payload != null
                    && seq.total() < maxAmmo
                    && ammoTypes.containsKey(payload.content());
        }

        @Override
        public void handlePayload(Building source, Payload payload) {
            if (payload != null && payload.content() != null) {
                seq.add(payload.content());
                payload.remove();
            }
        }
        @Override
        public boolean hasAmmo() {
            return seq.total() > 0;
        }
        @Override
        public BulletType useAmmo() {
            for (var content : ammoKeys) {
                if (seq.contains(content)) {
                    seq.remove(content);
                    return ammoTypes.get(content);
                }
            }
            return null;
        }
        @Override
        public BulletType peekAmmo() {
            for (var content : ammoKeys) {
                if (seq.contains(content)) {
                    return ammoTypes.get(content);
                }
            }
            return null;
        }
        @Override
        public PayloadSeq getPayloads() {
            return seq;
        }
        @Override
        public void updateTile() {
            totalAmmo = seq.total();

            if (unit != null && unit.type() != null) {
                unit.ammo((float) unit.type().ammoCapacity * totalAmmo / maxAmmo);
            }
            super.updateTile();
        }

        @Override
        public void displayBars(Table bars) {
            super.displayBars(bars);

            bars.add(new Bar("stat.ammo", Pal.ammo, () -> (float) totalAmmo / maxAmmo)).growX();
            bars.row();
        }
        @Override
        public void write(Writes write) {
            super.write(write);
            seq.write(write);
        }
        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            seq.read(read);
        }
    }
}
