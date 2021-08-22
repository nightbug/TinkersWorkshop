package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.HappyFlower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_happyflower extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_happyflower.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int TURNS = 2;
    private int ENERGY = 1;
    public aug_happyflower() {
        super(ID, relicInfo);
        AbstractRelic happyflower = new HappyFlower();
        flavorText = happyflower.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(HappyFlower.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(HappyFlower.ID)) {
                    counter = p().relics.get(i).counter;
                    instantObtain(p(), i, true);
                    fixDescription();
                    break;
                }
            }
        } else { super.obtain(); }
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_happyflower();
    }
    @Override
    public void atTurnStart() {
        this.counter++;
        if (this.counter == TURNS) {
            this.counter = 0;
            flash();
            addToBot(new RelicAboveCreatureAction(p(), this));
            addToBot(new GainEnergyAction(ENERGY));
        }
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], TURNS); }
}
