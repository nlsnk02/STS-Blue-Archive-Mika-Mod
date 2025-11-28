package bamika.cards;

import bamika.actions.XCardAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.misc.OnTimeTracingSubscriber;
import bamika.powers.BeishuiyizhanPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Xingyunbi extends AbstractMikaCard implements OnTimeTracingSubscriber {

    public Xingyunbi() {
        super(Xingyunbi.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
    }

    private void foo() {
        addToBot(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        foo();
    }

    @Override
    public void OnTimeTracing(AbstractCard card) {
        foo();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Xingyunbi();
    }
}