package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BronzeScales;
import com.megacrit.cardcrawl.relics.CeramicFish;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_ceramicfish extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_ceramicfish.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int GOLD = 12;
    public aug_ceramicfish() {
        super(ID, relicInfo);
        AbstractRelic fish = new CeramicFish();
        img = fish.img;
        largeImg = fish.largeImg;
        outlineImg = fish.outlineImg;
        flavorText = fish.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(CeramicFish.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(CeramicFish.ID)) {
                    counter = p().relics.get(i).counter;
                    instantObtain(p(), i, true);
                    fixDescription();
                    break;
                }
            }
        } else { super.obtain(); }
    }
    public void onObtainCard(AbstractCard c) {
        AbstractDungeon.player.gainGold(GOLD);
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_ceramicfish();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], GOLD); }
}
