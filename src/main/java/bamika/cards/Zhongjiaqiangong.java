package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.modcore.Enums;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Zhongjiaqiangong extends AbstractMikaCard {
    public Zhongjiaqiangong() {
        super(Zhongjiaqiangong.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 2;
        this.block = this.baseBlock = 16;
        setRecollectCard();
    }

    @Override
    public void onRecall(){
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int temp = Zhongjiaqiangong.this.baseDamage;
                Zhongjiaqiangong.this.baseDamage = Zhongjiaqiangong.this.baseBlock;
                Zhongjiaqiangong.this.baseBlock = temp;
                isDone = true;
            }
        });
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        if(this.damage > 12){
            effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                effect));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeBlock(6);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zhongjiaqiangong();
    }
}