package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import bamika.utils.RecollectManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Huishou extends AbstractMikaCard {

    private final ArrayList<AbstractCard> cards;

    public Huishou() {
        this(new ArrayList<>());
    }

    public Huishou(ArrayList<AbstractCard> cards) {
        super(Huishou.class.getSimpleName(), 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        this.cards = cards;
        this.exhaust = true;
        cards.forEach(c -> MultiCardPreview.add(this, c.makeStatEquivalentCopy()));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : cards) {
                    RecollectManager.flushRecall(c, false);
                    ModHelper.moveCard(c, AbstractDungeon.player.hand);
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Huishou(new ArrayList<>(this.cards));
    }

}