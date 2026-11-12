package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.KanataMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class KanataCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KanataMod.MOD_ID);

    @SuppressWarnings("unused")
    public static final RegistryObject<CreativeModeTab> INTRODUCTION_TAB = TABS.register(
        "kanata_tab",
        () -> CreativeModeTab
            .builder()
            .title(Component.translatable("creativetab.kanata.kanata_tab"))
            .icon(KanataItem.DEBUG_ITEM.get()::getDefaultInstance)
            .displayItems((parameters, output) -> KanataItem.ITEMS
                .getEntries()
                .forEach(item -> output.accept(item.get())))
            .build()
    );

    public static void register(IEventBus eventBus) {
        // クリエイティブタブをイベントバスに登録
        TABS.register(eventBus);
    }
}
