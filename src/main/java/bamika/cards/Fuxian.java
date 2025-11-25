package bamika.cards;

import bamika.actions.SelectFromGridAction;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.fantasyCard.MemoryHuiShou;
import bamika.fantasyCard.MemoryPingJing;
import bamika.fantasyCard.MemoryQianXun;
import bamika.utils.ModHelper;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fuxian extends AbstractMikaCard {

    public Fuxian() {
        super(Fuxian.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE, CardColor.COLORLESS);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectFromGridAction(
                (b, c) -> {
                    if (Fuxian.this.upgraded) return b == SelectFromGridAction.Source.DISCARD_PILE;
                    return b == SelectFromGridAction.Source.DISCARD_PILE || b == SelectFromGridAction.Source.EXHAUST_PILE;
                },
                (bs, cs) -> {
                    for (AbstractCard c : cs) {
                        ModHelper.moveCard(c, AbstractDungeon.player.hand);
                        c.freeToPlayOnce = true;
                    }
                },
                cardStrings.EXTENDED_DESCRIPTION[0],
                AbstractGameAction.ActionType.DRAW,
                1,
                true
        ));
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
        return new Fuxian();
    }

}