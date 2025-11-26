package bamika.cards;

import bamika.actions.SelectFromHandAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Jianxuan extends AbstractMikaCard {

    public Jianxuan() {
        super(Jianxuan.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber, new SelectFromHandAction(
                c -> DrawCardAction.drawnCards.contains(c),
                l -> {
                    for (AbstractCard c : l) {
                        ModHelper.moveCard(c, AbstractDungeon.player.hand, AbstractDungeon.player.drawPile);
                    }
                },
                cardStrings.EXTENDED_DESCRIPTION[0],
                1,
                false,
                false,
                AbstractGameAction.ActionType.DRAW
        )));
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
        return new Jianxuan();
    }

}