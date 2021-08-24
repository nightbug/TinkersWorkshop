package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BagOfPreparation;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;

public class aug_bagofpreparation extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_bagofpreparation.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int DRAW_FIRST_STEP = 2;
    private int DRAW_SECOND_STEP = 1;
    private int TURN = 2;
    public aug_bagofpreparation() {
        super(ID, relicInfo);
        AbstractRelic bagofpreperation = new BagOfPreparation();
        img = bagofpreperation.img;
        largeImg = bagofpreperation.largeImg;
        outlineImg = bagofpreperation.outlineImg;
        flavorText = bagofpreperation.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(BagOfPreparation.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(BagOfPreparation.ID)) {
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
        doDraw(DRAW_FIRST_STEP);
    }
    @Override
    public void atTurnStartPostDraw() {
        if(GameActionManager.turn == TURN){
            flash();
            atb(new RelicAboveCreatureAction(p(), this));
            doDraw(DRAW_SECOND_STEP);
        }
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_bagofpreparation();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], DRAW_FIRST_STEP); }
}
