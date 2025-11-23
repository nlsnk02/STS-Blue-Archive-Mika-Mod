package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Moran extends AbstractMikaCard {

    public Moran() {
        super(Moran.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
        this.cardsToPreview = new Huishou();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ArrayList<AbstractCard> cards = new ArrayList<>(AbstractDungeon.player.hand.group);
        cards.remove(this);

        addToBot(new MakeTempCardInDiscardAction(new Huishou(cards), 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Moran();
    }

}