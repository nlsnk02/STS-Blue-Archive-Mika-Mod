package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Huixuanti extends AbstractMikaCard {
    public Huixuanti() {
        super(Huixuanti.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int tmp = AbstractDungeon.actionManager.cardsPlayedThisCombat.size();
                if (tmp >= 2) {
                    AbstractCard c = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(tmp - 2);
                    ModHelper.moveCard(c, AbstractDungeon.player.hand);
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Huixuanti();
    }
}