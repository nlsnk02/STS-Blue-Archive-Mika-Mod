package bamika.cards;

import bamika.actions.XCardAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.powers.BeishuiyizhanPower;
import bamika.utils.RecollectManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Beishuiyizhan extends AbstractMikaCard {

    public Beishuiyizhan() {
        super(Beishuiyizhan.class.getSimpleName(), -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new XCardAction(
                AbstractGameAction.ActionType.DRAW,
                p,
                freeToPlayOnce,
                energyOnUse,
                x -> {
                    addToTop(new ChangeStanceAction("Divinity"));
                    addToTop(new DrawCardAction(x));
                    addToTop(new ApplyPowerAction(p, p, new BeishuiyizhanPower(p, x + this.magicNumber), 0));
                }
        ));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Beishuiyizhan();
    }

}