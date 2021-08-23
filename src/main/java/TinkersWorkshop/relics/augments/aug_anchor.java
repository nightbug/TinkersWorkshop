package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Akabeko;
import com.megacrit.cardcrawl.relics.Anchor;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_anchor extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_anchor.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int BLOCK = 13;
    public aug_anchor() {
        super(ID, relicInfo);
        AbstractRelic anchor = new Anchor();
        img = anchor.img;
        largeImg = anchor.largeImg;
        outlineImg = anchor.outlineImg;
        flavorText = anchor.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(Anchor.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(Anchor.ID)) {
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
        doDef(BLOCK);
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_anchor();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], BLOCK); }
}
