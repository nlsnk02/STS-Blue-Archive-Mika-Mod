package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KyrieEleison extends AbstractMikaCard {
    public KyrieEleison() {
        super(KyrieEleison.class.getSimpleName(), 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = 5;
        this.block = this.baseBlock = 8;
        this.magicNumber = this.baseMagicNumber = 25;


        this.shuffleBackIntoDrawPile = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int tempDamage = this.damage, tempBaseDamage = this.baseDamage;
        this.baseDamage = this.baseBlock;
        super.applyPowers();
        this.block = this.damage;
        this.baseBlock = this.baseDamage;
        this.baseDamage = tempBaseDamage;
        this.damage = tempDamage;

        this.isDamageModified = this.damage != this.baseDamage;
        this.isBlockModified = this.block != this.baseBlock;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int tempDamage = this.damage, tempBaseDamage = this.baseDamage;
        this.baseDamage = this.baseBlock;
        super.calculateCardDamage(mo);
        this.block = this.damage;
        this.baseBlock = this.baseDamage;
        this.baseDamage = tempBaseDamage;
        this.damage = tempDamage;

        this.isDamageModified = this.damage != this.baseDamage;
        this.isBlockModified = this.block != this.baseBlock;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);

        int amp = 1;
        if (m != null) {
            float score = ((float) m.currentHealth / (float) m.maxHealth) * 100;
            if (score > this.baseMagicNumber) amp = 2;
        }

        for (int i = 0; i < 3; i++)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage * amp, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        addToBot(new DamageAction(m, new DamageInfo(p, this.block * amp, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(25);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new KyrieEleison();
    }
}