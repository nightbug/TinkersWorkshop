package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MealTicket;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.att;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_mealticket extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_mealticket.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int HEAL = 20;
    public aug_mealticket() {
        super(ID, relicInfo);
        AbstractRelic mealticket = new MealTicket();
        img = mealticket.img;
        largeImg = mealticket.largeImg;
        outlineImg = mealticket.outlineImg;
        flavorText = mealticket.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(MealTicket.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(MealTicket.ID)) {
                    instantObtain(p(), i, true);
                    fixDescription();
                    break;
                }
            }
        } else { super.obtain(); }
    }
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof ShopRoom) {
            this.flash();
            att(new RelicAboveCreatureAction(p(), this));
            AbstractDungeon.player.heal(HEAL);
        }
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_mealticket();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], HEAL); }
}
