package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.LessonLearned;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class Xuexidengcha extends AbstractMikaCard {
    public Xuexidengcha() {
        super(Xuexidengcha.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = 3;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int damage = this.damage;

        addToBot(new AbstractGameAction() {
            private DamageInfo info;

            {
                this.info = new DamageInfo(p, damage, Xuexidengcha.this.damageTypeForTurn);
                this.target = m;
                this.actionType = ActionType.DAMAGE;
                this.duration = Settings.ACTION_DUR_MED;
            }

            @Override
            public void update() {
                if (this.duration == Settings.ACTION_DUR_MED && this.target != null) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));

                    while (this.info.output > 0) {
                        this.target.damage(this.info);
                        this.info.output--;
                    }

                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }
                }

                this.tickDuration();
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Xuexidengcha();
    }
}