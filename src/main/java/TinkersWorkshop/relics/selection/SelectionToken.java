package TinkersWorkshop.relics.selection;

import TinkersWorkshop.relics.AbstractNonTinkerRelic;
import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.ui.ObtainRelicLater;
import TinkersWorkshop.ui.RelicSelectScreen;
import TinkersWorkshop.ui.SpinningRelicEffect;
import TinkersWorkshop.ui.WaitBuffer;
import TinkersWorkshop.util.RelicInfo;
import basemod.DevConsole;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static TinkersWorkshop.TinkersWorkshop.makeID;
import static TinkersWorkshop.TinkersWorkshop.relicList;
import static TinkersWorkshop.util.actionShortcuts.p;

public class SelectionToken extends AbstractNonTinkerRelic
{
    private boolean relicSelected = true;
    private RelicSelectScreen relicSelectScreen;
    private boolean fakeHover = false;
    private static final RelicInfo relicInfo = new RelicInfo(
            SelectionToken.class.getSimpleName(),
            RelicTier.SPECIAL,
            LandingSound.MAGICAL
    );
    public static final String ID = makeID(relicInfo.relicName);
    public SelectionToken()
    {
        super(ID, relicInfo);
        fixDescription();
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip()
    {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;

        openRelicSelect();
    }

    private void openRelicSelect()
    {
        relicSelected = false;
        ArrayList<AbstractRelic> relics = new ArrayList<>();
        relicList.entrySet().stream().filter(
                p -> p().hasRelic(p.getValue().relicId)).forEach(relic -> relics.add(relic.getKey().makeCopy()));

        relicSelectScreen = new RelicSelectScreen();
        relicSelectScreen.open(relics);
    }

    @Override
    public void update()
    {
        super.update();

        if (!relicSelected) {
            if (relicSelectScreen.doneSelecting()) {
                relicSelected = true;
                AbstractRelic relic = relicSelectScreen.getSelectedRelics().get(0).makeCopy();
                switch (relic.tier) {
                    case COMMON:
                        AbstractDungeon.commonRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                    case UNCOMMON:
                        AbstractDungeon.uncommonRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                    case RARE:
                        AbstractDungeon.rareRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                    case SHOP:
                        AbstractDungeon.shopRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                    case BOSS:
                        AbstractDungeon.bossRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                }
                AbstractDungeon.player.loseGold(ShopScreen.actualPurgeCost);
                ShopScreen.actualPurgeCost += 25;
                AbstractDungeon.effectsQueue.add(0, new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectsQueue.add(1, new SpinningRelicEffect(relic));
                AbstractDungeon.effectsQueue.add(2, new ScreenOnFireEffect());
                AbstractDungeon.effectsQueue.add(3, new ObtainRelicLater(relic));
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            } else {
                relicSelectScreen.update();
                if (!hb.hovered) {
                    fakeHover = true;
                }
                hb.hovered = true;
            }
        }
    }

    @Override
    public void renderTip(SpriteBatch sb)
    {
        if (!relicSelected && fakeHover) {
            relicSelectScreen.render(sb);
        }
        if (fakeHover) {
            fakeHover = false;
            hb.hovered = false;
        } else {
            super.renderTip(sb);
        }
    }

    @Override
    public void renderInTopPanel(SpriteBatch sb)
    {
        if (!relicSelected && !fakeHover) {
            relicSelectScreen.render(sb);
        }
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new SelectionToken();
    }
}