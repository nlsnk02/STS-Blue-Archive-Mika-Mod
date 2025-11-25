package bamika.cards;

import bamika.actions.DrawSpecificCardAction;
import bamika.actions.SelectFromGridAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Tangruochonglai extends AbstractMikaCard {

    public Tangruochonglai() {
        super(Tangruochonglai.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public static HashSet<AbstractCard> cardUsed = new HashSet<>();

    public static void onUseCard(AbstractCard card) {
        cardUsed.add(card);
    }

    public static void onLeaveDiscardPile(AbstractCard card) {
        cardUsed.remove(card);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = AbstractDungeon.player.discardPile.group
                .stream().filter(c -> !cardUsed.contains(c)).collect(Collectors.toCollection(ArrayList::new));

        addToBot(new SelectFromGridAction(list,
                (b, l) -> {
                    for (AbstractCard c : l) {
                        ModHelper.moveCard(c, AbstractDungeon.player.discardPile, AbstractDungeon.player.hand);
                        c.freeToPlayOnce = true;
                    }
                },
                cardStrings.EXTENDED_DESCRIPTION[0],
                AbstractGameAction.ActionType.DRAW,
                this.magicNumber,
                true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Tangruochonglai();
    }

}