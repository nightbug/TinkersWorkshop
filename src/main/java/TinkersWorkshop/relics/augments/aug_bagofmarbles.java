package TinkersWorkshop.relics.augments;

import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.util.RelicInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Akabeko;
import com.megacrit.cardcrawl.relics.BagOfMarbles;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.util.actionShortcuts.*;
import static TinkersWorkshop.util.actionShortcuts.p;

public class aug_bagofmarbles extends AbstractTinkerRelic {
    public static final RelicInfo relicInfo = new RelicInfo(
            aug_bagofmarbles.class.getSimpleName(),
            RelicTier.COMMON,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    private int VULN = 2;
    public aug_bagofmarbles() {
        super(ID, relicInfo);
        AbstractRelic marbles = new BagOfMarbles();
        img = marbles.img;
        largeImg = marbles.largeImg;
        outlineImg = marbles.outlineImg;
        flavorText = marbles.flavorText;
        fixDescription();
    }
    @Override
    public void obtain() {
        if (p().hasRelic(BagOfMarbles.ID)) {
            for (int i = 0; i < p().relics.size(); ++i) {
                if (p().relics.get(i).relicId.equals(BagOfMarbles.ID)) {
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
        for(AbstractMonster m: getAliveMonsters()){
            atb(new RelicAboveCreatureAction(m, this));
            doPow(m, new VulnerablePower(m, VULN, false));
        }
    }
    @Override
    public AbstractTinkerRelic makeCopy() {
        return new aug_bagofmarbles();
    }
    @Override
    public String getUpdatedDescription() { return String.format(DESCRIPTIONS[0], VULN); }
}
