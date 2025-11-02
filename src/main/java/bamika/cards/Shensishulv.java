package bamika.cards;

import bamika.actions.SelectFromHandAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Shensishulv extends AbstractMikaCard {
    public static final String[] actionTEXT;

    public Shensishulv() {
        super(Shensishulv.class.getSimpleName(), 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        this.block = this.baseBlock = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));

        addToBot(new SelectFromHandAction(c -> true,
                l -> {
                    if (!l.isEmpty()) {
                        ModHelper.timeTracing(l.get(0), AbstractDungeon.player.hand);
                    }
                }, actionTEXT[0], 1, false, false, AbstractGameAction.ActionType.DRAW));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shensishulv();
    }

    static {
        UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString("bamika:Huisu");
        actionTEXT = actionUiStrings.TEXT;
    }
}