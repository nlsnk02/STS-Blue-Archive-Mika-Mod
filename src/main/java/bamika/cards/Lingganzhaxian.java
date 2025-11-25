package bamika.cards;

import bamika.actions.SelectFromGridAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Lingganzhaxian extends AbstractMikaCard {

    public Lingganzhaxian() {
        super(Lingganzhaxian.class.getSimpleName(), 0, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : p.hand.group) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    CardModifierManager.addModifier(copy, new EtherealMod());
                    CardModifierManager.addModifier(copy, new ExhaustMod());
                    if (Lingganzhaxian.this.upgraded) {
                        c.modifyCostForCombat(-1);
                    }
                    addToTop(new MakeTempCardInHandAction(copy));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lingganzhaxian();
    }

}