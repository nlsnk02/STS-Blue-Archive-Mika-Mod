package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.utils.ModHelper;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.cards.green.CalculatedGamble;
import com.megacrit.cardcrawl.cards.purple.WreathOfFlame;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashMap;

public class Chuxvguowang extends AbstractMikaCard {

    private final ArrayList<AbstractCard> cardsToChuxu = new ArrayList<>();
    private static final HashMap<String, AbstractCard> cardsLib = new HashMap<>();

    public Chuxvguowang() {
        super(Chuxvguowang.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
        this.selfRetain = true;
        this.exhaust = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            cardsToChuxu.clear();

            for (int i = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1,
                 flag = this.magicNumber;
                 i >= 0 && flag > 0;
                 i--, flag--) {
                AbstractCard c = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i);
                if (c.type == CardType.POWER) {
                    flag++;
                    continue;
                }
                cardsToChuxu.add(c);
            }

            MultiCardPreview.clear(this);
            cardsToChuxu.forEach(c -> {
                if (!cardsLib.containsKey(c.cardID)) {
                    cardsLib.put(c.cardID, c.makeCopy());
                }
                MultiCardPreview.add(this, cardsLib.get(c.cardID));
            });
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2) {

            ArrayList<AbstractCard> cards = new ArrayList<>();

            for (int i = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2,
                 flag = this.magicNumber;
                 i >= 0 && flag > 0;
                 i--, flag--) {
                AbstractCard c = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i);
                if (c.type == CardType.POWER) {
                    flag++;
                    continue;
                }
                cards.add(c);
            }

            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    cards.forEach(c -> {
                        ModHelper.moveCard(c, AbstractDungeon.player.hand);
                    });
                    isDone = true;
                }
            });

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
        return new Chuxvguowang();
    }

}