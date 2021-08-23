package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.relics.CentennialPuzzle;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;

public class aug_centennialpuzzle extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_centennialpuzzle.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int DRAW = 3;
    private boolean usedThisCombat = false;
    public aug_centennialpuzzle() {
        super(ID, relicInfo);
        AbstractRelic puzzle = new CentennialPuzzle();
        img = puzzle.img;
        largeImg = puzzle.largeImg;
        outlineImg = puzzle.outlineImg;
        flavorText = puzzle.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(CentennialPuzzle.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(CentennialPuzzle.ID)) {
                    counter = p().relics.get(i).counter;
                    instantObtain(p(), i, true);
                    fixDescription();
                    break;
                }
            }
        } else { super.obtain(); }
    }
    public void atPreBattle() {
       usedThisCombat = false;
       this.pulse = true;
       beginPulse();
    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !usedThisCombat){
            flash();
            pulse = false;
            doDraw(DRAW, true);
            att(new RelicAboveCreatureAction(p(), this));
            usedThisCombat = true;
            grayscale = true;
        }
        return super.onAttacked(info, damageAmount);
    }
    @Override
    public void onVictory() { pulse = false; }
    @Override
    public void justEnteredRoom(AbstractRoom room) { grayscale = false; }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_centennialpuzzle();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], DRAW); }
}
