package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class Yisuixin extends AbstractMikaCard {
    public Yisuixin() {
        super(Yisuixin.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 15;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GainBlockAction(p, block));

        if (p.hasPower(WeakPower.POWER_ID) || p.hasPower(VulnerablePower.POWER_ID) || p.hasPower(FrailPower.POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(p, p, VulnerablePower.POWER_ID));
            addToBot(new RemoveSpecificPowerAction(p, p, WeakPower.POWER_ID));
            addToBot(new RemoveSpecificPowerAction(p, p, FrailPower.POWER_ID));
        } else {
            addToBot(new ApplyPowerAction(p, p, new FrailPower(p, 1, false)));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yisuixin();
    }
}