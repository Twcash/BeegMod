package big;

import big.content.BigCrafters;
import big.content.BigDefense;
import big.content.BigTurrets;
import mindustry.mod.*;

public class BigMod extends Mod{

    public BigMod(){
    }

    @Override
    public void loadContent(){
        BigCrafters.load();
        BigTurrets.load();
        BigDefense.load();
    }
}
