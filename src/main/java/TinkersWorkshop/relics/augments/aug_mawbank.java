package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CeramicFish;
import com.megacrit.cardcrawl.relics.MawBank;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_mawbank extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_mawbank.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int GOLD = 16;
    public aug_mawbank() {
        super(ID, relicInfo);
        AbstractRelic mawbank = new MawBank();
        img = mawbank.img;
        largeImg = mawbank.largeImg;
        outlineImg = mawbank.outlineImg;
        flavorText = mawbank.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(MawBank.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(MawBank.ID)) {
                    instantObtain(p(), i, true);
                    fixDescription();
                    break;
                }
            }
        } else { super.obtain(); }
    }
    public void onEnterRoom(AbstractRoom room) {
        if (!this.usedUp) {
            this.flash();
            AbstractDungeon.player.gainGold(GOLD);
        }
    }
    public void onSpendGold() {
        if (!this.usedUp) {
            this.flash();
            this.setCounter(-2);
        }
    }
    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter == -2) {
            this.usedUp();
            this.counter = -2;
        }
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_mawbank();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], GOLD); }
}
