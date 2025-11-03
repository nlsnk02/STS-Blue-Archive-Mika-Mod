package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.modcore.Enums;
import bamika.utils.ModHelper;
import bamika.utils.RecollectManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.FollowUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Zhuifu extends AbstractMikaCard {

    public Zhuifu() {
        super(Zhuifu.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2) {

            for (int i = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2, flag = 0;
                 i >= 0 && flag < this.magicNumber;
                 i--, flag++) {
                AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i);
                if (card.type == CardType.POWER || AbstractDungeon.player.exhaustPile.contains(card)) {
                    this.exhaust = true;
                    this.exhaustOnlyOnce = true;
                }

                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        boolean flag = false;

                        if (AbstractDungeon.player.drawPile.contains(card)) {
                            flag = true;
                            ModHelper.moveCard(card, AbstractDungeon.player.drawPile, AbstractDungeon.player.hand);
                        }
                        if (AbstractDungeon.player.discardPile.contains(card)) {
                            flag = true;
                            ModHelper.moveCard(card, AbstractDungeon.player.discardPile, AbstractDungeon.player.hand);
                        }
                        if (AbstractDungeon.player.hand.contains(card)) {
                            flag = true;
                        }
                        if (AbstractDungeon.player.exhaustPile.contains(card)) {
                            flag = true;
                            ModHelper.moveCard(card, AbstractDungeon.player.exhaustPile, AbstractDungeon.player.hand);
                        }

                        if (!flag && card != Zhuifu.this) {
                            addToTop(new MakeTempCardInHandAction(card));
                        }

                        this.isDone = true;
                    }
                });
            }

        }
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
        return new Zhuifu();
    }

}