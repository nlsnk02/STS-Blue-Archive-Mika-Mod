package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import bamika.utils.RecollectManager;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class Wosuonengzhuazhu extends AbstractMikaCard {

    public Wosuonengzhuazhu() {
        super(Wosuonengzhuazhu.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractPlayer p = AbstractDungeon.player;

                ArrayList<AbstractCard> cardsToRemove = new ArrayList<>(p.hand.group);
                int flag = 0;
                for (AbstractCard c : cardsToRemove) {
                    if (c.selfRetain) {
                        ModHelper.moveCard(c, p.hand, p.discardPile);
                        flag++;
                    }
                }

                addToTop(new DrawCardAction(flag * 2,
                        new AbstractGameAction() {
                            @Override
                            public void update() {
                                DrawCardAction.drawnCards.forEach(c ->
                                        CardModifierManager.addModifier(c, new RetainMod()));
                                this.isDone = true;
                            }
                        }));

                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wosuonengzhuazhu();
    }

}