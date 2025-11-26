package bamika.powers;

import bamika.misc.OnTimeTracingSubscriber;
import bamika.utils.ModHelper;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class JiashenyinxiangPower extends TwoAmountPower implements OnTimeTracingSubscriber {
    public static final String POWER_ID = ModHelper.makeID(JiashenyinxiangPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(JiashenyinxiangPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(JiashenyinxiangPower.class.getSimpleName(), false);


    public JiashenyinxiangPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = 2;
        loadRegion("heartDef");
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void OnTimeTracing(AbstractCard card) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {

                if (amount2 > 0) {
                    amount2--;
                } else {
                    amount2 = 2;
                    addToTop(new GainEnergyAction(amount));
                }

                updateDescription();
                this.isDone = true;
            }
        });
    }
}