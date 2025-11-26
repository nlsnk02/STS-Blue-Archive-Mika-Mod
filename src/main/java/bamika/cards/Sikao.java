package bamika.cards;

import bamika.actions.SelectFromHandAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import bamika.utils.RecollectManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Sikao extends AbstractMikaCard {

    public Sikao() {
        super(Sikao.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectFromHandAction(c -> true,
                l -> {
                    for (AbstractCard c : l) {
                        ModHelper.timeTracing(c, AbstractDungeon.player.hand);
                    }
                },
                cardStrings.EXTENDED_DESCRIPTION[0],
                2,
                false,
                false,
                AbstractGameAction.ActionType.DRAW
        ));
        addToBot(new GainEnergyAction(this.magicNumber));
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
        return new Sikao();
    }

}