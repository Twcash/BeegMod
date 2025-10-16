package big;

import big.content.BigCrafters;
import mindustry.mod.*;

public class BigMod extends Mod{

    public BigMod(){
    }

    @Override
    public void loadContent(){
        BigCrafters.load();
    }
}
