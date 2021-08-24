package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Lantern;
import com.megacrit.cardcrawl.relics.ToyOrnithopter;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;

public class aug_toyornithopter extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_toyornithopter.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int HEAL = 7;
    public aug_toyornithopter() {
        super(ID, relicInfo);
        AbstractRelic ornithopter = new ToyOrnithopter();
        img = ornithopter.img;
        largeImg = ornithopter.largeImg;
        outlineImg = ornithopter.outlineImg;
        flavorText = ornithopter.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(ToyOrnithopter.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(ToyOrnithopter.ID)) {
                    counter = p().relics.get(i).counter;
                    instantObtain(p(), i, true);
                    fixDescription();
                    break;
                }
            }
        } else { super.obtain(); }
    }
    public void onUsePotion() {
        flash();
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            atb(new RelicAboveCreatureAction(p(), this));
            atb(new HealAction(p(), p(), HEAL));
        } else {
            AbstractDungeon.player.heal(HEAL);
        }
    }
    @Override
    public AbstractTinkerRelic makeCopy() { return new aug_toyornithopter(); }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], HEAL); }
}
