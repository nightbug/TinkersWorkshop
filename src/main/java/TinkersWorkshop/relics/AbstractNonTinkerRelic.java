package TinkersWorkshop.relics;

import TinkersWorkshop.util.RelicInfo;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.PowerTip;

public abstract class AbstractNonTinkerRelic extends CustomRelic {
    public AbstractNonTinkerRelic(String ID, RelicInfo relicInfo) { super(ID, relicInfo.loadRelicImage(relicInfo.relicName), relicInfo.loadOutlineImage(relicInfo.relicName), relicInfo.relicTier, relicInfo.sfx); }
    public void fixDescription() {
        description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
}