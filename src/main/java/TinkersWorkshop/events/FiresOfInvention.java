package TinkersWorkshop.events;

import TinkersWorkshop.relics.selection.SelectionToken;
import TinkersWorkshop.ui.ObtainRelicLater;
import TinkersWorkshop.ui.RelicSelectScreen;
import TinkersWorkshop.ui.SpinningRelicEffect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import static TinkersWorkshop.TinkersWorkshop.makeID;

public class FiresOfInvention extends AbstractImageEvent {
    public static final String ID = makeID(FiresOfInvention.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String INTRO_MSG = DESCRIPTIONS[0];
    private CurScreen screen = CurScreen.INTRO;
    private enum CurScreen { INTRO, PRE_COMBAT, END }

    public FiresOfInvention() {
        super(NAME, DESCRIPTIONS[0], "images/events/blacksmith.jpg");
        this.body = INTRO_MSG;
        //this.roomEventText.addDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.hasDialog = true;
        this.hasFocus = true;
    }

    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput) {
            buttonEffect(this.roomEventText.getSelectedOption());
        }
    }

    @Override
    public void onEnterRoom(){ }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        new SelectionToken().instantObtain();
                        this.screen = CurScreen.END;
                        return;
                    case 1:
                        this.screen = CurScreen.END;
                        this.roomEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.roomEventText.updateDialogOption(0, OPTIONS[2]);
                        this.roomEventText.clearRemainingOptions();
                        return;
                }
                break;
//            case PRE_COMBAT:
//                CardCrawlGame.music.fadeOutTempBGM();
//                AbstractDungeon.scene.fadeOutAmbiance();
//                CardCrawlGame.music.unsilenceBGM();
//                if (Settings.isDailyRun) { AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(50));
//                } else { AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(45, 55)); }
//                AbstractDungeon.getCurrRoom().addRelicToRewards(new NitoriTicket());
//                if (this.img != null) {
//                    this.img.dispose();
//                    this.img = null;
//                }
//                //this.img = ImageMaster.loadImage("images/events/sphereOpen.png");
//                enterCombat();
//                AbstractDungeon.lastCombatMetricKey = "Nitori";
//                break;
            case END:
                openMap();
                break;
        }
    }

}