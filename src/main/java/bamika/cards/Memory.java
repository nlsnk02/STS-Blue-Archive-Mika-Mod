package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.fantasyCard.MemoryHuiShou;
import bamika.fantasyCard.MemoryPingJing;
import bamika.fantasyCard.MemoryQianXun;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Memory extends AbstractMikaCard {

    public Memory() {
        super(Memory.class.getSimpleName(), 3, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        MultiCardPreview.add(this, new MemoryHuiShou(), new MemoryPingJing(), new MemoryQianXun());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new MemoryHuiShou()));
        addToBot(new MakeTempCardInHandAction(new MemoryPingJing()));
        addToBot(new MakeTempCardInHandAction(new MemoryQianXun()));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Memory();
    }

}