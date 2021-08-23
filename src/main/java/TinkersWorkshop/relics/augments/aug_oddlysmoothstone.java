package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BronzeScales;
import com.megacrit.cardcrawl.relics.OddlySmoothStone;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_oddlysmoothstone extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_oddlysmoothstone.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int DEX = 2;
    public aug_oddlysmoothstone() {
        super(ID, relicInfo);
        AbstractRelic oddlySmoothStone = new OddlySmoothStone();
        img = oddlySmoothStone.img;
        largeImg = oddlySmoothStone.largeImg;
        outlineImg = oddlySmoothStone.outlineImg;
        flavorText = oddlySmoothStone.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(OddlySmoothStone.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(OddlySmoothStone.ID)) {
                    counter = p().relics.get(i).counter;
                    instantObtain(p(), i, true);
                    fixDescription();
                    break;
                }
            }
        } else { super.obtain(); }
    }
    public void atBattleStart() {
        flash();
        doPow(p(), new DexterityPower(p(), DEX), true);
        att(new RelicAboveCreatureAction(p(), this));
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_oddlysmoothstone();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], DEX); }
}
