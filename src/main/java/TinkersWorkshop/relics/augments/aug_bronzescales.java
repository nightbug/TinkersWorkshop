package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Akabeko;
import com.megacrit.cardcrawl.relics.BronzeScales;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_bronzescales extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_bronzescales.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int THORNS = 4;
    public aug_bronzescales() {
        super(ID, relicInfo);
        AbstractRelic scales = new BronzeScales();
        flavorText = scales.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(BronzeScales.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(BronzeScales.ID)) {
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
        doPow(p(), new ThornsPower(p(), THORNS), true);
        att(new RelicAboveCreatureAction(p(), this));
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_bronzescales();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], THORNS); }
}
