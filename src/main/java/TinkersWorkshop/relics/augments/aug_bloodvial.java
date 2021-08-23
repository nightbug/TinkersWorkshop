package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.relics.BloodVial;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;

public class aug_bloodvial extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_bloodvial.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int HP = 3;
    public aug_bloodvial() {
        super(ID, relicInfo);
        AbstractRelic bloodvial = new BloodVial();
        flavorText = bloodvial.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(BloodVial.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(BloodVial.ID)) {
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
        atb(new RelicAboveCreatureAction(p(), this));
        atb(new HealAction(p(), p(), HP));
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_bloodvial();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], HP); }
}
