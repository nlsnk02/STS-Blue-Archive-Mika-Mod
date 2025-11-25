package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.powers.KesuxingjiyiPower;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Kesuxingjiyi extends AbstractMikaCard {

    public Kesuxingjiyi() {
        super(Kesuxingjiyi.class.getSimpleName(), 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(0);
                addToTop(new ApplyPowerAction(p, p, new KesuxingjiyiPower(p, 1, card)));
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Kesuxingjiyi();
    }

}