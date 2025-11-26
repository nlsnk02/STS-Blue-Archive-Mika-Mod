package bamika.cards;

import bamika.actions.SelectFromGridAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.misc.ChangePositionSubscriber;
import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class Wannengyaoshi extends AbstractMikaCard implements ChangePositionSubscriber {

    public Wannengyaoshi() {
        super(Wannengyaoshi.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
    }


    private SelectFromGridAction.Source map(CardGroup.CardGroupType type) {
        if (type == CardGroup.CardGroupType.EXHAUST_PILE)
            return SelectFromGridAction.Source.EXHAUST_PILE;
        if (type == CardGroup.CardGroupType.DISCARD_PILE)
            return SelectFromGridAction.Source.DISCARD_PILE;
        if (type == CardGroup.CardGroupType.HAND)
            return SelectFromGridAction.Source.HAND;
        if (type == CardGroup.CardGroupType.DRAW_PILE)
            return SelectFromGridAction.Source.DRAW_PILE;
        return SelectFromGridAction.Source.NONE;
    }

    @Override
    public void onChangePosition(CardGroup.CardGroupType from, CardGroup.CardGroupType to) {
        if (to != CardGroup.CardGroupType.HAND) {
            addToBot(new SelectFromGridAction((b, c) -> b == map(to),
                    (bs, cs) -> {
                        for (AbstractCard c : cs)
                            ModHelper.moveCard(c, AbstractDungeon.player.hand);
                    },
                    cardStrings.EXTENDED_DESCRIPTION[0],
                    AbstractGameAction.ActionType.DRAW,
                    1,
                    true));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wannengyaoshi();
    }
}