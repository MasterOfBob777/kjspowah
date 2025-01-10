package com.bobvaraioa.kubejspowah;

import dev.latvian.mods.kubejs.script.ScriptType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@Mod(KubeJSPowah.MODID)
public class KubeJSPowah {
    public static final String MODID = "kubejspowah";

    public KubeJSPowah() {
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, KubeJSPowah::serverReload);
    }

    public static void serverReload(TagsUpdatedEvent event) {
        KubeJSPowahPlugin.COOLANTS.post(ScriptType.SERVER, KubeJSPowahPlugin.CoolantsEvent.INSTANCE);
        KubeJSPowahPlugin.HEAT_SOURCE.post(ScriptType.SERVER, KubeJSPowahPlugin.HeatSourceEvent.INSTANCE);
        KubeJSPowahPlugin.MAGMATIC_FLUID.post(ScriptType.SERVER, KubeJSPowahPlugin.MagmaticFluidEvent.INSTANCE);
    }
}
