package bamika.cards;

import bamika.actions.DrawSpecificCardAction;
import bamika.actions.SelectFromHandAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Predator;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Beixuanfangan extends AbstractMikaCard {

    public Beixuanfangan() {
        super(Beixuanfangan.class.getSimpleName(), 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectFromHandAction(c -> true,
                l -> {
                    for (AbstractCard c : l)
                        ModHelper.timeTracing(c, p.hand);
                },
                cardStrings.EXTENDED_DESCRIPTION[0],
                this.magicNumber,
                false,
                false,
                AbstractGameAction.ActionType.DRAW));
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.magicNumber)));
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
        return new Beixuanfangan();
    }

}