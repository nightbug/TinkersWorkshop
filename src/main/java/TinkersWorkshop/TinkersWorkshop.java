package TinkersWorkshop;

import TinkersWorkshop.events.FiresOfInvention;
import TinkersWorkshop.relics.AbstractTinkerRelic;
import TinkersWorkshop.relics.augments.*;
import TinkersWorkshop.ui.AugmentRelicOption;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;


@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class TinkersWorkshop implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PostUpdateSubscriber,
        StartGameSubscriber
{

    private static String modID = "tinkersworkshop";
    public static String getModID() { return modID; }
    public static String makeID(String idText) { return getModID() + ":" + idText; }
    public static boolean teleportToAnvil = false;
    public static HashMap<AbstractTinkerRelic, AbstractRelic> relicList = new HashMap<>();

    public static final String mintyID = "mintyspire";
    public static final boolean hasMinty;
    static {
        hasMinty = Loader.isModLoaded(mintyID);
        if (hasMinty) { System.out.println("has minty, very cool"); }
    }


    public TinkersWorkshop() {
        BaseMod.subscribe(this);

    }

    public static String makePath(String resourcePath) { return modID + "Resources/" + resourcePath; }
    public static String makeImagePath(String resourcePath) { return modID + "Resources/images/" + resourcePath; }
    public static String makeRelicPath(String resourcePath) { return modID + "Resources/images/relics/" + resourcePath; }
    public static String makeOutlinePath(String resourcePath) { return modID + "Resources/images/relics/outline/" + resourcePath; }
    public static String makePowerPath(String resourcePath) { return modID + "Resources/images/powers/" + resourcePath; }
    public static String makeCardPath(String resourcePath) { return modID + "Resources/images/cards/" + resourcePath; }
    public static String makeCharacterPath(String resourcePath) { return modID + "Resources/images/character/" + resourcePath; }
    public static String makeEffectPath(String resourcePath) { return modID + "Resources/images/effects/" + resourcePath; }
    public static String makeUIPath(String resourcePath) { return modID + "Resources/images/ui/" + resourcePath; }

    public static void initialize() { TinkersWorkshop tinkersWorkshop = new TinkersWorkshop(); }
    @Override
    public void receiveEditCharacters() {

    }
    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractTinkerRelic.class)
                .any(AbstractTinkerRelic.class, (info, relic) -> {
                    BaseMod.addRelic(relic, RelicType.SHARED);
                    UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receiveEditCards() {
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/Powerstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "Resources/localization/eng/Uistrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, getModID() + "Resources/localization/eng/Eventstrings.json");

    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receivePostInitialize() {
        ModPanel settingsPanel = new ModPanel();
        Texture badgeImg = new Texture("tinkersworkshopResources/images/badge.png");
        BaseMod.registerModBadge(badgeImg, "${project.name}", "Squeeny", "${project.description}", settingsPanel);

        BaseMod.addEvent(new AddEventParams.Builder(FiresOfInvention.ID, FiresOfInvention.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> false)
                //Act//
                .dungeonID("")
                .create());

        // add relic time.
        relicList.put(new aug_happyflower(), new HappyFlower());
        relicList.put(new aug_akabeko(), new Akabeko());
        relicList.put(new aug_anchor(), new Anchor());
        relicList.put(new aug_bloodvial(), new BloodVial());
        relicList.put(new aug_bagofmarbles(), new BagOfMarbles());
        relicList.put(new aug_bronzescales(), new BronzeScales());
        relicList.put(new aug_centennialpuzzle(), new CentennialPuzzle());
        relicList.put(new aug_ceramicfish(), new CeramicFish());
        relicList.put(new aug_mawbank(), new MawBank());
        relicList.put(new aug_mealticket(), new MealTicket());
        relicList.put(new aug_oddlysmoothstone(), new OddlySmoothStone());
        relicList.put(new aug_vajra(), new Vajra());
        relicList.put(new aug_pennib(), new PenNib());
        relicList.put(new aug_nunchaku(), new Nunchaku());
        relicList.put(new aug_orichalcum(), new Orichalcum());
        relicList.put(new aug_lantern(), new Lantern());
        relicList.put(new aug_toyornithopter(), new ToyOrnithopter());
        relicList.put(new aug_bagofpreparation(), new BagOfPreparation());

    }

    @Override
    public void receivePostUpdate() {
        if (teleportToAnvil) {
            AugmentRelicOption.eventWarp();
            teleportToAnvil = false;
        }
    }


    @Override
    public void receiveStartGame() {
        ShopScreen.actualPurgeCost = ShopScreen.purgeCost;
    }

    public void addTinkerableRelic(AbstractTinkerRelic upgrade, AbstractRelic base){
        relicList.put(upgrade, base);
    }
}

