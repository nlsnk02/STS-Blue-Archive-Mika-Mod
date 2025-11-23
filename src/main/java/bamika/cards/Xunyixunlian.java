package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Xunyixunlian extends AbstractMikaCard {
    public Xunyixunlian() {
        super(Xunyixunlian.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 8;
    }

    public static int memoryCount = 0;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                memoryCount++;
                if (memoryCount > Xunyixunlian.this.baseMagicNumber) {
                    //TODO
                }
                this.isDone = true;
            }
        });
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
        return new Xunyixunlian();
    }
}