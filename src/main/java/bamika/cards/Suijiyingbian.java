package bamika.cards;

import bamika.actions.SelectFromRewardAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.fantasyCard.SuijiyingbianBlur;
import bamika.fantasyCard.SuijiyingbianDraw;
import bamika.fantasyCard.SuijiyingbianFreeCost;
import bamika.utils.ModHelper;
import bamika.utils.RecollectManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Suijiyingbian extends AbstractMikaCard {

    public Suijiyingbian() {
        super(Suijiyingbian.class.getSimpleName(), 2, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.selfRetain = true;
        this.magicNumber = this.baseMagicNumber = 3;
        this.block = this.baseBlock = 14;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new SuijiyingbianDraw());
        cards.add(new SuijiyingbianFreeCost());
        cards.add(new SuijiyingbianBlur());

        cards.forEach(c -> {
            if (this.upgraded) {
                c.upgrade();
            }
            c.applyPowers();
        });


        addToBot(new SelectFromRewardAction(cards,
                o -> o.ifPresent(abstractCard -> abstractCard.use(AbstractDungeon.player, m)),
                cardStrings.EXTENDED_DESCRIPTION[0],
                false, AbstractGameAction.ActionType.DRAW
        ));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            upgradeMagicNumber(1);
            upgradeBlock(4);

            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Suijiyingbian();
    }

}