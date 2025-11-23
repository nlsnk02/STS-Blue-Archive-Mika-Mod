package bamika.powers;

import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.TheBombPower;

import java.util.ArrayList;
import java.util.HashSet;

public class ZanbifengmangPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(ZanbifengmangPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(ZanbifengmangPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(ZanbifengmangPower.class.getSimpleName(), false);

    public static int idOffset = 0;

    public ArrayList<AbstractCard> cards;

    public ZanbifengmangPower(AbstractCreature owner, ArrayList<AbstractCard> cards) {
        idOffset++;
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        this.type = PowerType.BUFF;
        this.owner = owner;
        this.cards = cards;
        loadRegion("heartDef");
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                cards.forEach(c -> {
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.player.hand.addToHand(c);
                    } else AbstractDungeon.player.discardPile.addToTop(c);
                });
                this.isDone = true;
            }
        });
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        if (cards == null || cards.isEmpty()) {
            this.description = DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0]
                    + cards.stream().map(c -> c.name).reduce((a, b) -> a + "," + b).orElse("")
                    + DESCRIPTIONS[1];
        }
    }
}