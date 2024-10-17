/*
 * Copyright (c) 2020-2023 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.polyhedraldevelopment.seismic.algorithms.sampler;

import com.polyhedraldevelopment.seismic.type.sampler.Sampler;

import java.awt.image.BufferedImage;


public class ImageSampler implements Sampler {
    private final BufferedImage image;
    private final Channel channel;

    private final double frequency;

    public ImageSampler(BufferedImage image, Channel channel, double frequency) {
        this.image = image;
        this.channel = channel;
        this.frequency = frequency;
    }

    @Override
    public double getSample(long seed, double x, double y) {
        return ((channel.getChannel(image.getRGB(Math.floorMod((int) Math.floor(x * frequency), image.getWidth()),
            Math.floorMod((int) Math.floor(y * frequency), image.getHeight()))) / 255D) - 0.5) *
               2;
    }

    @Override
    public double getSample(long seed, double x, double y, double z) {
        return getSample(seed, x, y);
    }

    public enum Channel {
        RED {
            @Override
            public int getChannel(int mashed) {
                return (mashed >> 16) & 0xff;
            }
        },
        GREEN {
            @Override
            public int getChannel(int mashed) {
                return (mashed >> 8) & 0xff;
            }
        },
        BLUE {
            @Override
            public int getChannel(int mashed) {
                return mashed & 0xff;
            }
        },
        GRAYSCALE {
            @Override
            public int getChannel(int mashed) {
                return (Channel.RED.getChannel(mashed) + Channel.GREEN.getChannel(mashed) + Channel.BLUE.getChannel(mashed)) / 3;
            }
        },
        ALPHA {
            @Override
            public int getChannel(int mashed) {
                return (mashed >> 24) & 0xff;
            }
        };

        public abstract int getChannel(int mashed);
    }
}
