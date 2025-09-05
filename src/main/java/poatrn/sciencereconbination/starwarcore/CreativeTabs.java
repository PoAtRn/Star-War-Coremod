package poatrn.sciencereconbination.starwarcore;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StarWarCore.MODID);

    public static final RegistryObject<CreativeModeTab> TAB = REGISTER.register("tab",
            () -> CreativeModeTab.builder()
                    .title(Components.translatable("itemGroup."+ StarWarCore.MODID +".main"))
                    .withTabsBefore(AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey())
                    .icon(() -> {
                        return new ItemStack(Items.STICK);
                    })
                    .displayItems((params, output) -> {
                        List<ItemStack> items = StarWarCore.REGISTRATE.getAll(Registries.ITEM)
                                .stream()
                                .map((regItem) -> new ItemStack(regItem.get()))
                                .toList();
                        output.acceptAll(items);
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
