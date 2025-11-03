package bamika.cards;

import bamika.actions.SelectFromHandAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Weiyuchoumou extends AbstractMikaCard {
    public static final String[] actionTEXT;

    public Weiyuchoumou() {
        super(Weiyuchoumou.class.getSimpleName(), 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectFromHandAction(c -> true, l -> {
            for(AbstractCard c : l) {
                AbstractCard c2 = c.makeStatEquivalentCopy();
                CardModifierManager.addModifier(c, new RetainMod());

                addToTop(new MakeTempCardInHandAction(c2, Weiyuchoumou.this.magicNumber));
            }
        }, actionTEXT[0], this.magicNumber, true, true, AbstractGameAction.ActionType.DRAW));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Weiyuchoumou();
    }

    static {
        UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString("bamika:Huisu");
        actionTEXT = actionUiStrings.TEXT;
    }

}