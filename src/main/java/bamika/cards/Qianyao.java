package bamika.cards;

import bamika.fantasyCard.AbstractMikaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class Qianyao extends AbstractMikaCard {
    public Qianyao() {
        super(Qianyao.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 4;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster mo = m;
        DamageInfo info = new DamageInfo(p, this.damage, this.damageTypeForTurn);

        addToBot(new AbstractGameAction() {
            {
                this.target = mo;
                this.source = info.owner;
                this.amount = info.output;
                this.actionType = ActionType.DAMAGE;
                this.startDuration = Settings.ACTION_DUR_FAST;
                this.duration = this.startDuration;
            }

            public void update() {
                if (this.shouldCancelAction()) {
                    this.isDone = true;
                } else {
                    this.tickDuration();
                    if (this.isDone) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY, false));
                        this.target.damage(info);
                        if (this.target.lastDamageTaken > 0) {
                            addToTop(new ApplyPowerAction(source, source, new VigorPower(source, this.target.lastDamageTaken)));
                            if (this.target.hb != null) {
                                this.addToTop(new VFXAction(new WallopEffect(this.target.lastDamageTaken, this.target.hb.cX, this.target.hb.cY)));
                            }
                        }

                        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                            AbstractDungeon.actionManager.clearPostCombatActions();
                        } else {
                            this.addToTop(new WaitAction(0.1F));
                        }
                    }

                }
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Qianyao();
    }
}