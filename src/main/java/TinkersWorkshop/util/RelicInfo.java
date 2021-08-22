package TinkersWorkshop.util;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static TinkersWorkshop.TinkersWorkshop.makeOutlinePath;
import static TinkersWorkshop.TinkersWorkshop.makeRelicPath;

public class RelicInfo {
    public String relicName;
    public AbstractRelic.RelicTier relicTier;
    public AbstractRelic.LandingSound sfx;
    public RelicInfo(String relicName, AbstractRelic.RelicTier relicTier, AbstractRelic.LandingSound sfx)
    {
        this.relicName = relicName;
        this.relicTier = relicTier;
        this.sfx = sfx;
    }
    public Texture loadRelicImage(String name){ return TexLoader.getTexture(makeRelicPath(name + ".png")); }
    public Texture loadOutlineImage(String name){ return TexLoader.getTexture(makeOutlinePath(name + ".png")); }
}