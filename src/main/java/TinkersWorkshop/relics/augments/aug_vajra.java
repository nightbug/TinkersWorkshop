package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.OddlySmoothStone;
import com.megacrit.cardcrawl.relics.Vajra;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_vajra extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_vajra.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int STR = 2;
    public aug_vajra() {
        super(ID, relicInfo);
        AbstractRelic vajra = new Vajra();
        img = vajra.img;
        largeImg = vajra.largeImg;
        outlineImg = vajra.outlineImg;
        flavorText = vajra.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(Vajra.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(Vajra.ID)) {
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
        doPow(p(), new StrengthPower(p(), STR), true);
        att(new RelicAboveCreatureAction(p(), this));
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_vajra();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], STR); }
}
