package bamika.cards;

import bamika.actions.SelectFromGridAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Huolimanman extends AbstractMikaCard {

    public Huolimanman() {
        super(Huolimanman.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 3;
        this.damage = this.baseDamage = 7;
    }

    public void onLeaveOrAdd() {
        applyPowers();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new VigorPower(AbstractDungeon.player, this.magicNumber)));
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
            upgradeMagicNumber(1);
            upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Huolimanman();
    }

}