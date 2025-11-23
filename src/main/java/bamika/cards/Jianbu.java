package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.modcore.Enums;
import bamika.utils.ModHelper;
import bamika.utils.RecollectManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Jianbu extends AbstractMikaCard {

    public Jianbu() {
        super(Jianbu.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 3;
        this.block = this.baseBlock = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int gainBlock = this.block;
        addToTop(new DrawCardAction(this.magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : DrawCardAction.drawnCards) {
                    if (c.selfRetain) {
                        addToTop(new GainBlockAction(AbstractDungeon.player, gainBlock));
                        if (Jianbu.this.upgraded) {
                            addToTop(new AbstractGameAction() {
                                @Override
                                public void update() {
                                    if (c.costForTurn > 0)
                                        c.setCostForTurn(c.costForTurn - 1);
                                    this.isDone = true;
                                }
                            });
                        }
                    }
                }
                this.isDone = true;
            }
        }));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Jianbu();
    }

}