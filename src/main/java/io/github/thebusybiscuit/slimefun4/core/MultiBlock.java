package io.github.thebusybiscuit.slimefun4.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.events.MultiBlockInteractEvent;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.multiblocks.MultiBlockMachine;
import me.mrCookieSlime.Slimefun.Objects.handlers.MultiBlockInteractionHandler;

/**
 * A {@link MultiBlock} represents a structure build in a {@link World}.
 * A {@link MultiBlock} is often linked to a {@link MultiBlockMachine} and is used
 * to recognize that machine in a {@link MultiBlockInteractEvent}.
 * 
 * @author TheBusyBiscuit
 * @author Liruxo
 * 
 * @see MultiBlockMachine
 * @see MultiBlockInteractionHandler
 * @see MultiBlockInteractEvent
 *
 */
public class MultiBlock {

    public static final List<Tag<Material>> SUPPORTED_TAGS = new ArrayList<>();

    static {
        SUPPORTED_TAGS.add(Tag.LOGS);
        SUPPORTED_TAGS.add(Tag.WOODEN_TRAPDOORS);
        SUPPORTED_TAGS.add(Tag.WOODEN_SLABS);

        if (SlimefunPlugin.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_14)) {
            SUPPORTED_TAGS.add(Tag.WOODEN_FENCES);
        }
    }

    private final SlimefunItem item;
    private final Material[] blocks;
    private final BlockFace trigger;
    private final boolean isSymmetric;

    public MultiBlock(SlimefunItem item, Material[] build, BlockFace trigger) {
        this.item = item;

        this.blocks = build;
        this.trigger = trigger;
        this.isSymmetric = isSymmetric(build);
    }

    public SlimefunItem getSlimefunItem() {
        return item;
    }

    private static boolean isSymmetric(Material[] blocks) {
        return blocks[0] == blocks[2] && blocks[3] == blocks[5] && blocks[6] == blocks[8];
    }

    public Material[] getBuild() {
        return this.blocks;
    }

    public BlockFace getTriggerBlock() {
        return this.trigger;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MultiBlock)) return false;

        MultiBlock mb = (MultiBlock) obj;
        if (trigger == mb.getTriggerBlock()) {
            for (int i = 0; i < mb.getBuild().length; i++) {
                if (!compareBlocks(blocks[i], mb.getBuild()[i])) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item.getID(), blocks, trigger, isSymmetric);
    }

    private boolean compareBlocks(Material a, Material b) {
        if (b != null) {

            for (Tag<Material> tag : SUPPORTED_TAGS) {
                if (tag.isTagged(b)) {
                    return tag.isTagged(a);
                }
            }

            if (b != a) {
                return false;
            }
        }

        return true;
    }

    /**
     * This returns whether this {@link MultiBlock} is a symmetric structure or whether
     * the left and right side differ.
     * 
     * @return Whether this {@link MultiBlock} is a symmetric structure
     */
    public boolean isSymmetric() {
        return isSymmetric;
    }

    @Override
    public String toString() {
        return "MultiBlock (" + item.getID() + ") {" + Arrays.toString(blocks) + "}";
    }
}
