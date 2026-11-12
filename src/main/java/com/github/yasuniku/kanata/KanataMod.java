package com.github.yasuniku.kanata;

import com.github.yasuniku.kanata.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(KanataMod.MOD_ID)
public class KanataMod {
    public static final String MOD_ID = "kanata";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public KanataMod(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();

        // Register DeferredRegisters
        KanataBlock.register(eventBus);
        KanataBlockEntity.register(eventBus);
        KanataAttributes.register(eventBus);
        KanataEntity.register(eventBus);
        KanataItem.register(eventBus);
        KanataMenus.register(eventBus);
        KanataDimension.register(eventBus);
        KanataCreativeTabs.register(eventBus);
        KanataPatterns.register();
    }
}

