package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class Huisu extends AbstractMikaCard {

    private int usedCount = 0;
    private int energyStore = 0;

    public Huisu() {
        super(Huisu.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE, CardColor.COLORLESS);
        this.block = this.baseBlock = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                usedCount += 1;
                if (usedCount % 2 == 1) {
                    energyStore = EnergyPanel.totalCount;
                } else {
                    EnergyPanel.setEnergy(energyStore);
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.shuffleBackIntoDrawPile = true;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Huisu();
    }

}