package bamika.powers;

import bamika.actions.PlayACardAction;
import bamika.misc.OnRecallSubscriber;
import bamika.utils.ModHelper;
import bamika.utils.RecollectManager;
import basemod.cardmods.ExhaustMod;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashSet;

public class ZhenguihuiyiPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(ZhenguihuiyiPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(ZhenguihuiyiPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(ZhenguihuiyiPower.class.getSimpleName(), false);

    /*
    让这个效果打出的牌不会触发自己
    每张战斗开始时清空
     */
    public static HashSet<AbstractCard> cardsPlayed = new HashSet<>();

    public ZhenguihuiyiPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;
        this.amount = amount;
        loadRegion("heartDef");
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (RecollectManager.isRecalled(card) && !cardsPlayed.contains(card)) {

            AbstractCard copy = card.makeStatEquivalentCopy();

            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    cardsPlayed.add(copy);
                    isDone = true;
                }
            });
            addToBot(new PlayACardAction(copy, null, null, true));
        }
    }
}