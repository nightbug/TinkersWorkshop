package TinkersWorkshop.ui;

import TinkersWorkshop.TinkersWorkshop;
import TinkersWorkshop.events.FiresOfInvention;
import TinkersWorkshop.util.TexLoader;
import basemod.CustomEventRoom;
import basemod.ReflectionHacks;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import static TinkersWorkshop.TinkersWorkshop.makeUIPath;
import static TinkersWorkshop.TinkersWorkshop.relicList;
import static TinkersWorkshop.util.actionShortcuts.p;

public class AugmentRelicOption extends AbstractCampfireOption {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TinkersWorkshop.makeID("BustKeyButton"));
    public static final String[] TEXT = uiStrings.TEXT;

    public AugmentRelicOption() {
        if (AbstractDungeon.player.gold < ShopScreen.actualPurgeCost || !hasAugmentableRelic()) {
            this.usable = false;
            updateImage();
        } else {
            this.usable = true;
            updateImage();
        }
    }


    public void updateImage() {
        label = TEXT[0];
        if (this.usable) {
            this.img = TexLoader.getTexture(makeUIPath("augment.png"));
            this.description = String.format(TEXT[1], ShopScreen.actualPurgeCost);
        } else {
            this.img = TexLoader.getTexture(makeUIPath("augmentdisable.png"));
            this.description = String.format(TEXT[2], ShopScreen.actualPurgeCost);
        }


    }

    public void update() {
        float hackScale = (float) ReflectionHacks.getPrivate(this, AbstractCampfireOption.class, "scale");
        if (this.hb.hovered) {
            if (!this.hb.clickStarted) {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
            } else {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
            }
        } else {
            ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
        }

        super.update();

        if (AbstractDungeon.player.gold < ShopScreen.actualPurgeCost || !hasAugmentableRelic()) {
            this.usable = false;
            updateImage();
        }
        if (AbstractDungeon.player.gold >= ShopScreen.actualPurgeCost && hasAugmentableRelic()) {
            this.usable = true;
            updateImage();
        }
    }

    public static void eventWarp() {
        AbstractDungeon.eventList.add(0, FiresOfInvention.ID);// 50
        MapRoomNode cur = AbstractDungeon.currMapNode;// 52
        MapRoomNode node = new MapRoomNode(cur.x, cur.y);// 53
        node.room = new CustomEventRoom();// 54
        ArrayList<MapEdge> curEdges = cur.getEdges();// 56

        for (MapEdge edge : curEdges) {
            node.addEdge(edge);// 58
        }

        AbstractDungeon.player.releaseCard();// 61
        AbstractDungeon.overlayMenu.hideCombatPanels();// 62
        AbstractDungeon.previousScreen = null;// 63
        AbstractDungeon.dynamicBanner.hide();// 64
        AbstractDungeon.dungeonMapScreen.closeInstantly();// 65
        AbstractDungeon.closeCurrentScreen();// 66
        AbstractDungeon.topPanel.unhoverHitboxes();// 67
        AbstractDungeon.fadeIn();// 68
        AbstractDungeon.effectList.clear();// 69
        AbstractDungeon.topLevelEffects.clear();// 70
        AbstractDungeon.topLevelEffectsQueue.clear();// 71
        AbstractDungeon.effectsQueue.clear();// 72
        AbstractDungeon.dungeonMapScreen.dismissable = true;// 73
        AbstractDungeon.nextRoom = node;// 74
        AbstractDungeon.setCurrMapNode(node);// 75
        AbstractDungeon.getCurrRoom().onPlayerEntry();// 76
        AbstractDungeon.scene.nextRoom(node.room);// 77
        AbstractDungeon.rs = node.room.event instanceof AbstractImageEvent ? AbstractDungeon.RenderScene.EVENT : AbstractDungeon.RenderScene.NORMAL;// 78
    }

    @Override
    public void useOption() {
        TinkersWorkshop.teleportToAnvil = true;
    }

    public boolean hasAugmentableRelic(){
        ArrayList<AbstractRelic> relics = new ArrayList<>();
        relicList.entrySet().stream().filter(
                p -> p().hasRelic(p.getValue().relicId)).forEach(relic -> relics.add(relic.getKey()));
        return relics.size() > 0;
    }
}