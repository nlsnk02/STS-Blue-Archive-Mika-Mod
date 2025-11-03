package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Setup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ConciseStrike extends AbstractMikaCard {
    public ConciseStrike() {
        super(ConciseStrike.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        this.damage = this.baseDamage = 14;
        this.tags.add(CardTags.STRIKE);
        setRecollectCard();
    }

    @Override
    public void onRecall(){
        this.freeToPlayOnce = true;
        this.flash();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ConciseStrike();
    }
}