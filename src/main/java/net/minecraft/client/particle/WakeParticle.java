package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class WakeParticle extends TextureSheetParticle
{
    private final SpriteSet sprites;

    WakeParticle(ClientLevel pLevel, double pX, double p_108409_, double pY, double p_108411_, double pZ, double p_108413_, SpriteSet pXSpeed)
    {
        super(pLevel, pX, p_108409_, pY, 0.0D, 0.0D, 0.0D);
        this.sprites = pXSpeed;
        this.xd *= (double)0.3F;
        this.yd = Math.random() * (double)0.2F + (double)0.1F;
        this.zd *= (double)0.3F;
        this.setSize(0.01F, 0.01F);
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.setSpriteFromAge(pXSpeed);
        this.gravity = 0.0F;
        this.xd = p_108411_;
        this.yd = pZ;
        this.zd = p_108413_;
    }

    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        int i = 60 - this.lifetime;

        if (this.lifetime-- <= 0)
        {
            this.remove();
        }
        else
        {
            this.yd -= (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= (double)0.98F;
            this.yd *= (double)0.98F;
            this.zd *= (double)0.98F;
            float f = (float)i * 0.001F;
            this.setSize(f, f);
            this.setSprite(this.sprites.get(i % 4, 4));
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites)
        {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double p_108443_, double pY, double p_108445_, double pZ, double p_108447_)
        {
            return new WakeParticle(pLevel, pX, p_108443_, pY, p_108445_, pZ, p_108447_, this.sprites);
        }
    }
}
