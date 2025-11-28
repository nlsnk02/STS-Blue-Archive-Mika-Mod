package bamika.powers;

import bamika.misc.OnRecallSubscriber;
import bamika.utils.ModHelper;
import basemod.cardmods.ExhaustMod;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class BeishuiyizhanPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(BeishuiyizhanPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(BeishuiyizhanPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(BeishuiyizhanPower.class.getSimpleName(), false);

    public BeishuiyizhanPower(AbstractCreature owner, int amount) {
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

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.selfRetain) return;

        if (this.amount == 1) {
            this.flash();
            this.addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
            this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "EndTurnDeath"));
        } else {
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}