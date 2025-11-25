package bamika.cards;

import bamika.actions.DrawSpecificCardAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.fantasyCard.Huishou;
import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Beizhan extends AbstractMikaCard {

    public Beizhan() {
        super(Beizhan.class.getSimpleName(), 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cards = new ArrayList<>(AbstractDungeon.player.hand.group)
                .stream().filter(c -> c.type != CardType.ATTACK).collect(Collectors.toCollection(ArrayList::new));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                cards.forEach(c -> ModHelper.timeTracing(c, AbstractDungeon.player.hand));
                this.isDone = true;
            }
        });
        addToBot(new DrawSpecificCardAction(cards.size(), c -> c.type == CardType.ATTACK));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Beizhan();
    }

}