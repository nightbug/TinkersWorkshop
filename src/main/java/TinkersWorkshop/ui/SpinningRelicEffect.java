package TinkersWorkshop.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;

import static TinkersWorkshop.TinkersWorkshop.makeEffectPath;

public class SpinningRelicEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private static TextureRegion img = null;
    public Texture imag;
    private boolean upgVFX = false;

    public SpinningRelicEffect(AbstractRelic relic) {
        if (img == null) {
            imag = relic.img;
            img = new TextureRegion(imag);
        }

        this.startingDuration = 2.0F;
        this.duration = this.startingDuration;
        this.scale = Settings.scale * 3.0F;
        this.x = (float)Settings.WIDTH * 0.5F - (float)img.getRegionWidth() / 2.0F;
        this.y = (float)img.getRegionHeight() / 2.0F;
        this.color = Color.WHITE.cpy();
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        if (this.duration < 1.0F) {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
        } else {
            this.y = Interpolation.swingIn.apply((float)Settings.HEIGHT * 0.7F - (float)img.getRegionHeight() / 2.0F, (float)(-img.getRegionHeight()) / 2.0F, this.duration - 1.0F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(img, this.x, this.y, (float)img.getRegionWidth() / 2.0F, (float)img.getRegionHeight() / 2.0F, (float)img.getRegionWidth(), (float)img.getRegionHeight(), this.scale, this.scale, this.duration * 360.0F);
    }

    public void dispose() {
    }

}