package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.powers.ZanbifengmangPower;
import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Zanbifengmang extends AbstractMikaCard {
    public Zanbifengmang() {
        super(Zanbifengmang.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 15;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {

                ArrayList<AbstractCard> l = new ArrayList<>(AbstractDungeon.player.hand.group).stream()
                        .filter(c -> c.type == CardType.ATTACK)
                        .collect(Collectors.toCollection(ArrayList::new));

                l.forEach(c -> {
                    ModHelper.timeTracing(c, AbstractDungeon.player.hand);
                });

                addToTop(new ApplyPowerAction(p, p, new ZanbifengmangPower(p, l)));

                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zanbifengmang();
    }
}