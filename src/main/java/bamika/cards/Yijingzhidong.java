package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.SpotWeakness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Yijingzhidong extends AbstractMikaCard {
    public Yijingzhidong() {
        super(Yijingzhidong.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 0;
        this.selfRetain = true;
    }

    private int getDamage(AbstractMonster m) {
        if (m != null && m.getIntentDmg() >= 0) {
            return m.getIntentDmg() * (this.upgraded ? 2 : 1);
        }else return 0;
    }

    @Override
    public void applyPowers() {
        int formalBaseDamage = this.baseDamage;
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(m.hb.hovered){
                this.baseDamage = this.getDamage(m);
            }
        }
        super.applyPowers();
        this.baseDamage = formalBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;

        this.rawDescription = cardStrings.DESCRIPTION;
        if(upgraded) this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        this.rawDescription += String.format(cardStrings.EXTENDED_DESCRIPTION[0], damage);
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int formalBaseDamage = this.baseDamage;
        this.baseDamage = getDamage(mo);
        super.calculateCardDamage(mo);
        this.baseDamage = formalBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        if(this.damage > 12){
            effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                effect));
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
        return new Yijingzhidong();
    }
}