package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BronzeScales;
import com.megacrit.cardcrawl.relics.Lantern;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_lantern extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_lantern.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int ENERGY = 1;
    private int TURN = 1;
    public aug_lantern() {
        super(ID, relicInfo);
        AbstractRelic lantern = new Lantern();
        img = lantern.img;
        largeImg = lantern.largeImg;
        outlineImg = lantern.outlineImg;
        flavorText = lantern.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(Lantern.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(Lantern.ID)) {
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
        att(new GainEnergyAction(ENERGY));
        att(new RelicAboveCreatureAction(p(), this));
    }
    @Override
    public void atTurnStart() {
        if(GameActionManager.turn == TURN){
            flash();
            att(new GainEnergyAction(ENERGY));
            att(new RelicAboveCreatureAction(p(), this));
        }
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_lantern();
    }
    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
}
