package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.HappyFlower;
import com.megacrit.cardcrawl.relics.Nunchaku;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;
import static TinkersWorkshop.util.actionShortcuts.att;

public class aug_nunchaku extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_nunchaku.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int ATTACKS_NEEDED = 7;
    private int ENERGY = 1;
    public aug_nunchaku() {
        super(ID, relicInfo);
        AbstractRelic nunchaku = new Nunchaku();
        img = nunchaku.img;
        largeImg = nunchaku.largeImg;
        outlineImg = nunchaku.outlineImg;
        flavorText = nunchaku.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(Nunchaku.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(Nunchaku.ID)) {
                    counter = Math.min(p().relics.get(i).counter, ATTACKS_NEEDED);
                    instantObtain(p(), i, true);
                    fixDescription();
                    break;
                }
            }
        } else { super.obtain(); }
    }
    public void atBattleStart() {
        if (counter % ATTACKS_NEEDED == 0 && counter != 0)  {
            flash();
            counter = 0;
            att(new GainEnergyAction(ENERGY));
            att(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ++counter;
            if (this.counter % ATTACKS_NEEDED == 0) {
                counter = 0;
                flash();
                atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                atb(new GainEnergyAction(ENERGY));
            }
        }
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_nunchaku();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], ATTACKS_NEEDED); }
}