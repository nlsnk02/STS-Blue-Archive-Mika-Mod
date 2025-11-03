package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.modcore.Enums;
import bamika.utils.ModHelper;
import bamika.utils.RecollectManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Huiyisuipian extends AbstractMikaCard {

    public Huiyisuipian() {
        super(Huiyisuipian.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {

                AbstractCard card = ModHelper.getCard(
                        c -> c.hasTag(Enums.RECOLLECT),
                        AbstractDungeon.cardRandomRng);

                card.setCostForTurn(0);
                RecollectManager.flushRecall(card, false);

                addToTop(new MakeTempCardInHandAction(card));
                isDone = true;
            }
        });
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
        return new Huiyisuipian();
    }

}