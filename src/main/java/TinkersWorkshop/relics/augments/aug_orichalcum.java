package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BronzeScales;
import com.megacrit.cardcrawl.relics.Orichalcum;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_orichalcum extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_orichalcum.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int BLOCK = 9;
    private boolean trigger = false;
    public aug_orichalcum() {
        super(ID, relicInfo);
        AbstractRelic orichalcum = new Orichalcum();
        img = orichalcum.img;
        largeImg = orichalcum.largeImg;
        outlineImg = orichalcum.outlineImg;
        flavorText = orichalcum.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(Orichalcum.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(Orichalcum.ID)) {
                    counter = p().relics.get(i).counter;
                    instantObtain(p(), i, true);
                    fixDescription();
                    break;
                }
            }
        } else { super.obtain(); }
    }
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.currentBlock == 0 || this.trigger) {
            trigger = false;
            flash();
            stopPulse();
            doDef(BLOCK, true);
            att(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
    public void atTurnStart() {
        this.trigger = false;
        if (AbstractDungeon.player.currentBlock == 0) { beginLongPulse(); }
    }
    public int onPlayerGainedBlock(float blockAmount) {
        if (blockAmount > 0.0F) { stopPulse(); }
        return MathUtils.floor(blockAmount);
    }
    public void onVictory() {
        this.stopPulse();
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_orichalcum();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], BLOCK); }
}
