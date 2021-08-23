package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Akabeko;
import com.megacrit.cardcrawl.relics.HappyFlower;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;

public class aug_akabeko extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_akabeko.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int DMG_INCREASE = 11;
    public aug_akabeko() {
        super(ID, relicInfo);
        AbstractRelic akabeko = new Akabeko();
        flavorText = akabeko.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(Akabeko.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(Akabeko.ID)) {
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
        doPow(p(), new VigorPower(p(), DMG_INCREASE), true);
        att(new RelicAboveCreatureAction(p(), this));
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_akabeko();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], DMG_INCREASE); }
}
