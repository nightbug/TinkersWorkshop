package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Nunchaku;
import com.megacrit.cardcrawl.relics.PenNib;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;

public class aug_pennib extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_pennib.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int ATTACKS_NEEDED = 7;
    public aug_pennib() {
        super(ID, relicInfo);
        AbstractRelic pennib = new PenNib();
        img = pennib.img;
        largeImg = pennib.largeImg;
        outlineImg = pennib.outlineImg;
        flavorText = pennib.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(PenNib.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(PenNib.ID)) {
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
            doPow(p(), new PenNibPower(p(), 1),true);
            att(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ++counter;
            if (counter % ATTACKS_NEEDED == 0) {
                counter = 0;
                flash();
                atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                doPow(p(), new PenNibPower(p(), 1));
            }
        }
    }
    @Override
    public AbstractTinkerRelic makeCopy() { return new aug_pennib(); }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], ATTACKS_NEEDED); }
}