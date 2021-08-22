package TinkersWorkshop.relics;

import TinkersWorkshop.util.RelicInfo;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

import static TinkersWorkshop.TinkersWorkshop.makeUIPath;


public abstract class AbstractTinkerRelic extends CustomRelic {
    private static final float FPS_SCALE = (240f / Settings.MAX_FPS);
    private static final int MAX_PARTICLES = 7;

    public ArrayList<AugmentParticle> particles = new ArrayList<>();
    public AbstractTinkerRelic(String ID, RelicInfo relicInfo) {
        super(ID, relicInfo.loadRelicImage(relicInfo.relicName), relicInfo.loadOutlineImage(relicInfo.relicName), relicInfo.relicTier, relicInfo.sfx);
    }
    public void fixDescription() {
        description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    public abstract AbstractTinkerRelic makeCopy();
    @Override
    public boolean canSpawn() {
        return false;
    }

    @Override
    public void update(){
        super.update();

        particles.parallelStream()
                .forEach(AugmentParticle::update);
        particles.removeIf(AugmentParticle::isDead);

        if(this.particles.size() < MAX_PARTICLES){
            for(int i = 0; i < 2 * FPS_SCALE; i++){
                Vector2 point = generateRandomPointAlongEdgeOfHitbox();
                particles.add(new AugmentParticle(point.x, point.y, Settings.scale, this));
            }
        }
    }

    private Vector2 generateRandomPointAlongEdgeOfHitbox() {
        Vector2 result = new Vector2();
        Random random = new Random();
        boolean topOrBottom = random.randomBoolean();
        boolean leftOrRight = random.randomBoolean();
        boolean tbOrLr = random.randomBoolean();

        if(tbOrLr){
            result.x = random.random(this.currentX - (this.hb.width / 2f), this.currentX + (this.hb.width / 2f));
            result.y = topOrBottom ? this.currentY + (this.hb.height / 2f) : this.currentY - (this.hb.height / 2f);
        } else {
            result.x = leftOrRight ? this.currentX + (this.hb.width / 2f) : this.currentX - (this.hb.width / 2f);
            result.y = random.random(this.currentY - (this.hb.height / 2f), this.currentY + (this.hb.height / 2f));
        }

        return result;
    }

    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        particles.stream()
                .forEach(augmentParticle -> augmentParticle.render(sb));
        super.renderInTopPanel(sb);
    }


    public static class AugmentParticle {
        private Vector2 pos;
        private Vector2 vel;
        private float lifeSpan;
        private Color color;
        private float drawScale;
        private AbstractRelic r;

        private static TextureAtlas textureAtlas = new TextureAtlas(makeUIPath("particle.atlas"));

        public AugmentParticle(float x, float y, float drawScale, AbstractRelic relic) {
            pos = new Vector2(x, y);
            this.drawScale = drawScale;
            r = relic;
            float speedScale = MathUtils.clamp(
                    Gdx.graphics.getDeltaTime() * 240f,
                    FPS_SCALE - 0.2f,
                    FPS_SCALE + 0.2f);
            float maxV = 2.0f * drawScale;
            maxV = MathUtils.clamp(maxV, 0.01f, FPS_SCALE * 2f);

            float velX = MathUtils.random(-maxV * speedScale / 2f, maxV * speedScale / 2f);
            float velY = MathUtils.random(0.01f, maxV * speedScale);

            vel = new Vector2(velX, velY);

            lifeSpan = MathUtils.random(0.1f, 0.5f);

            switch (r.tier){
                case COMMON:
                    color = Color.WHITE.cpy();
                    break;
                case UNCOMMON:
                    color = Color.BLUE.cpy();
                    break;
                case RARE:
                    color = Color.YELLOW.cpy();
                    break;
                case SHOP:
                    color = Color.PURPLE.cpy();
                    break;
                case BOSS:
                    color = Color.RED.cpy();
                    break;
            }


        }

        public void update() {
            this.lifeSpan -= Gdx.graphics.getDeltaTime();
            this.pos.x += this.vel.x;
            this.pos.y += this.vel.y;
        }


        public void render(SpriteBatch sb) {
            sb.setColor(color);
            sb.draw(textureAtlas.findRegion("cardParticle"),
                    pos.x - 40f,
                    pos.y - 40f,
                    40f,
                    40f,
                    80f,
                    80f,
                    drawScale * (lifeSpan / 0.25f),
                    drawScale * (lifeSpan / 0.25f),
                    0f);
        }

        public boolean isDead() {
            return lifeSpan <= 0f;
        }
    }
}