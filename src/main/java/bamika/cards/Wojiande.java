package bamika.cards;

import bamika.actions.SelectFromRewardAction;
import bamika.fantasyCard.AbstractMikaCard;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.cards.green.CalculatedGamble;
import com.megacrit.cardcrawl.cards.purple.WreathOfFlame;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Wojiande extends AbstractMikaCard {

    public static final String[] actionTEXT;

    static {
        UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString("bamika:Wojiande");
        actionTEXT = actionUiStrings.TEXT;
    }

    public Wojiande() {
        super(Wojiande.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        MultiCardPreview.add(this, new Exhume(), new CalculatedGamble(), new Hologram(), new WreathOfFlame());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new Exhume());
        cards.add(new CalculatedGamble());
        cards.add(new Hologram());
        cards.add(new WreathOfFlame());

        if (upgraded) {
            cards.forEach(AbstractCard::upgrade);
        }

        addToBot(new SelectFromRewardAction(cards, o -> {
            if (!o.isPresent()) return;
            AbstractCard c = o.get();
            addToTop(new MakeTempCardInHandAction(c));
        }, actionTEXT[0], false, AbstractGameAction.ActionType.DRAW));
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
        return new Wojiande();
    }

}