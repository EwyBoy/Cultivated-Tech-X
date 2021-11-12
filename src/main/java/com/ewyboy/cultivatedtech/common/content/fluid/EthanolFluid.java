package com.ewyboy.cultivatedtech.common.content.fluid;

import com.ewyboy.bibliotheca.common.content.fluid.BaseFluid;
import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.cultivatedtech.common.content.item.EthanolBucket;
import net.minecraft.world.item.BucketItem;

public abstract class EthanolFluid extends BaseFluid implements ContentLoader.IHasCustomBucket {

    protected EthanolFluid(Properties properties) {
        super(properties);
    }

    private static final EthanolBucket BUCKET = new EthanolBucket();

    public EthanolBucket getBucket() {
        return BUCKET;
    }

    @Override
    public BucketItem getCustomBucketItem() {
        return BUCKET;
    }

}
