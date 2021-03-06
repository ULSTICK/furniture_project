package com.ulstick.sticksdeco.core.events;

import com.ulstick.sticksdeco.SticksDeco;
import com.ulstick.sticksdeco.core.blocks.ModBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SticksDeco.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void WandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().add(new BasicTrade(1, new ItemStack(ModBlock.DYNASTY_LEAVES.get(), 8), 16, 10));
        event.getGenericTrades().add(new BasicTrade(2, new ItemStack(ModBlock.DYNASTY_LOG.get(), 6), 16, 10));
        event.getRareTrades().add(new BasicTrade(2, new ItemStack(ModBlock.DYNASTY_LOG.get(), 8), 8, 10));
    }
}
