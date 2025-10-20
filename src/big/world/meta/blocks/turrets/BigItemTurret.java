package big.world.meta.blocks.turrets;

import big.world.meta.BigStatValues;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;

public class BigItemTurret extends ItemTurret {
    public BigItemTurret(String name) {
        super(name);
    }
    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, BigStatValues.ammo(ammoTypes));
    }
}
